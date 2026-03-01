<template>
    <div>
        <div class="search">
            <el-select v-model="isinuse" placeholder="请选择是否使用">
                <el-option :key="1" label="是" :value="1"/>
                <el-option :key="0" label="否" :value="0"/>
            </el-select>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
            <el-button type="primary" icon="el-icon-plus" plain @click="handleAdd">新增轮播图</el-button>
            <el-button type="danger"  icon="el-icon-delete" plain @click="delBatch">批量删除</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="ID" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="image" label="轮播图" align="center">
                    <template slot-scope="scope">
                        <el-image style="width: 40px; height: 40px; border-radius: 50%"
                                  :src="scope.row.image" :preview-src-list="[scope.row.image]">
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="isinuse" label="是否使用" align="center" width="150">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            {{ scope.row.isinuse === '0' ? '否' : '是' }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="time" label="创建时间" align="center"></el-table-column>
                <el-table-column prop="user" label="创建人" align="center"></el-table-column>

                <el-table-column label="操作" width="180" align="center" fixed="right">
                    <template v-slot="scope">
                        <el-button plain type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
                        <el-button plain type="danger" icon="el-icon-delete" @click=del(scope.row.id) size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination
                        background
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[5, 10, 20]"
                        :page-size="pageSize"
                        layout="total, prev, pager, next"
                        :total="total">
                </el-pagination>
            </div>
        </div>


        <el-dialog title="新增轮播图" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
            <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
                <el-form-item label="轮播图">
                    <el-upload
                            class="upload-demo"
                            :action="$baseUrl + '/files/upload'"
                            list-type="picture"
                            :on-success="handleAvatarSuccess">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item prop="isinuse" label="是否使用">
                    <el-radio-group v-model="form.isinuse">
                        <el-radio label="1">是</el-radio>
                        <el-radio label="0">否</el-radio>
                    </el-radio-group>
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
        name: "BaseCarousel",
        data() {
            return {
                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                isinuse: null,
                fromVisible: false,
                form: {},
                rules: {

                },
                ids: [],
                showId:false,
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            handleAdd() {   // 新增数据
                this.form = {}  // 新增数据的时候清空数据
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
                            url: this.form.id ? '/basecarousel/update' : '/basecarousel/add',
                            method: this.form.id ? 'PUT' : 'POST',
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
            del(id) {   // 单个删除
                this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/basecarousel/delete/' + id).then(res => {
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
            handleSelectionChange(rows) {   // 当前选中的所有的行数据
                this.ids = rows.map(v => v.id)   //  [1,2]
            },
            delBatch() {   // 批量删除
                if (!this.ids.length) {
                    this.$message.warning('请选择数据')
                    return
                }
                this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/basecarousel/delete/batch', {data: this.ids}).then(res => {
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
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/basecarousel/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        isinuse: this.isinuse,
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
            reset() {
                this.isinuse = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
            handleAvatarSuccess(response, file, fileList) {
                // 把头像属性换成上传的图片的链接
                this.form.image = response.data
            },
        }
    }
</script>

<style scoped>

</style>
