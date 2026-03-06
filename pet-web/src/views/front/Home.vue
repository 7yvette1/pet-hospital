<template>
    <div style="min-height: calc(100vh - 86px);">
        <div style="width: 70%; margin: 10px auto">
            <!--上面的区域-->
            <div style="display: flex; grid-gap: 10px">
                <div style="flex: 1">
                    <el-carousel height="350px" indicator-position="outside" class="banner-carousel">
                        <el-carousel-item v-for="(item, index) in imgs" :key="item.id">
                            <div class="carousel-item">
                                <img :src="item.image" alt="">
                                <div class="carousel-text" v-if="index === 0">
                                    <h2>因为严谨 所以安心</h2>
                                    <p>以保障爱宠的健康为宗旨，重新定义每个小生命的尊严和价值</p>
                                </div>
                                <div class="carousel-text" v-if="index === 1">
                                    <h2>24小时服务</h2>
                                    <p>全年无休，为您的爱宠提供及时专业的智慧服务</p>
                                    <el-button type="primary" size="large" class="btn-emergency" @click="$router.push('/front/busCategory')">订购服务</el-button>
                                </div>
                                <div class="carousel-text" v-if="index === 2">
                                    <h2>专业服务团队</h2>
                                    <p>20年+工作经验，100+专业医师持证上岗</p>
                                    <el-button type="primary" size="large" class="btn-team" @click="$router.push('/front/aboutUs')">了解团队</el-button>
                                </div>
                            </div>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </div>

            <!-- 宠物服务 -->
            <section class="services-section">
                <div class="container">
                    <div class="section-title">
                        <h2>宠物服务</h2>
                        <div class="title-line"></div>
                        <p>齐全的空间设置，专业的宠物服务，为宠物健康保驾护航</p>
                    </div>

                    <el-row :gutter="20" class="services-grid">
                        <el-col :xs="12" :sm="8" :md="6" v-for="(service, index) in services" :key="index">
                            <el-card class="service-card">
                                <div class="service-image">
                                    <img :src="service.img" :alt="service.name" class="service-img" />
                                </div>
                                <div class="service-body">
                                <h3 class="service-title">{{ service.name }}</h3>
                                <p class="service-desc">{{ service.descr }}</p>
                                </div>
                                <el-button type="text" class="more-btn" @click="$router.push('/front/busCategoryDetail?id=' + service.id)">详情 <i class="el-icon-arrow-right"></i></el-button>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
            </section>

            <!-- 医生团队 -->
            <section class="doctors-section">
                <div class="container">
                    <div class="section-title">
                        <h2>服务团队</h2>
                        <div class="title-line"></div>
                        <p>全体持证上岗，服务经验丰富、医疗技术精湛</p>
                    </div>

                    <el-carousel :interval="5000" class="doctors-carousel">
                        <el-carousel-item v-for="(doctor, index) in doctors" :key="index">
                            <el-row :gutter="30" class="doctor-item">
                                <el-col :span="8" class="doctor-photo">
                                    <img :src="doctor.avatar" alt="医生照片" />
                                </el-col>
                                <el-col :span="16" class="doctor-info">
                                    <h3 class="doctor-name">{{ doctor.nickName }}</h3>
                                    <p class="doctor-title">{{ doctor.job }}</p>
                                    <div class="doctor-intro">
                                        {{ doctor.descr }}
                                    </div>
                                    <el-tag v-for="(skill, i) in doctor.beGoodAt" :key="i" class="skill-tag">{{ skill }}</el-tag>
                                </el-col>
                            </el-row>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </section>

            <!-- 紧急联系 -->
            <section class="emergency-section">
                <div class="container">
                    <div class="emergency-content">
                        <h2>24小时宠物服务</h2>
                        <p>为宠物提供全天候紧急救助</p>
                        <el-button type="primary" size="large" class="emergency-btn">
                            <i class="el-icon-phone"></i> 400-020-8888
                        </el-button>
                    </div>
                </div>
            </section>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                imgs: [],
                pageNum: 1,
                pageSize: 5,
                total: 0,
                // 服务项目数据
                services: [

                ],
                // 医生团队数据
                doctors: [

                ],
            }
        },
        mounted() {
            this.loadImgs();
            this.loadServices();
            this.loadDoc();
        },
        methods: {
            //轮播图
            loadImgs(){
                this.$request.get('/front/basecarousel/selectAll').then(res => {
                    this.imgs = res.data || []
                })
            },
            //服务项目数据
            loadServices(){
                this.$request.get('/front/buscategory/selectTop').then(res => {
                    this.services = res.data || []
                })
            },
            //医生团队数据
            loadDoc(){
                this.$request.get('/front/sysuser/selectDoc').then(res => {
                    this.doctors = res.data || []
                })
            },
        }
    }
