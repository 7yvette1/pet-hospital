<template>

  <div class="page-container">

    <!-- 顶部统计卡片 -->
    <div class="card-box">

      <el-card class="stat-card blue">
        <div class="stat-title">我的宠物数量</div>
        <div class="stat-value">{{ total }}</div>
      </el-card>

      <el-card class="stat-card green">
        <div class="stat-title">健康状态</div>
        <div class="stat-value">良好</div>
      </el-card>

    </div>


    <!-- 主卡片 -->
    <el-card class="main-card">

      <div class="header">

        <div class="title">
          <i class="el-icon-s-order"></i>
          我的宠物健康档案
        </div>

        <el-button
            type="primary"
            icon="el-icon-plus"
            @click="add"
        >
          新增档案
        </el-button>

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
            width="60"
            align="center"
        />

        <el-table-column
            prop="petName"
            label="宠物名字"
            align="center"
        >
          <template slot-scope="scope">
            <span class="pet-name">
              🐾 {{ scope.row.petName }}
            </span>
          </template>
        </el-table-column>

        <el-table-column
            prop="petType"
            label="宠物类型"
            align="center"
        />

        <el-table-column
            prop="petGender"
            label="性别"
            align="center"
        >
          <template slot-scope="scope">

            <el-tag
                v-if="scope.row.petGender==='公'"
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
            prop="petAge"
            label="年龄"
            align="center"
        >
          <template slot-scope="scope">
            {{ scope.row.petAge }} 岁
          </template>
        </el-table-column>

        <el-table-column
            prop="weight"
            label="体重"
            align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.weight">
              {{ scope.row.weight }} kg
            </span>
            <span v-else>-</span>
          </template>
        </el-table-column>

        <el-table-column
            prop="healthStatus"
            label="健康状态"
            align="center"
        >

          <template slot-scope="scope">

            <el-tag
                v-if="scope.row.healthStatus==='健康'"
                type="success"
            >
              健康
            </el-tag>

            <el-tag
                v-else-if="scope.row.healthStatus==='生病'"
                type="danger"
            >
              生病
            </el-tag>

            <el-tag
                v-else
                type="warning"
            >
              观察
            </el-tag>

          </template>

        </el-table-column>

        <el-table-column
            prop="vaccineRecord"
            label="疫苗记录"
            show-overflow-tooltip
        />

        <el-table-column
            prop="medicalHistory"
            label="既往病史"
            show-overflow-tooltip
        />
<!--
        <el-table-column
            prop="createTime"
            label="创建时间"
            width="180"
            align="center"
        />-->

        <!-- 操作 -->
        <el-table-column
            label="操作"
            width="180"
            align="center"
        >

          <template slot-scope="scope">

            <div class="operation-btn">

              <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-edit"
                  @click="edit(scope.row)"
              >
                编辑
              </el-button>

              <el-button
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="del(scope.row.id)"
              >
                删除
              </el-button>

            </div>

          </template>

        </el-table-column>

      </el-table>


      <!-- 分页 -->
      <div class="pagination">

        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="pageNum"
            :page-size="pageSize"
            :total="total"
            @current-change="handleCurrentChange"
        />

      </div>

    </el-card>


    <!-- 弹窗 -->
    <el-dialog
        title="宠物健康档案"
        :visible.sync="dialogVisible"
        width="520px"
        class="pet-dialog"
    >

      <el-form
          :model="form"
          label-width="100px"
      >

        <el-form-item label="宠物名字">
          <el-input v-model="form.petName"/>
        </el-form-item>

        <el-form-item label="宠物类型">
          <el-input v-model="form.petType"/>
        </el-form-item>

        <el-form-item label="宠物性别">
          <el-select v-model="form.petGender">
            <el-option label="公" value="公"/>
            <el-option label="母" value="母"/>
          </el-select>
        </el-form-item>

        <el-form-item label="宠物年龄">
          <el-input-number v-model="form.petAge" :min="0"/>
        </el-form-item>

        <el-form-item label="体重(kg)">
          <el-input-number
              v-model="form.weight"
              :min="0"
              :precision="1"
              :step="0.1"
          />
        </el-form-item>

        <el-form-item label="健康状态">
          <el-select v-model="form.healthStatus">
            <el-option label="健康" value="健康"/>
            <el-option label="生病" value="生病"/>
            <el-option label="观察" value="观察"/>
          </el-select>
        </el-form-item>

        <el-form-item label="疫苗记录">
          <el-input
              type="textarea"
              v-model="form.vaccineRecord"
          />
        </el-form-item>

        <el-form-item label="既往病史">
          <el-input
              type="textarea"
              v-model="form.medicalHistory"
          />
        </el-form-item>

      </el-form>

      <span slot="footer">

        <el-button @click="dialogVisible=false">
          取消
        </el-button>

        <el-button
            type="primary"
            @click="save"
        >
          保存
        </el-button>

      </span>

    </el-dialog>

  </div>

</template>


<script>

export default {

  name:"MyPetHealth",

  data(){

    return{

      user:JSON.parse(localStorage.getItem('xm-user')||'{}'),

      tableData:[],

      pageNum:1,
      pageSize:10,
      total:0,

      dialogVisible:false,

      form:{}

    }

  },

  created(){

    this.load(1)

  },

  methods:{

    add(){

      this.form={}
      this.dialogVisible=true

    },

    edit(row){

      this.form=JSON.parse(JSON.stringify(row))
      this.dialogVisible=true

    },

    save(){

      this.form.userId=this.user.userId

      if(this.form.id){

        this.$request.put('/front/petHealthRecord/update',this.form).then(res=>{

          if(res.code==='200'){

            this.$message.success("修改成功")
            this.dialogVisible=false
            this.load(1)

          }

        })

      }else{

        this.$request.post('/front/petHealthRecord/add',this.form).then(res=>{

          if(res.code==='200'){

            this.$message.success("新增成功")
            this.dialogVisible=false
            this.load(1)

          }

        })

      }

    },

    del(id){

      this.$confirm('确定删除吗？','提示',{type:'warning'}).then(()=>{

        this.$request.delete('/front/petHealthRecord/delete/'+id).then(res=>{

          if(res.code==='200'){

            this.$message.success("删除成功")
            this.load(1)

          }

        })

      })

    },

    load(pageNum){

      if(pageNum) this.pageNum=pageNum

      this.$request.get('/front/petHealthRecord/selectMyPage',{

        params:{

          pageNum:this.pageNum,
          pageSize:this.pageSize,
          userId:this.user.userId

        }

      }).then(res=>{

        this.tableData=res.data?.list
        this.total=res.data?.total

      })

    },

    handleCurrentChange(pageNum){

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
  color:white;
}

.stat-card.blue{
  background:linear-gradient(135deg,#409EFF,#66b1ff);
}

.stat-card.green{
  background:linear-gradient(135deg,#67C23A,#95d475);
}

.stat-title{
  opacity:0.9;
}

.stat-value{
  font-size:30px;
  font-weight:bold;
  margin-top:10px;
}


/* 主卡片 */

.main-card{
  border-radius:12px;
}


/* 头部 */

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
  border-radius:10px;
}

.pet-name{
  font-weight:600;
  color:#409EFF;
}


/* 操作按钮 */

.operation-btn{
  display:flex;
  justify-content:center;
  gap:10px;
}


/* 分页 */

.pagination{
  margin-top:25px;
  text-align:center;
}


/* 弹窗 */

.pet-dialog >>> .el-dialog{
  border-radius:10px;
}

</style>