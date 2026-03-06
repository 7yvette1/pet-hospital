<template>

  <div class="page-container">

    <!-- 顶部统计 -->
    <div class="card-box">

      <el-card class="stat-card">
        <div class="stat-title">我的就诊记录</div>
        <div class="stat-value">{{ total }}</div>
      </el-card>

      <el-card class="stat-card">
        <div class="stat-title">最近就诊</div>
        <div class="stat-value">🐾</div>
      </el-card>

    </div>


    <!-- 主卡片 -->
    <el-card class="main-card">

      <div class="header">

        <div class="title">
          <i class="el-icon-document"></i>
          我的宠物就诊记录
        </div>

      </div>


      <!-- 表格 -->
      <el-table
          :data="tableData"
          stripe
          border
          highlight-current-row
          class="pet-table"
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
            prop="name"
            label="宠物姓名"
            align="center"
        >
          <template slot-scope="scope">
            <span class="pet-name">
              🐾 {{ scope.row.name }}
            </span>
          </template>
        </el-table-column>


        <el-table-column
            prop="gender"
            label="宠物性别"
            width="120"
            align="center"
        >

          <template slot-scope="scope">

            <el-tag
                v-if="scope.row.gender==='0'"
                type="success"
                effect="dark"
            >
              公
            </el-tag>

            <el-tag
                v-else
                type="danger"
                effect="dark"
            >
              母
            </el-tag>

          </template>

        </el-table-column>


        <el-table-column
            prop="age"
            label="宠物年龄"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.age }} 岁
          </template>
        </el-table-column>


        <el-table-column
            prop="phone"
            label="联系电话"
            align="center"
            show-overflow-tooltip
        />


        <el-table-column
            prop="visitDate"
            label="就诊日期"
            align="center"
        />


        <el-table-column
            prop="docName"
            label="接诊医生"
            align="center"
        />


        <el-table-column
            prop="medicalInfo"
            label="病因"
            show-overflow-tooltip
        />


        <el-table-column
            prop="diagnosis"
            label="诊断记录"
            show-overflow-tooltip
        />


        <el-table-column
            prop="adviceInfo"
            label="医嘱"
            show-overflow-tooltip
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

  name: "MyMedicalCase",

  data() {

    return {

      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),

      tableData: [],

      pageNum: 1,
      pageSize: 10,
      total: 0,

      name: null,

      ids: []

    }

  },

  created() {

    this.load(1)

  },

  methods: {

    del(id) {

      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(() => {

        this.$request.delete('/medicalCase/delete/' + id).then(res => {

          if (res.code === '200') {

            this.$message.success('删除成功')

            this.load(1)

          } else {

            this.$message.error(res.msg)

          }

        })

      })

    },


    load(pageNum) {

      if (pageNum) this.pageNum = pageNum

      this.$request.get('/front/medicalCase/selectMyPage', {

        params: {

          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          userId: this.user.userId,

        }

      }).then(res => {

        this.tableData = res.data?.list

        this.total = res.data?.total

      })

    },


    handleCurrentChange(pageNum) {

      this.load(pageNum)

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

.pet-table{
  border-radius:8px;
}

.pet-name{
  font-weight:600;
  color:#409EFF;
}


/* 分页 */

.pagination{
  margin-top:25px;
  text-align:center;
}

</style>