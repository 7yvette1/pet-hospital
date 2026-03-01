<template>
    <header>
        <div class="l-content">
            <el-button plain icon="el-icon-s-fold" size="mini" @click="collapseMenu"></el-button>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item v-for="item in tabList" :key="item.path" :to="{ path: item.path }">
                    {{ item.label }}
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown trigger="click" size="mini">
                <span class="el-dropdown-link"><img :src="userInfo.avatar" class="user"/></span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="goPersonalCenter">个人中心</el-dropdown-item>
                    <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </header>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: 'CommonHeader',
        data() {
            return {

            }
        },
        computed: {
            ...mapState('tab', ['tabList']),
            ...mapState('user', ['userInfo']),
        },
        methods: {
            //控制左侧菜单是否折叠
            collapseMenu() {
                this.$store.commit('menu/collapseMenu')
            },
            goPersonalCenter() {
                this.$router.push({path: '/personalCenter'});
            },
            //退出登陆
            logOut() {
                window.sessionStorage.removeItem('token');
                this.$router.push({path: '/admin'});
                this.$message.success('退出成功');
            }
        },
    }
</script>

<style lang="scss" scoped>
  header {
    display: flex;
    height: 100%;
    align-items: center;
    justify-content: space-between;
  }

  .l-content {
    display: flex;
    align-items: center;

    .el-button {
      margin-right: 20px;
    }
  }

  .r-content {
      .user {
          width: 40px;
          height: 40px;
          border-radius: 50%;
      }
  }
</style>
