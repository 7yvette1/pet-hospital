<template>
  <div class="main-content" style="width: 70%">

    <div class="card" style="margin-bottom: 28px;">
      <div style="display: flex; grid-gap: 20px; height: 350px;">

        <img :src="news.img" alt="" style="width: 50%">

        <div style="flex: 1">

          <div style="font-size: 18px; font-weight: bold; margin-bottom: 40px">
            {{news.name}}
          </div>

          <div class="item">
            <dl>
              <dt style="color:#333; float:left;">描述：</dt>
              <dd style="margin-left:40px">
                <div v-html="news.content"></div>
              </dd>
            </dl>
          </div>

          <div style="margin-top: 30px; margin-left: 18px">
            <el-button
                type="primary"
                size="medium"
                @click="addOrder">
              订购服务
            </el-button>
          </div>

        </div>
      </div>
    </div>

    <div style="margin: 50px 0;">
      <el-tabs type="border-card">
        <el-tab-pane label="特色服务" style="font-size: 15px; line-height: 1.8;">

          1. 24小时宠物急诊服务：24小时不间断服务，方便客户应对夜间急诊需求。

          <br>2. 时时刻刻为爱宠健康保驾护航：每日定时医生查房、24小时看护、犬猫独立分区。

          <br>3. 按照国际宠物医疗标准，每位医生经过严格专业的考核培训。

        </el-tab-pane>
      </el-tabs>
    </div>

  </div>
</template>

<script>

export default {

  name: "BusCategoryDetail",

  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      id: this.$route.query.id,
      news: {}
    }
  },

  created() {
    this.load()
  },

  methods: {

    load() {

      this.$request.get('/front/buscategory/selectById/' + this.id).then(res => {

        this.news = res.data || {}

      })

    },

    addOrder(){

      if (!this.user.userId){
        this.$message.warning('请先登录')
        return
      }

      let data = {

        userId: this.user.userId,

        clinicId: this.id

      }

      this.$confirm('确定订购该服务吗？', '提示', {

        confirmButtonText: '确定',

        cancelButtonText: '取消',

        type: 'warning'

      }).then(() => {

        this.$request.post('/front/registerInfo/add', data).then(res => {

          if (res.code === '200') {

            this.$message.success('订单创建成功，请前往我的订单支付')

            //跳转订单页面
            this.$router.push('/front/registerInfo')

          } else {

            this.$message.error(res.msg)

          }

        })

      }).catch(() => {})

    }

  }

}
</script>

<style scoped>

.item{
  color:#666;
  margin-bottom:20px;
  font-size:14px;
  padding-left:20px;
}

</style>