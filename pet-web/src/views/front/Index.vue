<template>
    <div style="background-color: #f6f6f6; min-height: 100vh">
        <!--
        <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div>
        -->
        <!--头部-->
        <div class="front-header">
            <div class="front-header-left">
                <img src="@/assets/imgs/宠物诊所.png" alt="">
                <div class="title" @click="navTo('/front/home')">智慧宠物综合管理系统</div>
            </div>
            <div class="front-header-center">
                <div class="front-header-nav">
                    <el-menu :default-active="$route.path" mode="horizontal" router>
                        <el-menu-item index="/front/home">首页</el-menu-item>
                        <el-menu-item index="/front/busCategory">宠物服务</el-menu-item>

                        <el-menu-item index="/front/sysDoc">服务团队</el-menu-item>
                          <!--
                          <el-menu-item index="/front/new">诊所资讯</el-menu-item>
                          <el-menu-item index="/front/new1">康复故事</el-menu-item>
                          -->
                          <el-menu-item index="/front/forum">宠物社区</el-menu-item>
                          <el-menu-item index="/front/ai">AI问诊</el-menu-item>
                          <el-menu-item index="/front/aboutUs">关于我们</el-menu-item>
                          <el-menu-item index="/front/person">个人中心</el-menu-item>
                          <el-menu-item index="" @click="navigateTo('/admin', '_blank')">后台管理</el-menu-item>
                        </el-menu>
                </div>
            </div>
            <div class="front-header-right">
                <div v-if="!user.userName">
                    <el-button @click="$router.push('/front/login')">登录</el-button>
                    <el-button @click="$router.push('/front/register')">注册</el-button>
                </div>
                <div v-else>
                    <el-dropdown>
                        <div class="front-header-dropdown">
                            <img :src="user.avatar" alt="">
                            <div style="margin-left: 10px">
                                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                            </div>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <div @click="$router.push('/front/myRegistInfo')">我的预约记录</div>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <div @click="$router.push('/front/myMedicalCase')">宠物病例信息</div>
                            </el-dropdown-item>
                            <el-dropdown-item>
                                <div @click="logout">退出登录</div>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </div>
        <!--主体-->
        <div class="main-body">
            <router-view ref="child" @update:user="updateUser" />
        </div>

        <Footer />
    </div>
</template>

<script>
import Footer from "@/components/front/Footer";

export default {
    name: "Index",
    components: {Footer},
    data () {
        return {
            top: '',
            user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
        }
    },

    mounted() {
        this.loadNotice()
    },
    methods: {
        navigateTo(url, target) {
            // 使用window.open在新标签页中打开链接
            window.open(url, target);
        },
        updateUser() {
            this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
        },
        // 退出登录
        logout() {
            localStorage.removeItem("xm-user");
            this.$router.push("/front/login");
        },
        navTo(url) {
            location.href = url
        },
        loadNotice() {
            this.$request.get('/front/basenotice/selectAll').then(res => {
                this.notice = res.data
                let i = 0
                if (this.notice && this.notice.length) {
                    this.top = this.notice[0].content
                    setInterval(() => {
                        this.top = this.notice[i].content
                        i++
                        if (i === this.notice.length) {
                            i = 0
                        }
                    }, 2500)
                }
            })
        },
    }

}
</script>

<style scoped>
    @import "@/assets/css/front.css";
</style>