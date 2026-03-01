<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入菜单名称查询" style="width: 200px; margin-right: 5px" v-model="menuName"></el-input>
            <el-select v-model="status" placeholder="请选择菜单状态">
                <el-option :key="0" label="正常" :value="0"/>
                <el-option :key="1" label="停用" :value="1"/>
            </el-select>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
            <el-button type="primary" icon="el-icon-plus" plain @click="handleAdd">新增菜单</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData"
                      row-key="menuId"
                      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="menuId" label="菜单ID" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="menuName" label="菜单名称" align="center"></el-table-column>
                <el-table-column prop="menuType" label="菜单类型" align="center">
                    <template slot-scope="scope">
                        <el-tag size="medium" :type="scope.row.menuType === 'M' ? 'warning' : 'success'">
                            {{ scope.row.menuType === 'M' ? '目录' : '菜单' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="icon" label="图标" align="center" width="100">
                    <template slot-scope="scope">
                        <i :class="scope.row.icon"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="orderNum" label="排序" align="center" width="60"></el-table-column>
                <el-table-column prop="component" label="组件路径" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="status" label="状态" align="center" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <el-switch
                                size="small"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                active-value="0"
                                inactive-value="1"
                                @change="handleSwitchChange(scope.row)"
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="createBy" label="创建人" align="center"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
                <el-table-column prop="remark" label="备注" align="center" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="180" align="center" fixed="right">
                    <template v-slot="scope">
                        <el-button plain type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
                        <el-button plain type="danger" icon="el-icon-delete" size="mini" @click=del(scope.row.menuId)>删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[5, 10, 20]"
                        :page-size="pageSize"
                        layout="total,sizes, prev, pager, next"
                        :total="total">
                </el-pagination>
            </div>
        </div>


        <el-dialog title="菜单信息" :visible.sync="fromVisible" width="45%" :close-on-click-modal="false" destroy-on-close>
            <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="菜单类型" prop="menuType">
                            <el-radio-group v-model="form.menuType" @change="selectMenuType()">
                                <el-radio label="M">目录</el-radio>
                                <el-radio label="C">菜单</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item prop="parentId" label="上级菜单">
                            <el-cascader
                                    placeholder="请选择"
                                    :props="cascaderConfig"
                                    v-model="form.parentId"
                                    :options="menuOptions"
                                    key="menuId"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="菜单图标" prop="icon">
                            <el-input v-model="form.icon" autocomplete="off"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="菜单名称" prop="menuName">
                            <el-input v-model="form.menuName" placeholder="请输入菜单名称" autocomplete="off"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="路由路径" v-if="form.menuType === 'C'" prop="path">
                            <el-input v-model="form.path" autocomplete="off"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="路由名称" v-if="form.menuType === 'C'" prop="routeName">
                            <el-input v-model="form.routeName" autocomplete="off"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item v-if="form.menuType === 'C'" prop="component">
                              <span slot="label">
                                <el-tooltip content="访问的组件路径，如：`views/manage/system/SysMenu`，默认在`views`目录下" placement="top">
                                <i class="el-icon-question"></i>
                                </el-tooltip>
                                组件
                              </span>
                              <el-input v-model="form.component" placeholder="请输入组件" autocomplete="off"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="显示排序" prop="orderNum">
                            <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="菜单状态" prop="status">
                            <el-radio-group v-model="form.status">
                                <el-radio label="0">正常</el-radio>
                                <el-radio label="1">停用</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item prop="remark" label="备注">
                            <el-input type="textarea" :rows="5" v-model="form.remark" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
    import {mapActions} from 'vuex';

export default {
    name: "SysMenu",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            fromVisible: false,
            form: {},
            rules: {
                parentId: [
                    { required: true, message: "上级菜单不能为空", trigger: "blur" }
                ],
                menuType: [
                    { required: true, message: "菜单类型不能为空", trigger: "blur" }
                ],
                menuName: [
                    { required: true, message: "菜单名称不能为空", trigger: "blur" }
                ],
                path: [
                    { required: true, message: "路由路径不能为空", trigger: "blur" }
                ],
                routeName: [
                    { required: true, message: "路由名称不能为空", trigger: "blur" }
                ],
                component: [
                    { required: true, message: "组件不能为空", trigger: "blur" }
                ],
                orderNum: [
                    { required: true, message: "菜单顺序不能为空", trigger: "blur" }
                ],
                path: [
                    { required: true, message: "路由地址不能为空", trigger: "blur" }
                ]
            },
            ids: [],
            showId:false,
            menuName: null,
            status: null,
            // 菜单树选项
            menuOptions: [],
            cascaderConfig: {
                label: 'label',
                value: 'menuId',
                checkStrictly: true
            },
        }
    },
    created() {
        this.load(1);
        this.getDirMenuList();
    },
    methods: {
        ...mapActions('menu', ['refreshMenu']),
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/sysmenu/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    menuName: this.menuName,
                }
            }).then(res => {
                this.tableData = res.data?.list
                this.total = res.data?.total
            })
        },
        handleAdd() {   // 新增数据
            this.form = {}  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.form.parentId = this.form.parentId + '';
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if(typeof (this.form.parentId) == "object"){
                    this.form.parentId = this.form.parentId[this.form.parentId.length - 1]
                }
                if (valid) {
                    this.$request({
                        url: this.form.menuId ? '/sysmenu/updateById' : '/sysmenu/add',
                        method: this.form.menuId ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.load(1)
                            this.refreshMenu();
                            this.fromVisible = false
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        del(menuId) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/sysmenu/delete/' + menuId).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                        this.refreshMenu();
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        handleSwitchChange(row) {
            this.$request({
                url: '/sysmenu/updateById',
                method: 'PUT',
                data: row
            }).then(res => {
                if (res.code === '200') {
                    this.refreshMenu();
                    this.$message({
                        type: 'success',
                        message: '修改成功'
                    });
                } else {
                    this.$message({
                        type: 'error',
                        message: '修改失败，' + res.message
                    });
                }
            }).catch(err => {
                this.$message({
                    type: 'error',
                    message: '修改失败'
                });
            })
        },
        reset() {
            this.menuName = null
            this.load(1)
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        selectMenuType() {
            // 取消校验状态和提示信息
            this.$refs.formRef.clearValidate();
            this.cascaderConfig.checkStrictly = true;
            this.getDirMenuList();
        },
        getDirMenuList(){
            this.$request.get('/sysmenu/getDirMenuList').then(res => {
                if (res.code === '200') {
                    this.menuOptions = res.data
                }
            })
        },
    }
}
</script>

<style scoped>

</style>
