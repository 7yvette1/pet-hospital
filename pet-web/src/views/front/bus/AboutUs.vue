<template>
    <div style="width: 70%; margin: 10px auto">
        <el-card>
            <div style="margin-top: -20px;">
                <el-divider><i class="el-icon-user-solid" style="font-weight: bold"> 关于我们</i></el-divider>
            </div>

            <div>
                <span>公司简介</span>
            </div>
            <div v-html="companyDescription"></div>
        </el-card>

        <el-card style="margin-top: 10px">
            <div style="margin-top: -20px;">
                <el-divider><i class="el-icon-star-on" style="font-weight: bold"> 价值工作</i></el-divider>
            </div>
            <el-row :gutter="20">
                <el-col :span="8">
                    <div class="grid-content bg-purple">
                        <div style="text-align: center; font-size: 20px;font-weight: bold; padding-bottom: 10px">价值</div>
                        <div style="padding-left: 25px" v-html="content1"></div>
                        <div style="padding-top: 15px; padding-right: 5px; text-indent: 2em;">宠物经济蓬勃发展的当下，宠物诊所作为保障宠物健康的核心场景，其运营效率与服务质量直接影响行业口碑与生存能力。</div>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple">
                        <div style="text-align: center; font-size: 20px;font-weight: bold">内容</div>
                        <div style="padding-top: 15px; padding-right: 5px; text-indent: 2em;">智慧宠物综合管理系统的内容设计需兼顾 “刚性需求” 与 “弹性空间”—— 既满足诊疗规范的强制性要求，又为诊所保留个性化服务的余地，最终实现 “用技术守护宠物健康，用服务温暖宠物主人” 的核心目标。</div>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple">
                        <div style="text-align: center; font-size: 20px;font-weight: bold">愿景</div>
                        <div style="padding-top: 15px; padding-right: 5px; text-indent: 2em;">当技术与温情在宠物医疗场景中深度交融，智慧宠物综合管理系统的终极愿景绝非停留在 “工具迭代” 的层面，而是成为连接宠物、主人、诊所与行业的数字枢纽，推动整个宠物健康领域从 “碎片化服务” 迈向 “全周期生态”。</div>
                    </div>
                </el-col>
            </el-row>
        </el-card>

        <el-card style="margin-top: 10px">
            <div style="margin-top: -20px;">
                <el-divider><i class="el-icon-phone" style="font-weight: bold"> 联系方式</i></el-divider>
            </div>

            <el-row :gutter="20">
                <el-col :span="8">
                    <div class="spans-container">
                        <span><i class="el-icon-phone-outline" style="font-weight: bold"> 电话：1532008XXXX</i></span>
                        <span><i class="el-icon-message" style="font-weight: bold"> 邮箱：1532008XXXX@163com</i></span>
                        <span><i class="el-icon-map-location" style="font-weight: bold"> 地址：天津市西青区XXX镇</i></span>
                    </div>
                </el-col>
                <el-col :span="16">
                    <div id="container" style="height: 176px; width: 600px; display: flex; margin-left: 80px"></div>
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
                companyDescription: '<p>这里是公司的详细描述，支持HTML内容。</p>',
                content1: '<p>1.重构运营流程，降本增效立竿见影。</br> ' +
                    '2.优化客户体验，筑牢信任护城河。</br>' +
                    '3.强化医疗规范，降低执业风险。</br>' +
                    '4.数据驱动决策，实现精细化运营。</br>' +
                    '5.拓展服务边界，打开增长空间。</br> </p>',
            };
        },
        mounted(){
            this.load();
        },
        methods: {
            load(){
                var map = new BMapGL.Map('container'); // 创建Map实例
                map.centerAndZoom(new BMapGL.Point(117.048631, 39.063574), 16); // 初始化地图,设置中心点坐标和地图级别
                map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
                let point = new BMapGL.Point(117.048631, 39.063574)
                let marker = new BMapGL.Marker(point);  // 创建标注
                map.addOverlay(marker);
                let opts = {
                    width : 300,     // 信息窗口宽度
                    height: 100,     // 信息窗口高度
                    title : 'ssssss' , // 信息窗口标题
                }
                let infoWindow = new BMapGL.InfoWindow(
                    "<b>地址</b>：" + 'xxxxxxxxxx' + "<br/>",
                    opts);  // 创建信息窗口对象

                marker.addEventListener("click", function(){
                    map.openInfoWindow(infoWindow,point); //开启信息窗口
                });
            }
        }
    }
</script>

<style scoped>
    .grid-content {
        border-radius: 4px;
        min-height: 240px;
    }
    .bg-purple {
        background: #d3dce6;
        padding-top: 10px;
        line-height: 20px;
    }
    .spans-container {
        line-height: 1.5; /* 设置行高 */
        font-weight: bold;
    }
    .spans-container span {
        display: block; /* 使span元素作为块级元素显示 */
        margin-bottom: 10px; /* 设置底部间距 */
    }
    .spans-container span:last-child {
        margin-bottom: 0; /* 最后一个span不设置底部间距 */
    }
</style>