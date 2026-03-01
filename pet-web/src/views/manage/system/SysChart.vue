<template>
    <el-card class="box-card">
        <div>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="12">
                    <div id="line" class="chart-wrapper" style="width: 100%;height: 400px"></div>
                </el-col>
                <el-col :xs="24" :sm="24" :lg="12">
                    <div id="bar" class="chart-wrapper" style="width: 100%;height: 400px"></div>
                </el-col>
            </el-row>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="12">
                    <div id="pie" class="chart-wrapper" style="width: 100%;height: 400px"></div>
                </el-col>
                <el-col :xs="24" :sm="24" :lg="12">
                    <div id="radar" class="chart-wrapper" style="width: 100%;height: 400px"></div>
                </el-col>
            </el-row>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="24">
                    <div id="lineAndBar" class="chart-wrapper" style="width: 100%;height: 500px"></div>
                </el-col>
            </el-row>
            <el-row :gutter="32">
                <el-col :xs="24" :sm="24" :lg="10">
                    <div id="gauge" class="chart-wrapper" style="width: 100%;height: 450px"></div>
                </el-col>
                <el-col :xs="24" :sm="24" :lg="14">
                    <div id="funnel" class="chart-wrapper" style="width: 100%;height: 450px"></div>
                </el-col>
            </el-row>
        </div>
        <div>
            <div class="china" ref="china"></div>
        </div>
    </el-card>
</template>


