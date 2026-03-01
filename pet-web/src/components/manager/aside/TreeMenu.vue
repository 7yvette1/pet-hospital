<template>
    <div>
        <template v-for="item in menuList">
            <el-submenu
                    :class="topLevel?'hide-arrow':''"
                    v-if="item.menuType === 'M'"
                    :index="item.menuId+''">
                <template slot="title">
                    <i :class="item.icon"></i>
                    <span v-show="!isCollapse || item.parentId !== 0" slot="title">{{ item.label }}</span>
                </template>
                <TreeMenu :menu-list="item.children"></TreeMenu>
            </el-submenu>

            <el-menu-item
                    v-if="item.menuType === 'C'"
                    @click="clickMenu(item)"
                    :index="item.path"
                    :key="item.menuId+''">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.label }}</span>
            </el-menu-item>

        </template>
    </div>
</template>

<script>
    import {mapState,mapMutations} from "vuex";

    export default {
        name: 'TreeMenu',
        props: {
            menuList: {
                type: Array,
                default: function () {
                    return []
                }
            },
            topLevel: false,
        },
        data() {
            return {};
        },
        //监听属性 类似于data概念
        computed: {
            ...mapState('menu', ['isCollapse']),
        },
        methods: {
            ...mapMutations('tab', ['updateMenu']),
            //跳转路由 根据名称跳转
            clickMenu(item) {
                let {routeName} = item
                this.updateMenu(item);
                this.$router.push({name: routeName});
            },
        }
    }

</script>

<style scoped>

  /* 去除 el-submenu 展开箭头 */
  .el-submenu.hide-arrow >>> .el-icon-arrow-right:before {
    display: none;
  }

</style>
