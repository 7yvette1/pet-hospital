<template>
    <el-card class="app-container home">
        <div>
            <el-row :gutter="32">
              <el-col :xs="24" :sm="24" :lg="12">
                  <div class="text-warning" style="width: 100%;height: 400px">
                      <div style="margin-bottom: 30px; font-size: 15px; margin-top: 5px">公告列表</div>
                      <div>
                          <el-timeline reverse slot="reference">
                              <el-card style="height: 348px; margin-top: -10px">
                                  <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
                                      <el-popover
                                              placement="right"
                                              width="200"
                                              trigger="hover"
                                              :content="item.content">
                                          <span slot="reference">{{ item.title }}</span>
                                      </el-popover>
                                  </el-timeline-item>
                              </el-card>
                          </el-timeline>
                      </div>
                  </div>
              </el-col>
              <el-col :xs="24" :sm="24" :lg="12">
                  <div class="text-warning" style="width: 100%;height: 400px">
                      <el-calendar class="custom-calendar" v-model="value">
                      </el-calendar>
                  </div>
              </el-col>
            </el-row>
            <el-row :gutter="32">
              <el-col :xs="24" :sm="24" :lg="24">
                  <div class="text-warning" style="width: 100%;height: 400px" id="lineAndBar">
                  </div>
              </el-col>
            </el-row>
        </div>
    </el-card>
</template>

<script>
    import * as echarts from 'echarts'

    export default {
        name: "Home",
        data() {
            return {
                value: new Date(),
                notices: []
            };
        },
        created(){
            this.$request.get('/basenotice/selectAll').then(res => {
                this.notices = res.data || []
            })

        },
        mounted(){
            this.loadChart();
        },
        methods: {
            loadChart(){
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
                        data:['男','女','登录量']
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
                            name : '数量',
                            axisLabel : {
                                formatter: '{value}'
                            }
                        },
                        {
                            type : 'value',
                            name : '登录量',
                            axisLabel : {
                                formatter: '{value} 次'
                            }
                        }
                    ],
                    series : [
                        {
                            name:'男',
                            type:'bar',
                            data:[]
                        },
                        {
                            name:'女',
                            type:'bar',
                            data:[]
                        },
                        {
                            name:'登录量',
                            type:'line',
                            yAxisIndex: 1,
                            data:[]
                        }
                    ]
                };
                var lineAndBarDom = document.getElementById('lineAndBar');
                var lineAndBarChart = echarts.init(lineAndBarDom);
                this.$request.get('/sysuser/getUserCount').then(res => {
                    if (res.code === '200') {
                        lineAndBarOption.series[0].data=res.data.nan
                        lineAndBarOption.series[1].data=res.data.nv
                        lineAndBarOption.series[2].data=res.data.login
                        //数据准备完成之后在set
                        lineAndBarChart.setOption(lineAndBarOption);
                    } else {
                        this.$message.error('lineAndBarOption加载失败...')
                    }
                });
            }
        }
    };
</script>

<style scoped>
    .app-container home {
        padding: 5px 5px 5px 5px;
    }
    .text-warning {
        background: #EFF4F9;
        padding: 5px 5px 5px 5px;
        margin: 5px 5px;
    }
    ::v-deep .el-calendar-table .el-calendar-day {
        height: 2.94em;
        text-align: center;
    }
    ::v-deep .custom-calendar .el-calendar__body .is-selected {
        color: black;
        background-color: #409EFF; /* 被选中日期的背景色 */
    }
</style>