<script>
    import * as echarts from 'echarts';
    import china from 'echarts-map/json/china.json' //导入china包
    echarts.registerMap('china', china)

    export default {
        name: 'SysChart',
        data() {
            return {
            };
        },

        // 页面元素渲染之后再触发
        mounted() {
            //折线图
            var lineOption = {
                title : {
                    text: '未来一周气温变化',
                    subtext: '纯属虚构'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['最高气温','最低气温']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : []
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value} °C'
                        }
                    }
                ],
                series : [
                    {
                        name:'最高气温',
                        type:'line',
                        data:[],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'最低气温',
                        type:'line',
                        data:[],
                        markPoint : {
                            data : [
                                {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }
                    }
                ]
            };
            var lineDom = document.getElementById('line');
            var lineChart = echarts.init(lineDom);
            this.$request.get('/syschart/selectline').then(res => {
                if (res.code === '200') {
                    lineOption.xAxis[0].data=res.data.x
                    lineOption.series[0].data=res.data.y
                    lineOption.series[1].data=res.data.y1
                    //数据准备完成之后在set
                    lineChart.setOption(lineOption);
                } else {
                    this.$message.error('lineChart加载失败...')
                }
            });


            // 柱状图
            var barOption = {
                title : {
                    text: '某地区蒸发量和降水量',
                    subtext: '纯属虚构'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['蒸发量','降水量']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data :  []
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'蒸发量',
                        type:'bar',
                        data:[],
                    },
                    {
                        name:'降水量',
                        type:'bar',
                        data:[],
                    }
                ]
            };
            var barDom = document.getElementById('bar');
            var barChart = echarts.init(barDom);
            this.$request.get('/syschart/selectbar').then(res => {
                if (res.code === '200') {
                    barOption.xAxis[0].data=res.data.x
                    barOption.series[0].data=res.data.y
                    barOption.series[1].data=res.data.y1
                    //数据准备完成之后在set
                    barChart.setOption(barOption);
                } else {
                    this.$message.error('barChart加载失败...')
                }
            });


            // 饼图
            var pieOption = {
                title : {
                    text: '某站点用户访问来源',
                    subtext: '纯属虚构',
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
                        name:'访问来源',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[]
                    }
                ]
            };
            var pieDom = document.getElementById('pie');
            var pieChart = echarts.init(pieDom);
            this.$request.get('/syschart/selectpie').then(res => {
                if (res.code === '200') {
                    //饼图填空
                    pieOption.series[0].data = [
                        {name: "直接访问", value: res.data[0]},
                        {name: "邮件营销", value: res.data[1]},
                        {name: "联盟广告", value: res.data[2]},
                        {name: "视频广告", value: res.data[3]},
                        {name: "搜索引擎", value: res.data[4]},
                    ]
                    pieChart.setOption(pieOption);
                } else {
                    this.$message.error('pieChart加载失败...')
                }
            })



            var radarOption = {
                title : {
                    text: '罗纳尔多 vs 舍普琴科',
                    subtext: '完全实况球员数据'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    x : 'center',
                    data:[]
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                polar : [
                    {
                        indicator : [
                            {text : '进攻', max  : 100},
                            {text : '防守', max  : 100},
                            {text : '体能', max  : 100},
                            {text : '速度', max  : 100},
                            {text : '力量', max  : 100},
                            {text : '技巧', max  : 100}
                        ],
                        radius : 130
                    }
                ],
                series : [
                    {
                        name: '完全实况球员数据',
                        type: 'radar',
                        itemStyle: {
                            normal: {
                                areaStyle: {
                                    type: 'default'
                                }
                            }
                        },
                        data : [
                            {
                                value : [],
                                name : '舍普琴科'
                            },
                            {
                                value : [],
                                name : '罗纳尔多'
                            }
                        ]
                    }
                ]
            };
            var radarDom = document.getElementById('radar');
            var radarChart = echarts.init(radarDom);
            this.$request.get('/syschart/selectradar').then(res => {
                if (res.code === '200') {
                    radarOption.legend.data=res.data.legend
                    //饼图填空
                    radarOption.series[0].data[0].value = res.data.y
                    radarOption.series[0].data[1].value = res.data.y1
                    radarChart.setOption(radarOption);
                } else {
                    this.$message.error('pieChart加载失败...')
                }
            })


            var lineAndBarOption = {
                tooltip : {
                    trigger: 'axis'
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                legend: {
                    data:['蒸发量','降水量','平均温度']
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '水量',
                        axisLabel : {
                            formatter: '{value} ml'
                        }
                    },
                    {
                        type : 'value',
                        name : '温度',
                        axisLabel : {
                            formatter: '{value} °C'
                        }
                    }
                ],
                series : [

                    {
                        name:'蒸发量',
                        type:'bar',
                        data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
                    },
                    {
                        name:'降水量',
                        type:'bar',
                        data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
                    },
                    {
                        name:'平均温度',
                        type:'line',
                        yAxisIndex: 1,
                        data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
                    }
                ]
            };
            var lineAndBarDom = document.getElementById('lineAndBar');
            var lineAndBarChart = echarts.init(lineAndBarDom);
            lineAndBarChart.setOption(lineAndBarOption);


            //仪表盘
            var gaugeoption = {
                tooltip : {
                    formatter: "{a} <br/>{b} : {c}%"
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name:'业务指标',
                        type:'gauge',
                        splitNumber: 10,       // 分割段数，默认为5
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']],
                                width: 8
                            }
                        },
                        axisTick: {            // 坐标轴小标记
                            splitNumber: 10,   // 每份split细分多少段
                            length :12,        // 属性length控制线长
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: 'auto'
                            }
                        },
                        splitLine: {           // 分隔线
                            show: true,        // 默认显示，属性show控制显示与否
                            length :30,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        pointer : {
                            width : 5
                        },
                        title : {
                            show : true,
                            offsetCenter: [0, '-40%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        detail : {
                            formatter:'{value}%',
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: 'auto',
                                fontWeight: 'bolder'
                            }
                        },
                        data:[{value: 50, name: '完成率'}]
                    }
                ]
            };
            var gaugeDom = document.getElementById('gauge');
            var gaugeChart = echarts.init(gaugeDom);
            gaugeoption.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
            gaugeChart.setOption(gaugeoption, true);



            var funneloption = {
                title : {
                    text: '漏斗图',
                    subtext: '纯属虚构'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c}%"
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                legend: {
                    data : ['展现','点击','访问','咨询','订单']
                },
                calculable : true,
                series : [
                    {
                        name:'漏斗图',
                        type:'funnel',
                        width: '40%',
                        data:[
                            {value:60, name:'访问'},
                            {value:40, name:'咨询'},
                            {value:20, name:'订单'},
                            {value:80, name:'点击'},
                            {value:100, name:'展现'}
                        ]
                    },
                    {
                        name:'金字塔',
                        type:'funnel',
                        x : '50%',
                        sort : 'ascending',
                        itemStyle: {
                            normal: {
                                // color: 各异,
                                label: {
                                    position: 'left'
                                }
                            }
                        },
                        data:[
                            {value:60, name:'访问'},
                            {value:40, name:'咨询'},
                            {value:20, name:'订单'},
                            {value:80, name:'点击'},
                            {value:100, name:'展现'}
                        ]
                    }
                ]
            };
            var funnelDom = document.getElementById('funnel');
            var funnelChart = echarts.init(funnelDom);
            funnelChart.setOption(funneloption, true);



            let myChart = echarts.init(this.$refs.china)
            let option;
            var data = [{name:'北京',value:'5000'}]
            option = {
                tooltip: {
                    trigger: 'item'
                },
                //左侧小导航图标
                visualMap: {
                    show: true,
                    x: 'left',
                    y: 'center',
                    splitList: [
                        { start: 1, end: 500 }, { start: 500, end: 1000 },
                        { start: 1000, end: 1500 }, { start: 1500, end: 2000 },
                        { start: 2000, end: 2500 }, { start: 2500, },
                    ],
                    color: [
                        '#ffb43d',
                        '#5475f5',
                        '#85daef',
                        '#9feaa5',
                        '#74e2ca',
                        '#0074bc'],// e6ac53 '#9fb5ea'
                    textStyle: {
                        color: '#black'
                    }
                },
                geo: {
                    map: 'china',
                    aspectScale: 0.75,
                    layoutCenter: ["50%", "51.5%"], //地图位置
                    layoutSize: '118%',
                    roam: false, //关闭缩放
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(147, 235, 248, 1)',
                            borderWidth: 0.5,
                            color: {
                                type: 'linear-gradient',
                                x: 0,
                                y: 1500,
                                x2: 2500,
                                y2: 0,
                                colorStops: [{
                                    offset: 0,
                                    color: '#009DA1' // 0% 处的颜色

                                }, {
                                    offset: 1,
                                    color: '#005B9E' // 50% 处的颜色

                                }],
                                global: true // 缺省为 false

                            },
                            opacity: 0.5,
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    },
                    regions: [{
                        name: '南海诸岛',
                        itemStyle: {
                            areaColor: 'rgba(0, 10, 52, 1)',
                            borderColor: 'rgba(0, 10, 52, 1)'
                        },
                        emphasis: {
                            areaColor: 'rgba(0, 10, 52, 1)',
                            borderColor: 'rgba(0, 10, 52, 1)'
                        }
                    }],
                    z: 2
                },
                series: [{
                    name: '',
                    type: 'map',
                    map: 'china',
                    label: {
                        show: true,
                        color: 'black',
                        fontSize: 14
                    },
                    aspectScale: 0.75,
                    layoutCenter: ["50%", "50%"], //地图位置
                    layoutSize: '118%',
                    roam: false, //关闭缩放
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(147, 235, 248, 0.6)',
                            borderWidth: 0.8,
                            areaColor: {
                                type: 'linear-gradient',
                                x: 0,
                                y: 1200,
                                x2: 1000,
                                y2: 0,
                                colorStops: [{
                                    offset: 0,
                                    color: '#009DA1' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#005B9E' // 50% 处的颜色
                                }],
                                global: true // 缺省为 false
                            },
                        },
                        emphasis: {
                            areaColor: 'rgba(147, 235, 248, 0)'
                        }
                    },
                    zlevel: 1,
                    data: data
                }],
            };
            //地图点击事件,根据点击某个省份计算出这个省份的数据
            myChart.on('click', (params) => {
                console.log(params);

            });
            myChart.setOption(option);

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
    .china {
        width: calc(100% - 40px);
        height: 600px;
        margin: 20px;
    }
</style>