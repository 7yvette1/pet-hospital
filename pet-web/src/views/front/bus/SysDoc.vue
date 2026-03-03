<template>
    <div class="main-content">
        <div>
            <el-row :gutter="10">
              <el-col :span="6" v-for="item in newsInfoList" :key="item.userId">
                <div class="doctor-card"
                     @click="$router.push('/front/sysDocDetail?id=' + item.userId)">

                  <img :src="item.avatar" class="doctor-img">

                  <div class="doctor-body">
                    <div class="doctor-name">
                      <el-tag>{{ item.nickName }}</el-tag>
                    </div>

                    <div class="doctor-desc">
                      {{ item.descr }}
                    </div>
                  </div>

                </div>
              </el-col>
            </el-row>
        </div>

        <div style="margin: 10px 0" v-if="total">
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
</template>

<script>
    export default {
        name: "SysDoc",
        data() {
            return {
                newsInfoList: [],
                pageNum: 1,
                pageSize: 8,
                total: 0
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/sysuser/selectDocPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                    }
                }).then(res => {
                    this.newsInfoList = res.data?.list
                    this.total = res.data?.total
                })
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>
.main-content {
  padding: 20px 0;
}

.doctor-card {
  margin-bottom: 20px;
  cursor: pointer;
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  height: 100%;
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
}

.doctor-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 10px 25px rgba(0,0,0,0.12);
}

.doctor-img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.doctor-body {
  padding: 15px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.doctor-name {
  text-align: center;
  margin-bottom: 10px;
}

.doctor-desc {
  font-size: 14px;
  color: #666;
  line-height: 22px;

  display: -webkit-box;
  -webkit-line-clamp: 3;   /* 最多3行 */
  -webkit-box-orient: vertical;
  overflow: hidden;
}

</style>

