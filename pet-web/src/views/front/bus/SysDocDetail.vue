<template>
    <div class="main-content" style="width: 70%">
        <div class="card" style="margin-bottom: 28px; ">
            <div style="display: flex; grid-gap: 20px; height: 350px;">
                <img :src="news.avatar" alt="" style="width: 50%">
                <div style="flex: 1">
                    <div style="font-size: 18px; font-weight: bold; margin-bottom: 40px">
                        {{news.nickName }}
                    </div>
                    <div class="item">
                        <dl style="display: block;">
                            <dt style="color: #333; float: left;">职位： </dt>
                            <dd style="color: #333;margin-left: 40px;">{{news.job }}</dd>
                        </dl>
                    </div>
                    <div class="item">
                        <dl style="display: block;">
                            <dt style="color: #333; float: left;">擅长： </dt>
                            <dd style="color: #333;margin-left: 40px;">
                                <el-tag v-for="(skill, i) in news.beGoodAt" :key="i" class="skill-tag">{{ skill }}</el-tag>
                            </dd>
                        </dl>
                    </div>
                    <div class="item">
                        <dl style="display: block;">
                            <dt style="color: #333; float: left;">描述： </dt>
                            <dd style="color: #333;margin-left: 40px;">
                                <div v-html="news.descr"></div>
                            </dd>
                        </dl>
                    </div>
                    <div style="margin-top: 30px; margin-left: 18px">
                        <el-button style="margin-left: 10px" type="primary" size="medium" @click="addOrder">订购挂号</el-button>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin: 50px 0;">
            <el-tabs type="border-card">
                <el-tab-pane label="特色服务" style="font-size: 15px; line-height: 1.8; ">
                    1.
                    24小时宠物急诊服务：24小时不间断服务，方便客户应对夜间急诊需求。对突发情况说NO！。

                    <br>2.

                    时时刻刻为爱宠健康保驾护航：每日定时医生查房、24小时看护、犬猫独立分区，避免交叉感染，全程保障爱宠健康。

                    <br> 3.

                    按照国际宠物医疗标准，每位医生经过严格专业的考核培训，每位服务人员都经过专业的礼仪培训，每个服务细节实行专项磨练，让您倍感信任与关怀。


                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysDocDetail",
        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                id: this.$route.query.id,
                news: {},
            }
        },
        created() {
            this.load();
        },
        methods: {
            load() {
                this.$request.get('/front/sysuser/selectById/' + this.id).then(res => {
                    this.news = res.data || {}
                })
            },
            addOrder(){
                if (this.user.userId === undefined){
                    this.$message.warning('请先登录')
                    return;
                }
                let data = {
                    userId: this.user.userId,
                    docId: this.id,
                    status: "已订购",
                }
                this.$confirm('您确定订购吗？', '确定订购', {type: "success"}).then(response => {
                    this.$request.post('/front/registerInfo/add', data).then(res => {
                        if (res.code === '200') {
                            this.$message.success('订购成功！')
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }).catch(() => {
                })
            },
        },

    }
</script>

<style scoped>
    .item {
        color: #666;
        margin-bottom: 20px;
        font-size: 14px;
        padding-left: 20px;
    }

    .skill-tag {
        margin-right: 10px;
        margin-bottom: 10px;
        background-color: #e3f2fd;
        color: #1976d2;
    }
</style>