<template>
    <div class="main-content">
        <div>
            <el-row :gutter="10">
                <el-col :span="6" v-for="item in newsInfoList" :key="item.userId">
                    <div style="margin-bottom: 10px; cursor: pointer;" @click="$router.push('/front/sysDocDetail?id=' + item.userId)">
                        <img :src="item.avatar" alt="" style="width: 100%; height: 220px; display: block; border-radius: 5px 5px 0 0;">
                        <div style="padding: 10px; background-color: #fff; box-shadow: -3px 3px 3px -2px rgba(0,0,0,0.1), 3px 3px 3px -2px rgba(0,0,0,0.1);">
                            <div class="line2" style="height: 20px; margin-bottom: 5px; text-align: center">
                                <el-tag>{{ item.nickName }}</el-tag>
                            </div>
                            <div class="line2" style="height: 50px; margin-bottom: 5px; text-align: center">
                                {{ item.descr }}
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>

        <div style="margin: 10px 0" v-if="total">
            <el-pagination
                    background
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-size="pageSize"
                    layout="total, prev, pager, next"
                    :total="total">
            </el-pagination>
        </div>

    </div>
</template>

<script>
    export default {
        name: "SysDoc",
        data() {
            return {
                newsInfoList: [],
                pageNum: 1,
                pageSize: 8,
                total: 0
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/sysuser/selectDocPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                    }
                }).then(res => {
                    this.newsInfoList = res.data?.list
                    this.total = res.data?.total
                })
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>

</style>