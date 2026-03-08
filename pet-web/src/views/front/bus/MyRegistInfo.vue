<template>

  <div class="page-container">

    <!-- 顶部统计 -->
    <div class="card-box">

      <el-card class="stat-card">
        <div class="stat-title">我的订购数量</div>
        <div class="stat-value">{{ total }}</div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-title">订购状态</div>
        <div class="stat-value">📅</div>
      </el-card>

    </div>


    <!-- 主卡片 -->
    <el-card class="main-card">

      <div class="header">

        <div class="title">
          <i class="el-icon-date"></i>
          我的订单管理
        </div>

      </div>


      <!-- 表格 -->
      <el-table
          :data="ordersData"
          stripe
          border
          highlight-current-row
          class="order-table"
      >

        <el-table-column
            type="index"
            label="序号"
            width="70"
            align="center"
        />


        <el-table-column
            prop="clinicName"
            label="订购服务"
            align="center"
            show-overflow-tooltip
        />


        <el-table-column
            prop="docName"
            label="服务人员"
            align="center"
        >
          <template slot-scope="scope">
            👨‍⚕️ {{ scope.row.docName }}
          </template>
        </el-table-column>


        <el-table-column
            prop="userName"
            label="订购用户"
            align="center"
        />


        <!-- 状态 -->
        <el-table-column
            label="订购状态"
            align="center"
        >

          <template slot-scope="scope">
            <el-tag v-if="scope.row.status=='0'" type="warning">待支付</el-tag>
            <el-tag v-if="scope.row.status=='1'" type="primary">已支付</el-tag>
            <el-tag v-if="scope.row.status=='2'" type="info">已接单</el-tag>
            <el-tag v-if="scope.row.status=='3'">服务中</el-tag>
            <el-tag v-if="scope.row.status=='4'" type="success">已完成</el-tag>
            <el-tag v-if="scope.row.status=='5'" type="danger">已取消</el-tag>
          </template>

        </el-table-column>


        <el-table-column
            prop="time"
            label="订购时间"
            align="center"
        />


        <!-- 操作 -->
        <el-table-column
            label="操作"
            width="220"
            align="center"
        >

          <template slot-scope="scope">

            <el-button
                v-if="scope.row.status=='0'"
                type="success"
                size="mini"
                @click="pay(scope.row.id)">
              支付
            </el-button>

            <el-button
                v-if="scope.row.status=='0'"
                type="danger"
                size="mini"
                @click="cancel(scope.row.id)">
              取消
            </el-button>

            <span v-if="scope.row.status=='1'">等待接单</span>

            <span v-if="scope.row.status=='2'">等待服务</span>

            <span v-if="scope.row.status=='3'">服务进行中</span>

            <span v-if="scope.row.status=='4'">订单完成</span>

            <span v-if="scope.row.status=='5'">订单已取消</span>

          </template>

        </el-table-column>

      </el-table>


      <!-- 分页 -->
      <div class="pagination">

        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total"
        />

      </div>

    </el-card>

  </div>

</template>


<script>

export default {

  name: "MyOrders",

  data() {

    return {

      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),

      ordersData: [],

      pageNum: 1,
      pageSize: 10,
      total: 0,

    }

  },

  mounted() {

    this.loadOrders(1)

  },

  methods: {

    loadOrders(pageNum) {

      if (pageNum) this.pageNum = pageNum

      this.$request.get('/front/registerInfo/selectPage', {

        params: {

          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.userId,

        }

      }).then(res => {

        if (res.code === '200') {

          this.ordersData = res.data?.list

          this.total = res.data?.total

        } else {

          this.$message.error(res.msg)

        }

      })

    },


    handleCurrentChange(pageNum) {

      this.pageNum = pageNum

      this.loadOrders()

    },


    // 支付
    pay(id){

      this.$confirm('确认支付该订单吗？','提示',{type:"warning"}).then(()=>{

        this.$request.put("/front/registerInfo/pay/"+id).then(res=>{

          if(res.code==='200'){

            this.$message.success("支付成功")

            this.loadOrders()

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },


    // 取消
    cancel(id){

      this.$confirm('确认取消订单吗？','提示',{type:"warning"}).then(()=>{

        this.$request.put("/front/registerInfo/cancel/"+id).then(res=>{

          if(res.code==='200'){

            this.$message.success("订单已取消")

            this.loadOrders()

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    }

  }

}

</script>



<style scoped>

.page-container{
  width:90%;
  margin:30px auto;
}


/* 顶部卡片 */

.card-box{
  display:flex;
  gap:20px;
  margin-bottom:20px;
}

.stat-card{
  flex:1;
  text-align:center;
}

.stat-title{
  color:#999;
}

.stat-value{
  font-size:28px;
  font-weight:bold;
  margin-top:10px;
}


/* 主卡片 */

.main-card{
  border-radius:10px;
}


/* 标题 */

.header{
  display:flex;
  justify-content:space-between;
  align-items:center;
  margin-bottom:20px;
}

.title{
  font-size:20px;
  font-weight:600;
}

.title i{
  color:#409EFF;
  margin-right:5px;
}


/* 表格 */

.order-table{
  border-radius:8px;
}


/* 分页 */

.pagination{
  margin-top:25px;
  text-align:center;
}

</style>