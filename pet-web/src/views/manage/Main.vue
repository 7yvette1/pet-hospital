<template>
    <el-container style="height: 100%">
        <!--左侧栏-->
        <el-aside width="auto">
            <!--左侧栏控件-->
            <common-aside></common-aside>
        </el-aside>
        <!--右侧栏-->
        <el-container>
            <!--header部分-->
            <el-header>
                <!--header部分控件-->
                <common-header></common-header>
            </el-header>
            <el-main>
                <!--左侧栏 和 header部分对于整个后台部分都是不变的，唯一变的就是上面3的部分，这里就通过router-view来展示所需控件-->
                <div class="manage-main" style="height: calc(100vh - 52px)">
                    <router-view/>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>

import CommonAside from "../../components/manager/aside/CommonAside";
import CommonHeader from "../../components/manager/header/CommonHeader";

export default {
    name:'Main',
    components: {CommonAside, CommonHeader}
}
</script>

<style lang="scss" scoped>
@import "@/assets/css/manage.css";

  .el-header {
    background-color: #F3F3F3;
    height: 50px !important;
  }

  .el-main {
      padding: 1px;
  }
</style>
