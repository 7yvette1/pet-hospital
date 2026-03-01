<template>
    <div>
        <el-card class="box-card">
            <div class="icon-grid">
                <div class="icon-item" v-for="(icon, index) in icons" :key="index">
                    <i :class="icon.iconName"></i>
                    <span>{{ icon.iconName }}</span>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>

export default {
    name: 'SysIcon',
    components: {},
    data() {
        return {
            icons: [],
        };
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
        getIcons() {
            this.$request.get('/sysicon/selectAll').then(res => {
                if (res.code === '200') {
                    this.icons = res.data
                } else {
                    this.$message.error('icon加载失败...')
                }
            }).catch(err => {
                this.$message.error('icon加载失败...')
            })
        },
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {

    },
    //生命周期 - 挂载完成（可以访问DOM元素）
    mounted() {
        this.getIcons()
    }
};
</script>

<style scoped>
.header {
    height: 50px;
    background-color: #eff4f9;
}

.page-header {
    float: left;
    margin-top: 15px
}

.box-card {
    width: 100%;
}

.icon-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
    align-items: center;
}

.icon-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 12px;
    width: 12%;
    height: 65px;
}

.icon-item i {
    font-size: 30px;
    margin-bottom: 12px;
}

</style>
