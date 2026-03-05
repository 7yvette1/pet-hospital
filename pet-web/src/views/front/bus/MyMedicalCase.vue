<template>
    <div style="width: 70%; margin: 10px auto">
        <div class="table">
            <el-table :data="tableData">
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="宠物姓名" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="gender" label="患物性别" align="center" width="150">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            {{ scope.row.gender === '0' ? '公' : '母' }}
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="age" label="宠物年龄" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="phone" label="联系电话" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="visitDate" label="就诊日期" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="docName" label="接诊医生" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="medicalInfo" label="病因" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="diagnosis" label="诊断记录" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="adviceInfo" label="医嘱" align="center" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template v-slot="scope">
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
    export default {
        name: "MyMedicalCase",
        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),

                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                name: null,
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
            del(id) {   // 单个删除
                this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/medicalCase/delete/' + id).then(res => {
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
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/medicalCase/selectMyPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        name: this.name,
                        userId: this.user.userId,
                    }
                }).then(res => {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                })
            },
            reset() {
                this.name = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>

</style>
