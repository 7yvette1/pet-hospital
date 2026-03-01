<template>
    <div class="main-content" style="width: 60%">
        <div class="card">
            <div style="margin-bottom: 20px; font-size: 22px; font-weight: bold; text-align: center">{{news.title }}</div>
            <div style="color: #666; text-align: center">
                <span style="margin-right: 20px">发布时间：{{ news.time }}</span>
                <span style="margin-right: 20px">发布人：{{ news.user }}</span>
            </div>
            <div style="margin: 30px 0; padding: 0 20px">
                <div class="w-e-text" style="line-height: 26px; text-indent: 2rem">
                    <div v-html="news.content"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "BusNewsDetail",
        data() {
            return {
                id: this.$route.query.id,
                news: {},
            }
        },
        created() {
            this.load();
        },
        methods: {
            load() {
                this.$request.get('/front/busnews/selectById/' + this.id).then(res => {
                    this.news = res.data || {}
                })
            },
        },

    }
</script>

<style scoped>

</style>