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
        >
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>


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


        <el-table-column
            prop="status"
            label="订购状态"
            align="center"
        >

          <template slot-scope="scope">

            <el-tag
                v-if="scope.row.status==='已订购'"
                type="success"
            >
              已订购
            </el-tag>

            <el-tag
                v-else-if="scope.row.status==='待审核'"
                type="warning"
            >
              待审核
            </el-tag>

            <el-tag
                v-else
                type="danger"
            >
              已取消
            </el-tag>

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
            width="120"
            align="center"
        >

          <template slot-scope="scope">

            <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                @click="del(scope.row.id)"
            >
              删除
            </el-button>

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

      form: {},
      fromVisible: false,

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


    del(id) {

      this.$confirm('确定删除该订购吗？', '提示', {type: 'warning'}).then(() => {

        this.$request.delete('/front/registerInfo/delete/' + id).then(res => {

          if (res.code === '200') {

            this.$message.success('删除成功')

            this.loadOrders(1)

          } else {

            this.$message.error(res.msg)

          }

        })

      })

    },


    handleCurrentChange(pageNum) {

      this.loadOrders(pageNum)

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