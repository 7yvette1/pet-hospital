<template>
    <div class="app-container">
        <el-row :gutter="20">
            <el-col :span="6" :xs="24">
                <el-card class="box-card" style="height: 681px">
                    <div slot="header" class="clearfix">
                        <span>个人信息</span>
                    </div>
                    <div>
                        <div class="user-info-head"  @click="editAvatar()">
                            <img :src="user.avatar" class="img-circle img-lg"/>
                        </div>
                        <ul class="list-group list-group-striped">
                            <li class="list-group-item" >
                                用户ID
                                <div class="pull-right">{{ user.userId }}</div>
                            </li>
                            <li class="list-group-item">
                                用户名称
                                <div class="pull-right">{{ user.userName }}</div>
                            </li>
                            <li class="list-group-item">
                                用户呢称
                                <div class="pull-right">{{ user.nickName }}</div>
                            </li>
                            <li class="list-group-item">
                                手机号码
                                <div class="pull-right">{{ user.phone }}</div>
                            </li>
                            <li class="list-group-item">
                                用户邮箱
                                <div class="pull-right">{{ user.email }}</div>
                            </li>
                            <li class="list-group-item">
                                创建日期
                                <div class="pull-right">{{ user.createTime }}</div>
                            </li>
                        </ul>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="18" :xs="24">
                <el-card style="height: 681px">
                    <div slot="header" class="clearfix">
                        <span>基本资料</span>
                    </div>
                    <el-tabs v-model="activeTab">
                        <el-tab-pane label="基本资料" name="userinfo">
                            <el-form ref="form" :model="user" :rules="rules" label-width="80px">
                                <el-form-item label="手机号码" prop="phone">
                                    <el-input v-model="user.phone" maxlength="11" />
                                </el-form-item>
                                <el-form-item label="邮箱" prop="email">
                                    <el-input v-model="user.email" maxlength="50" />
                                </el-form-item>
                                <el-form-item label="性别">
                                    <el-radio-group v-model="user.sex">
                                        <el-radio label="0">男</el-radio>
                                        <el-radio label="1">女</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" size="mini" @click="submit1">保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                        <el-tab-pane label="修改密码" name="resetPwd">
                            <el-form ref="form1" :model="user" :rules="rules" label-width="80px">
                                <el-form-item label="旧密码" prop="password">
                                    <el-input v-model="user.password" placeholder="请输入旧密码" type="password" show-password/>
                                </el-form-item>
                                <el-form-item label="新密码" prop="newPassword">
                                    <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" show-password/>
                                </el-form-item>
                                <el-form-item label="确认密码" prop="confirmPassword">
                                    <el-input v-model="user.confirmPassword" placeholder="请确认新密码" type="password" show-password/>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" size="mini" @click="submit2">保存</el-button>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
        </el-row>



        <el-dialog title="用户头像" :visible.sync="fromVisible" width="50%" :close-on-click-modal="false" destroy-on-close>
            <el-image style="width: 300px; height: 300px; display: block; margin: 0 auto;"
                      :src="user.avatar" :preview-src-list="[user.avatar]">
            </el-image>
            <div style="display: flex;flex-wrap: nowrap; justify-content: space-around;">
            <el-upload :action="$baseUrl + '/files/upload'"
                       :on-success="handleAvatarSuccess"
                       :show-file-list="false">
                <el-button type="primary"  size="small" style="height: 30px; width: 80px">
                    选择
                    <i class="el-icon-upload el-icon--right"></i>
                </el-button>
            </el-upload>
            <el-button  type="primary" size="small" style="height: 30px; width: 80px" @click="uploadImg()">提 交</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PersonalCenter",
        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                activeTab: "userinfo",
                form: {},
                form1: {},
                // 表单校验
                rules: {
                    password: [
                        { required: true, message: "旧密码不能为空", trigger: "blur" }
                    ],
                    newPassword: [
                        { required: true, message: "新密码不能为空", trigger: "blur" },
                        { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
                        { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
                    ],
                    confirmPassword: [
                        { required: true, message: "确认密码不能为空", trigger: "blur" },
                    ],
                    email: [
                        { required: true, message: "邮箱地址不能为空", trigger: "blur" },
                        {
                            type: "email",
                            message: "请输入正确的邮箱地址",
                            trigger: ["blur", "change"]
                        }
                    ],
                    phone: [
                        { required: true, message: "手机号码不能为空", trigger: "blur" },
                        {
                            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                            message: "请输入正确的手机号码",
                            trigger: "blur"
                        }
                    ]
                },
                fromVisible: false,
            };
        },
        created() {
            this.getUser();
        },
        methods: {
            getUser(){
                if (this.user.userId === undefined){
                    this.$message.warning('请先登录')
                    this.$router.push('/front/login')  // 跳转到前端登记页面
                }
                this.$request.get('/front/selectById/' + this.user.userId).then(res => {
                    if (res.code === '200') {
                        this.user = res.data;
                        this.user.roleNameList1 = res.data.roleNameList.toString();
                    }
                }).catch(err => {

                })
            },
            submit1(){
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.$request({
                            url: '/front/updateById',
                            method: 'PUT',
                            data: this.user
                        }).then(res => {
                            if (res.code === '200') {  // 表示成功保存
                                this.$message.success('保存成功')
                            } else {
                                this.$message.error(res.msg)  // 弹出错误的信息
                            }
                        })
                    }
                })
            },
            submit2(){
                console.log(this.user.newPassword);
                if(this.user.newPassword != this.user.confirmPassword){
                    this.$message.error("新密码和确认密码不一样！")
                }
                this.$refs.form1.validate((valid) => {
                    if (valid) {
                        this.$request({
                            url: '/front/updatePassword',
                            method: 'PUT',
                            data: this.user
                        }).then(res => {
                            if (res.code === '200') {  // 表示成功保存
                                this.$message.success('保存成功')
                                localStorage.removeItem("xm-user");
                                this.$router.push('/front/login')  // 跳转到登录页
                            } else {
                                this.$message.error(res.msg)  // 弹出错误的信息
                            }
                        })
                    }
                })
            },
            editAvatar(){
                this.fromVisible = true   // 打开弹窗
            },
            handleAvatarSuccess(response, file, fileList) {
                // 把头像属性换成上传的图片的链接
                this.user.avatar = response.data
            },
            uploadImg(){
                this.$request({
                    url: '/front/updateById',
                    method: 'PUT',
                    data: this.user
                }).then(res => {
                    if (res.code === '200') {  // 表示成功保存
                        // 更新属性值
                        var userInfo = JSON.parse(localStorage.getItem('xm-user'));
                        userInfo.avatar = this.user.avatar;
                        localStorage.setItem('xm-user', JSON.stringify(userInfo));
                        this.$message.success('保存成功')
                        this.fromVisible = false   // 打开弹窗
                        window.location.reload()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .app-container {
        width: 70%;
        margin: 10px auto;
    }
    .list-group-item {
        border-bottom: 1px solid #e7eaec;
        border-top: 1px solid #e7eaec;
        margin-bottom: -1px;
        padding: 11px 0px;
        font-size: 13px;
    }
    .list-group-striped > .list-group-item {
        border-left: 0;
        border-right: 0;
        border-radius: 0;
        padding-left: 0;
        padding-right: 0;
    }
    .pull-right {
        float: right !important;
    }
    .img-circle {
        border-radius: 50%;
    }
    .img-lg {
        width: 100px;
        height: 100px;
    }
    .user-info-head {
        position: relative;
        display: inline-block;
        height: 100px;
        margin-left: 50px;
        align-items: center;
        justify-content: center;
    }
    .user-info-head:hover:after {
        content: '+';
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        color: #eee;
        background: rgba(0, 0, 0, 0.5);
        font-size: 24px;
        font-style: normal;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        cursor: pointer;
        border-radius: 50%;
    }
</style>