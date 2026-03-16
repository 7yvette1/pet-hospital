<template>
  <div class="container">

    <!-- 顶部 -->
    <div style="height: 60px; background-color: white">
      <div style="width: 60%; height: 100%; display: flex; align-items: center; margin:  0 auto">
        <img src="@/assets/imgs/宠物诊所.png" style="width: 40px">
        <span style="color:#2A60C9;font-size:24px;font-weight:bold;margin-left:10px">
          智慧宠物综合管理系统
        </span>
      </div>
    </div>

    <!-- 主体 -->
    <div style="height: calc(100vh - 60px); width: 73%; margin:  0 auto; display: flex; align-items: center">

      <!-- 左侧图片 -->
      <div style="flex: 1">
        <img src="@/assets/imgs/login.png" style="width: 90%;height: 450px;border-radius: 5px;">
      </div>

      <!-- 登录框 -->
      <div style="width: 350px; height: 420px; padding: 50px 30px;
                  box-shadow: 0 0 10px rgba(0,0,0,.1);
                  background-color: white; border-radius: 5px;">

        <div style="text-align:center;font-size:22px;margin-bottom:20px;font-weight:bold;color:#2A60C9">
          欢 迎 使 用
        </div>

        <!-- 登录方式切换 -->
        <div style="text-align:center;margin-bottom:20px">
          <el-radio-group v-model="loginType">
            <el-radio-button label="password">密码登录</el-radio-button>
            <el-radio-button label="code">验证码登录</el-radio-button>
          </el-radio-group>
        </div>

        <el-form :model="form" :rules="rules" ref="formRef">

          <!-- 密码登录 -->
          <div v-if="loginType === 'password'">

            <el-form-item prop="userName">
              <el-input prefix-icon="el-icon-user"
                        placeholder="请输入账号"
                        v-model="form.userName"/>
            </el-form-item>

            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock"
                        placeholder="请输入密码"
                        show-password
                        v-model="form.password"/>
            </el-form-item>

          </div>

          <!-- 验证码登录 -->
          <div v-if="loginType === 'code'">

            <el-form-item prop="phone">
              <el-input prefix-icon="el-icon-phone"
                        placeholder="请输入手机号"
                        v-model="form.phone"/>
            </el-form-item>

            <el-form-item prop="code">
              <div style="display:flex">
                <el-input placeholder="请输入验证码"
                          v-model="form.code"
                          style="flex:1"/>

                <el-button
                    style="margin-left:10px"
                    :disabled="countdown>0"
                    @click="sendCode">

                  {{ countdown>0 ? countdown + 's' : '获取验证码' }}

                </el-button>
              </div>
            </el-form-item>

          </div>

          <el-form-item>
            <el-button
                style="width:100%;margin-top:40px;background:#2A60C9;border-color:#2A60C9;color:white"
                @click="login">
              登 录
            </el-button>
          </el-form-item>

          <div style="display:flex">
            <div style="flex:1"></div>
            <div style="flex:1;text-align:right">
              还没有账号？请 <a href="/front/register">注册</a>
            </div>
          </div>

        </el-form>

      </div>
    </div>

  </div>
</template>

<script>
export default {

  name: "Login",

  data() {
    return {

      loginType: 'password',

      form: {},

      countdown: 0,
      timer: null,

      rules: {

        userName: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],

        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],

        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误', trigger: 'blur' }
        ],

        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]

      }

    }
  },

  methods: {

    // 登录
    login() {

      this.$refs['formRef'].validate((valid) => {

        if (!valid) return

        // 密码登录
        if (this.loginType === 'password') {

          this.$request.post('/front/login', this.form).then(res => {

            if (res.code === '200') {

              localStorage.setItem("xm-user", JSON.stringify(res.data))
              location.href = '/front/home'

              this.$message.success('登录成功')

            } else {

              this.$message.error(res.msg)

            }

          })

        }

        // 验证码登录
        if (this.loginType === 'code') {

          this.$request.post('/front/loginByCode', this.form).then(res => {

            if (res.code === '200') {

              localStorage.setItem("xm-user", JSON.stringify(res.data))
              location.href = '/front/home'

              this.$message.success('登录成功')

            } else {

              this.$message.error(res.msg)

            }

          })

        }

      })

    },

    // 发送验证码（和注册页一样）
    sendCode() {

      if (!this.form.phone) {
        this.$message.error("请输入手机号")
        return
      }

      this.$request.post('/front/sendCode', { phone: this.form.phone })
          .then(res => {

            if (res.code === '200') {

              this.$message.success("验证码已发送")

              this.countdown = 60

              this.timer = setInterval(() => {

                this.countdown--

                if (this.countdown <= 0) {
                  clearInterval(this.timer)
                }

              }, 1000)

            } else {

              this.$message.error(res.msg)

            }

          })

    }

  },

  beforeUnmount() {
    if (this.timer) clearInterval(this.timer)
  }

}
</script>

<style scoped>

.container {
  height: 100vh;
  overflow: hidden;
  background-color: #f8f8f8;
}

a {
  color: #2a60c9;
}

</style>