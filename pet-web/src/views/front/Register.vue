<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">
        欢迎注册
      </div>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="auto">

        <el-form-item prop="userName" label="账号:">
          <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.userName"/>
        </el-form-item>

        <el-form-item prop="nickName" label="昵称:">
          <el-input prefix-icon="el-icon-user" placeholder="请输入昵称" v-model="form.nickName"/>
        </el-form-item>

        <el-form-item prop="password" label="密码:">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="form.password"/>
        </el-form-item>

        <el-form-item prop="confirmPass" label="确认密码:">
          <el-input prefix-icon="el-icon-lock" placeholder="请确认密码" show-password v-model="form.confirmPass"/>
        </el-form-item>

        <el-form-item prop="phone" label="电话:">
          <el-input prefix-icon="el-icon-phone" placeholder="请输入电话" v-model="form.phone"/>
        </el-form-item>

        <!-- 验证码 -->
        <el-form-item prop="code" label="验证码:">
          <div style="display: flex">
            <el-input placeholder="请输入验证码" v-model="form.code" style="flex: 1"/>
            <el-button style="margin-left: 10px" :disabled="countdown > 0" @click="sendCode">
              {{ countdown > 0 ? countdown + 's' : '获取验证码' }}
            </el-button>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="register">
            注 册
          </el-button>
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
    const validatePassword = (rule, confirmPass, callback) => {
      if (!confirmPass) {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    return {
      form: {},
      countdown: 0,
      timer: null,
      rules: {
        userName: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPass: [{ validator: validatePassword, trigger: 'blur' }],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误', trigger: 'blur' }
        ],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      }
    }
  },

  methods: {

    // 发送短信认证服务验证码
    sendCode() {
      if (!this.form.phone) {
        this.$message.error("请输入手机号");
        return;
      }

      this.$request.post('/front/sendCode', { phone: this.form.phone })
          .then(res => {
            if (res.code === '200') {
              this.$message.success("验证码已发送");

              this.countdown = 60;
              this.timer = setInterval(() => {
                this.countdown--;
                if (this.countdown <= 0) {
                  clearInterval(this.timer);
                }
              }, 1000);

            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(err => {
            this.$message.error("验证码发送异常");
          });
    },

    // 注册
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          this.$request.post('/front/register', this.form)
              .then(res => {
                if (res.code === '200') {
                  this.$message.success('注册成功');
                  this.$router.push('/front/login');
                } else {
                  this.$message.error(res.msg);
                }
              })
              .catch(err => {
                this.$message.error("注册异常");
              });
        }
      });
    }
  },

  beforeUnmount() {
    if (this.timer) clearInterval(this.timer);
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