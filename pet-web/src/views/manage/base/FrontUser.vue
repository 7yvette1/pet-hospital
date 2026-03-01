<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入姓名查询" style="width: 200px" v-model="userName"></el-input>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData">
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="userId" label="用户ID" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="avatar" label="头像" align="center">
                    <template slot-scope="scope">
                        <el-image style="width: 40px; height: 40px; border-radius: 50%"
                                  :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="userName" label="用户名" align="center" width="150"></el-table-column>
                <el-table-column prop="nickName" label="呢称" align="center" width="150"></el-table-column>
                <el-table-column prop="status" label="账号状态" align="center" width="150">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium"
                                    :type="scope.row.status === '0' ? 'success' : 'danger'">
                                {{ formatStatus(scope.row) }}
                            </el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="email" label="邮箱" align="center" width="150"></el-table-column>
                <el-table-column prop="phone" label="手机号" align="center" width="150"></el-table-column>
                <el-table-column prop="sex" label="用户性别" align="center" width="150">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            {{ scope.row.sex === '0' ? '男' : '女' }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="createBy" label="创建人" align="center" width="150"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center" width="150"></el-table-column>
                <el-table-column prop="remark" label="备注" align="center" width="150" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="300" align="center" fixed="right">
                    <template v-slot="scope">
                        <el-button plain type="danger" icon="el-icon-delete" @click="del(scope.row.userId)" size="mini"
                                   :disabled="scope.row.userId === 1 ">删除
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


        <el-dialog title="用户信息" :visible.sync="fromVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
            <el-form :inline="true" label-width="120px" style="padding: 0px 50px" :model="form" :rules="rules" ref="formRef" size="small">
                <el-form-item label="用户名" prop="userName" label-width="120px">
                    <el-input v-model="form.userName" placeholder="请输入用户名" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" placeholder="请输入密码" type="password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱" autocomplete="off" />
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入电话" autocomplete="off" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" placeholder="请选择">
                        <el-option
                                v-for="item in sexOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="头像">
                    <el-upload
                            class="upload-demo"
                            :action="$baseUrl + '/files/upload'"
                            list-type="picture"
                            :on-success="handleAvatarSuccess">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="用户状态" prop="status">
                    <el-switch
                            v-model="form.status"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="启用"
                            inactive-text="禁用"
                            active-value="0"
                            inactive-value="1">
                    </el-switch>
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
    </div>
</template>

<script>

export default {
    name: "FrontUser",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            fromVisible: false,
            form: {},
            rules: {
                userName: [
                    {required: true, message: '用户名不能为空', trigger: 'blur'}
                ],
            },
            ids: [],
            showId:false,
            userName: null,
            sexOptions: [
                {
                    value: '0',
                    label: '男'
                }, {
                    value: '1',
                    label: '女'
                }
            ],
            dialogCheckBoxVisible: false,
            grantRoleParams: {
                userId: 0,
                roleIdList: []
            },
        }
    },
    created() {
        this.load(1)
    },
    methods: {
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/fore/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    userName: this.userName,
                }
            }).then(res => {
                if (res.code === '200'){
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                }else{
                    this.$message.error(res.msg)
                }
            })
        },
        handleAdd() {   // 新增数据
            this.form = {status: '0'}  // 新增数据的时候清空数据
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
                        url: this.form.userId ? '/front/updateById' : '/front/add',
                        method: this.form.userId ? 'PUT' : 'POST',
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
        del(userId) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/front/delete/' + userId).then(res => {
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
            this.userName = null
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
        handleAvatarSuccess(response, file, fileList) {
            // 把头像属性换成上传的图片的链接
            this.form.avatar = response.data
        },
    }
}
</script>

<style scoped>

</style>
