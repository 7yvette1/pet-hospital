<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入角色名称查询" style="width: 200px" v-model="roleName"></el-input>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
            <el-button type="primary" icon="el-icon-plus" plain @click="handleAdd">新增角色</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData">
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="roleId" label="角色ID" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="roleName" label="角色名称" align="center"></el-table-column>
                <el-table-column prop="status" label="角色状态" :show-overflow-tooltip='true' align="center">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium" :type="scope.row.status === '0' ? 'success' : 'danger'">
                                {{ formatStatus(scope.row) }}
                            </el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="createBy" label="创建人" align="center"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
                <el-table-column prop="remark" label="备注" align="center" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="300" align="center" fixed="right">
                    <template v-slot="scope">
                        <el-button plain type="primary" icon="el-icon-setting" @click="grantData(scope.row)" size="mini"
                                   :disabled="scope.row.roleId === 1">分配权限
                        </el-button>
                        <el-button plain type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" size="mini"
                                   :disabled="scope.row.roleId === 1">编辑
                        </el-button>
                        <el-button plain type="danger" icon="el-icon-delete" @click="del(scope.row.roleId)" size="mini"
                                   :disabled="scope.row.roleId === 1">删除
                        </el-button>
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


        <el-dialog title="角色信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
            <el-form label-width="120px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="form.roleName" placeholder="请输入角色名称" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="角色状态" prop="status">
                    <el-radio-group v-model="form.status">
                        <el-radio label="0">启用</el-radio>
                        <el-radio label="1">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="remark" label="备注">
                    <el-input type="textarea" :rows="5" v-model="form.remark" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog
                width="45%"
                top="3vh"
                title="权限分配"
                :visible.sync="dialogTreeVisible">
            <el-tree
                    :data="menuAllList"
                    show-checkbox
                    :check-strictly="true"
                    ref="tree"
                    node-key="menuId"
                    :default-expanded-keys="form.permissionList"
                    :default-checked-keys="form.permissionList"
                    @check="handleCheckNode"
                    :props="defaultProps">
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelGrant" size="medium">取 消</el-button>
                <el-button type="primary" @click="grantPermission" size="medium">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

export default {
    name: "SysRole",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            fromVisible: false,
            form: {},
            rules: {
                roleName: [
                    {required: true, message: '角色名称不能为空', trigger: 'blur'}
                ],
                status: [
                    {required: true, message: '角色状态不能为空', trigger: 'change'}
                ],
            },
            ids: [],
            showId:false,
            roleName: null,
            dialogTreeVisible: false,
            menuAllList: [],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
        }
    },
    created() {
        this.load(1)
        this.getMenuAllList()
    },
    methods: {
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/sysrole/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    roleName: this.roleName,
                }
            }).then(res => {
                if (res.code === '200') {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                }
            })
        },
        handleAdd() {   // 新增数据
            this.form = {status: "0"}  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.roleId ? '/sysrole/updateById' : '/sysrole/add',
                        method: this.form.roleId ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.load(1)
                            this.fromVisible = false
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        del(roleId) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/sysrole/delete/' + roleId).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load(1)
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => {
            })
        },
        reset() {
            this.roleName = null
            this.load(1)
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load(1)
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        formatStatus(row) {
            return row.status === '0' ? '正常' : '禁用'
        },
        //分配权限页面
        grantData(role) {
            this.dialogTreeVisible = true;
            this.form = role;
            this.$nextTick(() => {
                this.$refs.tree.setCheckedKeys([]);
                this.$refs.tree.setCheckedKeys(role.permissionList);
            });
        },
        //分配权限确定
        grantPermission() {
            this.$request({
                url: '/sysrole/grantPermissions',
                method: 'POST',
                data: this.form
            }).then(res => {
                if (res.code === '200') {
                    //this.refreshMenu()
                    this.load(1)
                    this.$message({
                        message: '权限分配成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: res.message,
                        type: 'error'
                    });
                }
                this.dialogTreeVisible = false;
            }).catch(err => {
                this.$message({
                    message: '权限分配失败',
                    type: 'error'
                });
            })
        },
        //分配权限取消
        cancelGrant() {
            this.dialogTreeVisible = false;
            this.load(1)
        },
        //查询所有菜单数据
        getMenuAllList() {
            this.$request.get('/sysmenu/selectAll').then(res => {
                if (res.code === '200') {
                    this.menuAllList = res.data
                }
            }).catch(err => {

            })
        },
        handleCheckNode() {
            this.form.permissionList = this.$refs.tree.getCheckedKeys()
        },
    }
}
</script>

<style scoped>

</style>
