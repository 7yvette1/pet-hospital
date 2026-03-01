<template>
    <div>
        <div class="search">
            <el-input placeholder="请输入用户名查询" style="width: 200px" v-model="userName"></el-input>
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
                <el-table-column prop="id" label="ID" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="userName" label="用户名" align="center"></el-table-column>
                <el-table-column prop="loginTime" label="创建时间" align="center"></el-table-column>
                <el-table-column prop="loginAddr" label="登录IP地址" align="center"></el-table-column>

                <el-table-column label="操作" width="300" align="center" fixed="right">
                    <template v-slot="scope">
                        <el-button plain type="danger" icon="el-icon-delete" @click="del(scope.row.id)" size="mini"
                                   :disabled="scope.row.id === 1">删除
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
    </div>
</template>

<script>

export default {
    name: "SysLogin",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 10,  // 每页显示的个数
            total: 0,
            fromVisible: false,
            form: {},
            rules: {

            },
            ids: [],
            showId:false,
            userName: null,
        }
    },
    created() {
        this.load(1)
    },
    methods: {
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/syslogin/selectPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    userName: this.userName,
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

        del(roleId) {   // 单个删除
            this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/syslogin/delete/' + roleId).then(res => {
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
        delBatch() {   // 批量删除
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/syslogin/delete/batch', {data: this.ids}).then(res => {
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
        handleSelectionChange(rows) {   // 当前选中的所有的行数据
            this.ids = rows.map(v => v.id)   //  [1,2]
        },
    }
}
</script>

<style scoped>

</style>
