<template>
  <div class="main-content">

    <!-- 服务类型切换 -->
    <div style="margin-bottom: 15px; text-align: center;">
      <el-button
          :type="serviceType === '' ? 'primary' : 'default'"
          @click="changeServiceType('')"
      >全部</el-button>
      <el-button
          :type="serviceType === 'medical' ? 'primary' : 'default'"
          @click="changeServiceType('medical')"
      >医疗</el-button>
      <el-button
          :type="serviceType === 'beauty' ? 'primary' : 'default'"
          @click="changeServiceType('beauty')"
      >美容</el-button>
      <el-button
          :type="serviceType === 'boarding' ? 'primary' : 'default'"
          @click="changeServiceType('boarding')"
      >寄养</el-button>
    </div>

    <div>
      <el-row :gutter="10">
        <el-col :span="6" v-for="item in newsInfoList" :key="item.id">
          <div
              style="margin-bottom: 10px; cursor: pointer;"
              @click="$router.push('/front/busCategoryDetail?id=' + item.id)"
          >
            <img :src="item.img" alt="" style="width: 100%; height: 190px; display: block; border-radius: 5px 5px 0 0;">
            <div style="padding: 10px; background-color: #fff; box-shadow: -3px 3px 3px -2px rgba(0,0,0,0.1), 3px 3px 3px -2px rgba(0,0,0,0.1);">
              <div class="line2" style="height: 20px; margin-bottom: 5px; text-align: center">
                <el-tag>{{ item.name }}</el-tag>
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
          :total="total"
      >
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "BusCategory",
  data() {
    return {
      newsInfoList: [],
      pageNum: 1,
      pageSize: 8,
      total: 0,
      serviceType: ''   // 当前服务类型
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    // 切换服务类型
    changeServiceType(type) {
      this.serviceType = type
      this.pageNum = 1
      this.load(1)
    },

    // 分页查询
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/front/buscategory/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          serviceType: this.serviceType  // 传给后端，筛选不同服务类型
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
</style>

<!--
<template>
    <div class="main-content">
        <div>
            <el-row :gutter="10">
                <el-col :span="6" v-for="item in newsInfoList" :key="item.id">
                    <div style="margin-bottom: 10px; cursor: pointer;" @click="$router.push('/front/busCategoryDetail?id=' + item.id)">
                        <img :src="item.img" alt="" style="width: 100%; height: 190px; display: block; border-radius: 5px 5px 0 0;">
                        <div style="padding: 10px; background-color: #fff; box-shadow: -3px 3px 3px -2px rgba(0,0,0,0.1), 3px 3px 3px -2px rgba(0,0,0,0.1);">
                            <div class="line2" style="height: 20px; margin-bottom: 5px; text-align: center">
                                <el-tag>{{ item.name }}</el-tag>
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
        name: "BusCategory",
        data() {
            return {
                newsInfoList: [],
                pageNum: 1,
                pageSize: 8,
                total: 0,
                serviceType: ''   //  服务类型
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/buscategory/selectPage', {
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

</style>
-->