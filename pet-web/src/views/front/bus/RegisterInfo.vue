<template>
  <div class="main-content" style="width: 80%">

    <div class="card">

      <div style="font-size:18px;font-weight:bold;margin-bottom:20px">
        我的订单
      </div>

      <el-table :data="tableData" stripe>

        <el-table-column prop="orderNo" label="订单号" width="200"/>

        <el-table-column prop="clinicName" label="服务名称"/>

        <el-table-column prop="createTime" label="下单时间" width="180"/>

        <el-table-column label="订单状态" width="120">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status=='0'" type="warning">待支付</el-tag>
            <el-tag v-if="scope.row.status=='1'" type="primary">已支付</el-tag>
            <el-tag v-if="scope.row.status=='2'" type="info">已接单</el-tag>
            <el-tag v-if="scope.row.status=='3'">服务中</el-tag>
            <el-tag v-if="scope.row.status=='4'" type="success">已完成</el-tag>
            <el-tag v-if="scope.row.status=='5'" type="danger">已取消</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="220">

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

      <div style="margin-top:20px;text-align:right">

        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="pageSize"
            :current-page="pageNum"
            :total="total"
            @current-change="handleCurrentChange">
        </el-pagination>

      </div>

    </div>

  </div>
</template>

<script>

export default {

  name: "RegisterInfo",

  data() {

    return {

      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),

      tableData: [],

      pageNum: 1,

      pageSize: 10,

      total: 0

    }

  },

  created() {

    this.load()

  },

  methods: {

    load(){

      this.$request.get("/front/registerInfo/selectPage1",{

        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          userId:this.user.userId
        }

      }).then(res=>{

        this.tableData = res.data.list

        this.total = res.data.total

      })

    },

    handleCurrentChange(pageNum){

      this.pageNum = pageNum

      this.load()

    },

    pay(id){

      this.$confirm('确认支付该订单吗？','提示',{type:"warning"}).then(()=>{

        this.$request.put("/front/registerInfo/pay/"+id).then(res=>{

          if(res.code==='200'){

            this.$message.success("支付成功")

            this.load()

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },

    cancel(id){

      this.$confirm('确认取消订单吗？','提示',{type:"warning"}).then(()=>{

        this.$request.put("/front/registerInfo/cancel/"+id).then(res=>{

          if(res.code==='200'){

            this.$message.success("订单已取消")

            this.load()

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    }

  }

}

</script>