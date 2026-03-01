<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入标题查询" style="width: 200px" v-model="title"></el-input>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="operation">
            <el-button type="primary" icon="el-icon-plus" plain @click="handleAdd">新增</el-button>
            <el-button type="danger"  icon="el-icon-delete" plain @click="delBatch">批量删除</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" strip @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="ID" width="70" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="title" label="标题" align="center"></el-table-column>
                <el-table-column prop="type" label="类型"  align="center"></el-table-column>
                <el-table-column prop="descr" label="简介" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column label="内容" align="center">
                    <template v-slot="scope">
                        <el-button @click="preview(scope.row.content)">查看内容</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="cover" label="封面" align="center">
                    <template v-slot="scope">
                        <el-image :preview-src-list="[scope.row.cover]" :src="scope.row.cover" style="width: 60px; height: 40px;"></el-image>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="180">
                    <template v-slot="scope">
                        <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
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


        <el-dialog title="信息" :visible.sync="fromVisible" width="50%" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title" placeholder="标题" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="简介" prop="descr">
                    <el-input type="textarea" v-model="form.descr" placeholder="简介" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="分类" prop="type">
                    <el-select style="width: 100%" v-model="form.type">
                        <el-option v-for="item in typeList" :key="item.id" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="封面" prop="cover">
                    <el-upload
                            class="avatar-uploader"
                            :action="$baseUrl + '/files/upload'"
                            :headers="{ token: this.token }"
                            list-type="picture"
                            :on-success="handleCoverSuccess"
                    >
                        <el-button type="primary">上传封面</el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <div id="editor"></div>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="内容" :visible.sync="fromVisible1" width="50%" :close-on-click-modal="false" destroy-on-close>
            <div class="w-e-text">
                <div v-html="content"></div>
            </div>

            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible1 = false">关 闭</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import E from "wangeditor"

    export default {
        name: "BusNews",
        data() {
            return {
                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                title: null,
                fromVisible: false,
                form: {},
                token: window.sessionStorage.getItem('token'),
                rules: {
                    title: [
                        {required: true, message: '请输入标题', trigger: 'blur'},
                    ],
                    descr: [
                        {required: true, message: '请输入简介', trigger: 'blur'},
                    ]
                },
                ids: [],
                showId:false,
                content: '',
                fromVisible1: false,
                typeList: [{"id":"1","name":"资讯信息"},{"id":"2","name":"康复信息"}],
            }
        },
        created() {
            this.load(1);
        },
        methods: {
            handleAdd() {   // 新增数据
                this.form = {}  // 新增数据的时候清空数据
                this.fromVisible = true   // 打开弹窗
                this.setRichText('')
            },
            handleEdit(row) {   // 编辑数据
                this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
                this.fromVisible = true   // 打开弹窗
                this.setRichText(this.form.content)
            },
            save() {
                this.$refs.formRef.validate((valid) => {
                    if (valid) {
                        this.form.content = this.editor.txt.html()
                        this.$request({
                            url: this.form.id ? '/busnews/update' : '/busnews/add',
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
            del(id) {
                this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/busnews/delete/' + id).then(res => {
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
                    this.$request.delete('/busnews/delete/batch', {data: this.ids}).then(res => {
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
                this.$request.get('/busnews/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        title: this.title,
                    }
                }).then(res => {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                })
            },
            reset() {
                this.title = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
            handleCoverSuccess(res) {
                this.form.cover = res.data
            },
            setRichText(html) {
                this.$nextTick(() => {
                    this.editor = new E(`#editor`)
                    this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
                    this.editor.config.uploadFileName = 'file'
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
