<template>
    <div style="width: 70%; margin: 10px auto">
        <div>
            <div style="padding-bottom: 10px">
                <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
                    <div style="margin-left: 20px">我的预约（{{ ordersData.length }} 个）</div>
                </div>
                <div style="margin: 20px 0; padding: 0 50px">
                    <div class="table">
                        <el-table :data="ordersData" strip>
                            <el-table-column prop="id" label="序号" width="80" align="center" v-if="showId"></el-table-column>
                            <el-table-column prop="clinicName" label="预约服务" align="center" show-overflow-tooltip></el-table-column>
                            <el-table-column prop="docName" label="预约医生" align="center" show-overflow-tooltip></el-table-column>
                            <el-table-column prop="userName" label="预约用户" align="center" show-overflow-tooltip></el-table-column>
                            <el-table-column prop="status" label="预约状态" align="center" show-overflow-tooltip></el-table-column>
                            <el-table-column prop="time" label="预约时间" align="center" show-overflow-tooltip></el-table-column>
                            <el-table-column label="操作" align="center" width="180">
                                <template v-slot="scope">
                                    <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>

                        <div class="pagination" style="margin-top: 20px">
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
            </div>
        </div>
    </div>
</template>

<script>

    export default {

        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                ordersData: [],
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                form: {},
                fromVisible: false,
            }
        },
        mounted() {
            this.loadOrders(1)
        },
        // methods：本页面所有的点击事件或者其他函数定义区
        methods: {
            loadOrders(pageNum) {
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/registerInfo/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        userId: this.user.userId,
                    }
                }).then(res => {
                    if (res.code === '200') {
                        this.ordersData = res.data?.list
                        this.total = res.data?.total
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            navTo(url) {
                location.href = url
            },
            del(id) {
                this.$request.delete('/front/registerInfo/delete/' + id).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.loadOrders(1)
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            handleCurrentChange(pageNum) {
                this.loadOrders(pageNum)
            },
        }
    }
</script>