</script>

<style scoped>
    /* 全局样式 */
    .pet-clinic-home {
        font-family: "Microsoft YaHei", sans-serif;
        color: #333;
    }

    .container {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        padding: 0 15px;
    }

    .section-title {
        text-align: center;
        margin-bottom: 40px;
    }

    .section-title h2 {
        font-size: 28px;
        color: #1976d2;
        margin-bottom: 15px;
    }

    .title-line {
        width: 80px;
        height: 3px;
        background-color: #1976d2;
        margin: 0 auto 20px;
    }

    /* 轮播图样式 */
    .banner-carousel {
        width: 100%;
    }
    .carousel-item {
        position: relative;
        height: 100%;
    }
    .carousel-item img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .carousel-text {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
        color: #fff;
        text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        max-width: 800px;
        width: 90%;
    }
    .carousel-text h2 {
        font-size: 40px;
        margin-bottom: 20px;
    }
    .carousel-text p {
        font-size: 18px;
        margin-bottom: 30px;
    }

    /* 服务项目样式 */
    .services-section {
        padding: 60px 0;
    }

    .services-grid {
        margin-top: 30px;
    }

    .service-card {
        text-align: center;
        padding: 0;
        transition: all 0.3s;
        height: 100%;
        display: flex;
        flex-direction: column;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
        background-color: #f9f9f9;
    }

    .service-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    }

    .service-image {
        height: 200px;
        overflow: hidden;
    }

    .service-img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.5s;
    }

    .service-card:hover .service-img {
        transform: scale(1.05);
    }

    .service-title {
        font-size: 18px;
        margin: 20px 0 15px;
        padding: 0 15px;
    }

    .service-body {
      flex: 1;
      padding: 0 15px;
    }

    .service-title {
      font-size: 18px;
      margin: 20px 0 10px;
      height: 50px;              /* 固定标题高度 */
      overflow: hidden;
    }

    .service-desc {
      color: #666;
      font-size: 14px;
      line-height: 22px;

      display: -webkit-box;
      -webkit-line-clamp: 2;     /* 最多2行 */
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .more-btn {
        margin-bottom: 20px;
        color: #1976d2;
    }

    /* 服务团队样式 */
    .doctors-section {
        padding: 20px 0;
        background-color: #f9f9f9;
    }

    .doctors-carousel {
        margin-top: 30px;
    }

    .doctor-item {
        align-items: center;
        padding: 20px;
    }

    .doctor-photo img {
        width: 100%;
        max-width: 200px;
        border-radius: 50%;
        margin: 0 auto;
        display: block;
        border: 5px solid #fff;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }

    .doctor-name {
        font-size: 22px;
        margin-bottom: 10px;
    }

    .doctor-title {
        color: #666;
        margin-bottom: 20px;
        font-style: italic;
    }

    .doctor-intro {
        line-height: 1.8;
        margin-bottom: 20px;
    }

    .skill-tag {
        margin-right: 10px;
        margin-bottom: 10px;
        background-color: #e3f2fd;
        color: #1976d2;
    }

    /* 紧急联系样式 */
    .emergency-section {
        background-color: #1976d2;
        color: #fff;
        padding: 60px 0;
        text-align: center;
        background-image: linear-gradient(rgba(25, 118, 210, 0.9), rgba(25, 118, 210, 0.9)), url('https://picsum.photos/seed/emergency/1200/400');
        background-size: cover;
        background-position: center;
    }

    .emergency-content h2 {
        font-size: 30px;
        margin-bottom: 15px;
    }

    .emergency-content p {
        font-size: 18px;
        margin-bottom: 25px;
    }

    .emergency-btn {
        background-color: #fff;
        color: #1976d2;
        padding: 12px 30px;
        font-size: 18px;
        transition: all 0.3s;
    }

    .emergency-btn:hover {
        transform: scale(1.05);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    }


    /* 响应式调整 */
    @media (max-width: 768px) {
        .carousel-text h2 {
            font-size: 28px;
        }

        .doctor-item {
            flex-direction: column;
            text-align: center;
        }

        .doctor-photo {
            margin-bottom: 20px;
        }

        .skill-tag {
            margin: 5px;
        }

        .main-nav {
            display: none;
        }

        .stat-item {
            min-width: 80px;
        }

        .stat-badge {
            font-size: 18px;
        }

        .stat-plus {
            font-size: 18px;
        }
    }
</style>
