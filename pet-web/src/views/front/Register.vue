<template>
    <div class="container">
        <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
            <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">欢迎注册</div>
            <el-form :model="form" :rules="rules" ref="formRef" label-width="auto">
                <el-form-item prop="userName" label="账号:">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.userName"></el-input>
                </el-form-item>
                <el-form-item prop="nickName" label="呢称:">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入呢称" v-model="form.nickName"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码:">
                    <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPass" label="确认密码:">
                    <el-input prefix-icon="el-icon-lock" placeholder="请确认密码" show-password  v-model="form.confirmPass"></el-input>
                </el-form-item>
                <el-form-item prop="phone" label="电话:">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入电话" v-model="form.phone"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="邮箱:">
                    <el-input prefix-icon="el-icon-user" placeholder="请输入邮箱" v-model="form.email"></el-input>
                </el-form-item>
                <el-form-item label="性别:">
                    <el-radio-group v-model="form.sex">
                        <el-radio label="0">男</el-radio>
                        <el-radio label="1">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 78%; background-color: #333; border-color: #333; color: white" @click="register">注 册</el-button>
                </el-form-item>
                <div style="display: flex; align-items: center">
                    <div style="flex: 1"></div>
                    <div style="flex: 1; text-align: right">
                        已有账号？请 <a href="/front/login">登录</a>
                    </div>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Register",
        data() {
            // 验证码校验
            const validatePassword = (rule, confirmPass, callback) => {
                if (confirmPass === '') {
                    callback(new Error('请确认密码'))
                } else if (confirmPass !== this.form.password) {
                    callback(new Error('两次输入的密码不一致'))
                } else {
                    callback()
                }
            }
            return {
                form: {},
                rules: {
                    userName: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                    ],
                    nickName: [
                        { required: true, message: '请输入呢称', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ],
                    confirmPass: [
                        { validator: validatePassword, trigger: 'blur' }
                    ]
                }
            }
        },
        created() {

        },
        methods: {
            register() {
                this.$refs['formRef'].validate((valid) => {
                    if (valid) {
                        // 验证通过
                        this.$request.post('/front/register', this.form).then(res => {
                            if (res.code === '200') {
                                this.$router.push('/front/login')  // 跳转登录页面
                                this.$message.success('注册成功')
                            } else {
                                this.$message.error(res.msg)
                            }
                        })
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .container {
        height: 100vh;
        overflow: hidden;
        background-image: url("@/assets/imgs/register.jpg");
        background-size: 100% 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: #666;
    }
    a {
        color: #2a60c9;
    }
</style>