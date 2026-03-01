<template>
    <!--collapse 是否水平折叠收起菜单-->
    <el-menu
            :collapse="isCollapse"
            :default-active="$route.path"
            class="el-menu-vertical-demo"
            background-color="#2f4158"
            text-color="#fff"
            active-text-color="#2b9dfc">
        <!--是否水平折叠收起菜单 会影响这里字段的显示 -->
        <h3 v-show="isCollapse">系统</h3>
        <h3 v-show="!isCollapse">后台管理系统</h3>

        <TreeMenu :menu-list="getMenuList" :top-level="true"></TreeMenu>
    </el-menu>



</template>

<script>
    import {mapState, mapGetters, mapActions} from 'vuex';
    import TreeMenu from "./TreeMenu";

    export default {
        name: 'CommonAside',
        components: {TreeMenu},
        data() {
            return {
                menuList: []
            }
        },
        computed: {
            hasChildren() {
                return this.menuList.filter(item => item.children)
            },
            ...mapState('tab', ['tabList']),
            ...mapState('user', ['userInfo']),
            ...mapState('menu', ['isCollapse']),
            ...mapGetters('menu', ['getMenuList'])
        },
        methods: {
            ...mapActions('menu', ['refreshMenu']),
        },
        mounted() {
            this.refreshMenu();
        }

    }
</script>

<style lang="scss" scoped>
    .el-menu {
        height: 100%;
        border: none;

        h3 {
            color: #ffffff;
            text-align: center;
            line-height: 48px;
        }
    }

    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }
</style>
