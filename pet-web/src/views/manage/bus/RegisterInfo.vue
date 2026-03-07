<template>
  <div>

    <!-- 搜索 -->
    <div class="search">
      <el-input placeholder="请输入服务名称查询"
                style="width:200px"
                v-model="clinicName">
      </el-input>

      <el-button type="primary"
                 icon="el-icon-search"
                 style="margin-left:10px"
                 @click="load(1)">
        查询
      </el-button>

      <el-button type="warning"
                 icon="el-icon-refresh"
                 style="margin-left:10px"
                 @click="reset">
        重置
      </el-button>
    </div>

    <!-- 操作 -->
    <div class="operation">
      <el-button type="danger"
                 icon="el-icon-delete"
                 plain
                 @click="delBatch">
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table">

      <el-table
          :data="tableData"
          stripe
          @selection-change="handleSelectionChange">

        <el-table-column type="selection"
                         width="55"
                         align="center">
        </el-table-column>

        <el-table-column type="index"
                         label="序号"
                         width="80"
                         align="center">
        </el-table-column>

        <el-table-column prop="userName"
                         label="订购用户"
                         align="center">
        </el-table-column>

        <el-table-column prop="clinicName"
                         label="订购服务"
                         align="center">
        </el-table-column>

        <el-table-column prop="createTime"
                         label="下单时间"
                         align="center">
        </el-table-column>

        <!-- 状态 -->
        <el-table-column label="订单状态"
                         align="center">

          <template slot-scope="scope">

            <el-tag v-if="scope.row.status=='0'" type="warning">
              待支付
            </el-tag>

            <el-tag v-if="scope.row.status=='1'" type="primary">
              已支付
            </el-tag>

            <el-tag v-if="scope.row.status=='2'" type="info">
              已接单
            </el-tag>

            <el-tag v-if="scope.row.status=='3'">
              服务中
            </el-tag>

            <el-tag v-if="scope.row.status=='4'" type="success">
              已完成
            </el-tag>

            <el-tag v-if="scope.row.status=='5'" type="danger">
              已取消
            </el-tag>

          </template>

        </el-table-column>

        <!-- 操作 -->
        <el-table-column label="操作"
                         width="260"
                         align="center">

          <template slot-scope="scope">

            <el-button
                v-if="scope.row.status=='1'"
                type="primary"
                size="mini"
                @click="accept(scope.row.id)">
              接单
            </el-button>

            <el-button
                v-if="scope.row.status=='2'"
                type="warning"
                size="mini"
                @click="start(scope.row.id)">
              开始服务
            </el-button>

            <el-button
                v-if="scope.row.status=='3'"
                type="success"
                size="mini"
                @click="finish(scope.row.id)">
              完成服务
            </el-button>

            <el-button
                type="danger"
                size="mini"
                @click="del(scope.row.id)">
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
            :total="total">
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

      tableData: [],

      pageNum: 1,

      pageSize: 10,

      total: 0,

      clinicName: null,

      ids: []

    }

  },

  created() {

    this.load(1)

  },

  methods: {

    load(pageNum){

      if(pageNum) this.pageNum = pageNum

      this.$request.get("/registerInfo/selectPage",{

        params:{

          pageNum:this.pageNum,

          pageSize:this.pageSize,

          clinicName:this.clinicName

        }

      }).then(res=>{

        this.tableData = res.data?.list

        this.total = res.data?.total

      })

    },

    reset(){

      this.clinicName = null

      this.load(1)

    },

    handleCurrentChange(pageNum){

      this.load(pageNum)

    },

    handleSelectionChange(rows){

      this.ids = rows.map(v=>v.id)

    },

    del(id){

      this.$confirm("确认删除吗？","提示",{type:"warning"}).then(()=>{

        this.$request.delete("/registerInfo/delete/"+id).then(res=>{

          if(res.code==="200"){

            this.$message.success("删除成功")

            this.load(1)

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },

    delBatch(){

      if(!this.ids.length){

        this.$message.warning("请选择数据")

        return

      }

      this.$confirm("确认批量删除吗？","提示",{type:"warning"}).then(()=>{

        this.$request.delete("/registerInfo/delete/batch",{data:this.ids}).then(res=>{

          if(res.code==="200"){

            this.$message.success("删除成功")

            this.load(1)

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },

    accept(id){

      this.$confirm("确认接单吗？","提示",{type:"warning"}).then(()=>{

        this.$request.put("/registerInfo/accept/"+id).then(res=>{

          if(res.code==="200"){

            this.$message.success("接单成功")

            this.load(1)

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },

    start(id){

      this.$confirm("确认开始服务吗？","提示",{type:"warning"}).then(()=>{

        this.$request.put("/registerInfo/start/"+id).then(res=>{

          if(res.code==="200"){

            this.$message.success("服务开始")

            this.load(1)

          }else{

            this.$message.error(res.msg)

          }

        })

      })

    },

    finish(id){

      this.$confirm("确认完成服务吗？","提示",{type:"warning"}).then(()=>{

        this.$request.put("/registerInfo/finish/"+id).then(res=>{

          if(res.code==="200"){

            this.$message.success("服务完成")

            this.load(1)

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

.search{
  margin-bottom:20px;
}

.operation{
  margin-bottom:15px;
}

.pagination{
  margin-top:20px;
  text-align:right;
}

</style>