<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 左侧个人信息 -->
      <el-col :span="8" :xs="24">
        <el-card class="box-card">
          <div slot="header">
            <span>个人信息</span>
          </div>

          <div class="user-info-head" @click="editAvatar">
            <img :src="user.avatar" class="img-circle img-lg"/>
          </div>

          <ul class="list-group list-group-striped">
            <li class="list-group-item">用户ID <span class="pull-right">{{ user.userId }}</span></li>
            <li class="list-group-item">用户名称 <span class="pull-right">{{ user.userName }}</span></li>
            <li class="list-group-item">用户昵称 <span class="pull-right">{{ user.nickName }}</span></li>
            <li class="list-group-item">手机号码 <span class="pull-right">{{ user.phone }}</span></li>
            <li class="list-group-item">用户邮箱 <span class="pull-right">{{ user.email }}</span></li>
            <li class="list-group-item">创建日期 <span class="pull-right">{{ user.createTime }}</span></li>
          </ul>
        </el-card>
      </el-col>

      <!-- 右侧编辑 -->
      <el-col :span="16" :xs="24">
        <el-card>
          <div slot="header">
            <span>基本资料</span>
          </div>

          <el-tabs v-model="activeTab">

            <!-- 基本资料 -->
            <el-tab-pane label="基本资料" name="userinfo">
              <el-form ref="form" :model="user" :rules="rules" label-width="80px">
                <el-form-item label="手机号码" prop="phone">
                  <el-input v-model="user.phone" maxlength="11"/>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="user.email" maxlength="50"/>
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

            <!-- 修改密码 -->
            <el-tab-pane label="修改密码" name="resetPwd">
              <el-form ref="form1" :model="user" :rules="rules" label-width="80px">
                <el-form-item label="旧密码" prop="password">
                  <el-input v-model="user.password" type="password" show-password/>
                </el-form-item>

                <el-form-item label="新密码" prop="newPassword">
                  <el-input v-model="user.newPassword" type="password" show-password/>
                </el-form-item>

                <el-form-item label="确认密码" prop="confirmPassword">
                  <el-input v-model="user.confirmPassword" type="password" show-password/>
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

    <!-- 头像弹窗 -->
    <el-dialog title="用户头像"
               :visible.sync="fromVisible"
               width="40%"
               :close-on-click-modal="false">

      <el-image style="width:200px;height:200px;display:block;margin:0 auto;"
                :src="user.avatar"
                :preview-src-list="[user.avatar]">
      </el-image>

      <div class="upload-btns">
        <el-upload
            :action="$baseUrl + '/files/upload'"
            :on-success="handleAvatarSuccess"
            :show-file-list="false">
          <el-button type="primary" size="small">
            选择 <i class="el-icon-upload el-icon--right"></i>
          </el-button>
        </el-upload>

        <el-button type="success" size="small" @click="uploadImg">
          提交
        </el-button>
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
      fromVisible: false,

      rules: {
        phone: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          { pattern: /^1[3-9]\d{9}$/, message: "请输入正确手机号", trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱不能为空", trigger: "blur" },
          { type: "email", message: "邮箱格式不正确", trigger: ["blur","change"] }
        ],
        password: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "6-20位字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value !== this.user.newPassword) {
                callback(new Error("两次输入密码不一致"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ]
      }
    }
  },

  created() {
    this.getUser();
  },

  methods: {

    getUser() {
      if (!this.user || !this.user.userId) {
        this.$message.warning('请先登录');
        this.$router.push('/front/login');
        return;
      }

      this.$request.get('/front/selectById/' + this.user.userId)
          .then(res => {
            if (res.code === '200') {
              this.user = res.data;
            }
          });
    },

    submit1() {
      this.$refs.form.validate(valid => {
        if (!valid) return;

        this.$request({
          url: '/front/updateById',
          method: 'PUT',
          data: this.user
        }).then(res => {
          if (res.code === '200') {
            this.$message.success('保存成功');
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    submit2() {

      this.$refs.form1.validate(valid => {
        if (!valid) return;

        this.$request({
          url: '/front/updatePassword',
          method: 'PUT',
          data: this.user
        }).then(res => {
          if (res.code === '200') {
            this.$message.success('密码修改成功，请重新登录');
            localStorage.removeItem("xm-user");
            this.$router.push('/front/login');
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    editAvatar() {
      this.fromVisible = true;
    },

    handleAvatarSuccess(response) {
      this.user.avatar = response.data;
    },

    uploadImg() {
      this.$request({
        url: '/front/updateById',
        method: 'PUT',
        data: this.user
      }).then(res => {
        if (res.code === '200') {

          let userInfo = JSON.parse(localStorage.getItem('xm-user'));
          userInfo.avatar = this.user.avatar;
          localStorage.setItem('xm-user', JSON.stringify(userInfo));

          this.$message.success('头像更新成功');
          this.fromVisible = false;
          this.getUser();
        } else {
          this.$message.error(res.msg);
        }
      });
    }

  }
}
</script>

<style scoped>

.app-container {
  width: 70%;
  margin: 20px auto;
}

.user-info-head {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  cursor: pointer;
}

.img-circle {
  border-radius: 50%;
}

.img-lg {
  width: 100px;
  height: 100px;
}

.list-group-item {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
  font-size: 13px;
}

.pull-right {
  float: right;
}

.upload-btns {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

</style>