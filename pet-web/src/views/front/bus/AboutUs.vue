<template>
  <div class="about-container">

    <!-- 关于我们 -->
    <el-card class="about-card">
      <el-divider>
        <i class="el-icon-user-solid section-title"> 关于我们</i>
      </el-divider>

      <div class="section-content" v-html="companyDescription"></div>
    </el-card>

    <!-- 价值工作 -->
    <el-card class="about-card">
      <el-divider>
        <i class="el-icon-star-on section-title"> 价值理念</i>
      </el-divider>

      <el-row :gutter="20">
        <el-col :span="8" v-for="item in valueList" :key="item.title">
          <div class="value-box">
            <div class="value-title">{{ item.title }}</div>
            <div class="value-content" v-html="item.content"></div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 联系方式 -->
    <el-card class="about-card">
      <el-divider>
        <i class="el-icon-phone section-title"> 联系方式</i>
      </el-divider>

      <el-row :gutter="20">
        <el-col :span="8">
          <div class="contact-box">
            <div><i class="el-icon-phone-outline"></i> 电话：1532008XXXX</div>
            <div><i class="el-icon-message"></i> 邮箱：1532008XXXX@163.com</div>
            <div><i class="el-icon-map-location"></i> 地址：广东省佛山市南海区xx镇</div>
          </div>
        </el-col>

        <el-col :span="16">
          <div id="container" class="map-container"></div>
        </el-col>
      </el-row>
    </el-card>

  </div>
</template>

<script>
export default {
  name: "AboutUs",
  data() {
    return {
      companyDescription: `
        <p>
          本系统致力于打造智慧宠物综合管理平台，
          通过数字化技术提升宠物医疗服务质量与运营效率，
          构建覆盖诊疗、管理与服务的全流程数字生态。
        </p>
      `,
      valueList: [
        {
          title: "价值",
          content: `
            <p>
            1. 重构运营流程，降本增效<br/>
            2. 优化客户体验，增强信任<br/>
            3. 强化医疗规范，降低风险<br/>
            4. 数据驱动决策，实现精细化管理<br/>
            5. 拓展服务边界，提升行业竞争力
            </p>
          `
        },
        {
          title: "内容",
          content: `
            <p>
            系统设计兼顾刚性需求与弹性扩展，
            满足规范化诊疗管理，同时支持个性化服务创新，
            实现技术与服务的融合发展。
            </p>
          `
        },
        {
          title: "愿景",
          content: `
            <p>
            打造连接宠物、主人、诊所与行业的数字枢纽，
            推动宠物医疗从碎片化服务走向全周期生态体系。
            </p>
          `
        }
      ]
    }
  },

  mounted() {
    this.initMap()
  },

  methods: {
    initMap() {
      const map = new BMapGL.Map('container')
      const point = new BMapGL.Point(113.142, 23.028)

      map.centerAndZoom(point, 15)
      map.enableScrollWheelZoom(true)

      const marker = new BMapGL.Marker(point)
      map.addOverlay(marker)

      const infoWindow = new BMapGL.InfoWindow(
          "<b>地址：</b>广东省佛山市南海区xx镇",
          {
            width: 300,
            height: 100,
            title: "宠物医院位置"
          }
      )

      marker.addEventListener("click", () => {
        map.openInfoWindow(infoWindow, point)
      })
    }
  }
}
</script>

<style scoped>

.about-container {
  width: 70%;
  margin: 20px auto;
}

.about-card {
  margin-bottom: 20px;
}

.section-title {
  font-weight: bold;
  font-size: 18px;
}

.section-content {
  line-height: 26px;
  text-indent: 2em;
}

.value-box {
  background: #f5f7fa;
  border-radius: 8px;
  padding: 20px;
  min-height: 220px;
  transition: all 0.3s ease;
}

.value-box:hover {
  box-shadow: 0 6px 20px rgba(0,0,0,0.1);
  transform: translateY(-3px);
}

.value-title {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
}

.value-content {
  line-height: 24px;
  text-indent: 2em;
}

.contact-box div {
  margin-bottom: 12px;
  font-weight: 500;
}

.map-container {
  height: 200px;
  border-radius: 8px;
  overflow: hidden;
}

</style>