<template>
    <div class="container">
        <div style="width: 400px; padding: 30px; background-color: #f9f9f9; border-radius: 5px;">
            <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">欢迎登录智慧宠物综合管理系统</div>
            <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item prop="username">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item prop="verificationCode">
                    <el-input placeholder="请输入验证码" v-model="form.verificationCode">
                        <template slot="append">
                            <div class="img-box">
                                <el-image
                                        @click="getVerificationCode"
                                        class="code-img"
                                        :src="verificationCode.dataUrl"
                                        fit="fill">
                                </el-image>
                            </div>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%; background-color: #2a60c9; border-color: #2a60c9; color: white" @click="login">登 录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {mapActions} from "vuex";
    import LoadingUtil from "@/utils/LoadingUtil";

    export default {
        name: "Login",
        data() {
            return {
                form: {},
                dialogVisible: true,
                rules: {
                    userName: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ],
                    verificationCode: [
                        { required: true, message: '请输入验证码', trigger: 'blur' },
                    ]
                },
                verificationCode: {
                    flagKey: '',
                    dataUrl: '',
                },
            }
        },
        created() {

        },
        mounted() {
            this.getVerificationCode()
        },
        methods: {
            login() {
                let loading = LoadingUtil.loading('正在登录中...');
                this.$refs['formRef'].validate((valid) => {
                    if (valid) {
                        // 验证通过
                        this.form.flagKey = this.verificationCode.flagKey;
                        this.$request.post('/manage/login', this.form).then(res => {
                            if (res.code === '200') {
                                let token = res.data.token;
                                window.sessionStorage.setItem('token', token);
                                setTimeout(() => {
                                    this.$request.get('/sysuser/getUserInfo', {
                                        params: {
                                            token: token,
                                        }
                                    }).then(res => {
                                        if (res.code === '200') {
                                            this.setUserInfo(res.data)
                                            this.$router.push('/home')  // 跳转到后端主页
                                        }
                                    })
                                    loading.close();
                                    this.$message.success('登录成功')
                                }, 1000)
                            } else {
                                this.$message.error(res.msg)
                                loading.close();
                            }
                        }).catch(err => {
                            this.$message.error(err)
                            loading.close();
                        })
                    }
                })
            },
            getVerificationCode() {
                this.$request.get('/syslogin/getVerificationCode').then(res => {
                    if (res.code === '200') {
                        this.verificationCode = res.data
                    }
                }).catch(err => {

                })
            },
            ...mapActions('user', ['setUserInfo'])
        }
    }
</script>

<style scoped>
    .container {
        height: 100vh;
        overflow: hidden;
        background-image: url("@/assets/imgs/bg.jpg");
        background-size: 100% 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #666;
    }
    a {
        color: #2a60c9;
    }
    .img-box {
        width: 52px;
    }
</style>