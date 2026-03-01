<template>
    <el-card class="box-card">
        <div>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="24">
                    <div id="line" class="chart-wrapper" style="width: 100%;height: 400px"></div>
                </el-col>
            </el-row>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="24">
                    <div class="text-warning" style="width: 100%;height: 400px" id="pie"></div>
                </el-col>
            </el-row>
        </div>
    </el-card>
</template>


<script>
    import * as echarts from 'echarts'

    export default {
        name: 'RegisterCount',
        data() {
            return {
            };
        },

        // 页面元素渲染之后再触发
        mounted() {
            var lineAndBarOption = {
                color: ['#80FFA5'],
                title: {
                    text: '预约申请统计曲线图'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
                    data: ['Line 1']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: 'Line 1',
                        type: 'line',
                        stack: 'Total',
                        smooth: true,
                        lineStyle: {
                            width: 0
                        },
                        showSymbol: false,
                        areaStyle: {
                            opacity: 0.8,
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                {
                                    offset: 0,
                                    color: 'rgb(128, 255, 165)'
                                },
                                {
                                    offset: 1,
                                    color: 'rgb(1, 191, 236)'
                                }
                            ])
                        },
                        emphasis: {
                            focus: 'series'
                        },
                        data: [140, 232, 101, 264, 90, 340, 250]
                    },
                ]
            };
            var lineAndBarDom = document.getElementById('line');
            var lineAndBarChart = echarts.init(lineAndBarDom);
            this.$request.get('/count/selectRegisterApply').then(res => {
                if (res.code === '200') {
                    lineAndBarOption.series[0].data=res.data.y
                    //数据准备完成之后在set
                    lineAndBarChart.setOption(lineAndBarOption);
                } else {
                    this.$message.error('lineAndBarChart加载失败...')
                }
            });


            // 饼图
            var pieOption = {
                title : {
                    text: '预约状态统计',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                },
                legend: {
                    orient : 'vertical',
                    x : 'left',
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'分类',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[]
                    }
                ]
            };
            var pieDom = document.getElementById('pie');
            var pieChart = echarts.init(pieDom);
            this.$request.get('/count/selectRegisterStatus').then(res => {
                if (res.code === '200') {
                    //饼图填空
                    pieOption.series[0].data = res.data
                    pieChart.setOption(pieOption);
                } else {
                    this.$message.error('pieChart加载失败...')
                }
            });
        }
    }
</script>

<style scoped>
    .box-card {
        padding: 5px 5px 5px 5px;
        background-color: #eff4f9;
    }
    .chart-wrapper {
        background: #fff;
        padding: 5px 5px 5px 5px;
        margin: 5px 5px;
    }
</style>