<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入医生名称查询" style="width: 200px" v-model="docName"></el-input>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
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
                <el-table-column prop="id" label="序号" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="userName" label="预约用户" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="docName" label="预约医生" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="status" label="预约状态" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="time" label="预约时间" align="center" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template v-slot="scope">
                        <el-button v-if="scope.row.status == '已预约'" plain type="primary" @click="handleEdit(scope.row.id)" size="mini">接诊</el-button>
                        <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
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

    </div>
</template>

<script>
    import E from "wangeditor"


    export default {
        nickName: "RegisterInfo2",
        data() {
            return {
                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                docName: null,
                fromVisible: false,
                fromVisible1: false,
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
            handleEdit(id){
                let data = {
                    id: id,
                    status: "已接诊",
                }
                this.$confirm('您确定接诊吗？', '确认接诊', {type: "success"}).then(response => {
                    this.$request({
                        url:  '/registerInfo/updateStatus',
                        method: 'PUT',
                        data: data
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.load(1)
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }).catch(() => {
                })
            },
            del(id) {   // 单个删除
                this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/registerInfo/delete/' + id).then(res => {
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
                    this.$request.delete('/registerInfo/delete/batch', {data: this.ids}).then(res => {
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
                this.$request.get('/registerInfo/selectPage2', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        docName: this.docName,
                    }
                }).then(res => {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                })
            },
            reset() {
                this.docName = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
            handleCoverSuccess(res) {
                this.form.img = res.data
            },
            setRichText(html) {
                this.$nextTick(() => {
                    this.editor = new E(`#editor`)
                    this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
                    this.editor.config.uploadFilenickName = 'file'
                    this.editor.config.uploadImgHeaders = {
                        token: this.token
                    }
                    this.editor.config.uploadImgParams = {
                        type: 'img',
                    }
                    this.editor.create()  // 创建
                    this.editor.txt.html(html)
                })
            },
            preview(content) {
                this.content = content
                this.fromVisible1 = true
            },
        }
    }
</script>

<style scoped>

</style>
