<template>
    <div>
        <div class="search">
            <el-select v-model="status" placeholder="请选择订购状态">
                <el-option key="已订购" label="已订购" value="已订购"/>
                <el-option key="已确定" label="已确定" value="已确定"/>
                <el-option key="已完成" label="已完成" value="已完成"/>
            </el-select>
            <el-button type="primary" icon="el-icon-search" style="margin-left: 10px" @click="load(1)">查询</el-button>
            <el-button type="warning" icon="el-icon-refresh" style="margin-left: 10px" @click="reset">重置</el-button>
        </div>

        <div class="table">
            <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="80" align="center">
                    <!-- 使用template可以自定义索引的显示格式 -->
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="序号" width="80" align="center" v-if="showId"></el-table-column>
                <el-table-column prop="userName" label="订购用户" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="clinicName" label="订购服务" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="docName" label="服务人员" align="center" show-overflow-tooltip></el-table-column>
                <el-table-column prop="status" label="订购状态" align="center" show-overflow-tooltip></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template v-slot="scope">
                        <el-button v-if="scope.row.status == '已确定'" plain type="primary" @click="makeInfo(scope.row.id)" size="mini">登记病历信息</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination
                        background
                        @current-change="handleCurrentChange"
                        :current-page="pageNum"
                        :page-sizes="[5, 10, 20]"
                        :page-size="pageSize"
                        layout="total, prev, pager, next"
                        :total="total">
                </el-pagination>
            </div>
        </div>

        <el-dialog title="登记病历信息" :visible.sync="fromVisible" width="55%" :close-on-click-modal="false" destroy-on-close>
            <el-form :inline="true" label-width="120px" style="padding: 0px 50px" :model="form" :rules="rules" ref="formRef" size="small">
                <el-form-item label="宠物名称" prop="name" label-width="120px">
                    <el-input v-model="form.name" placeholder="请输入宠物名称" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="宠物性别" prop="gender">
                    <el-select v-model="form.gender" placeholder="请选择">
                        <el-option
                                v-for="item in sexOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="宠物年龄" prop="age">
                    <el-input v-model="form.age" placeholder="请输入宠物年龄" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入联系电话"  autocomplete="off" />
                </el-form-item>
                <el-form-item label="就诊日期" prop="visitDate">
                    <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                    v-model="form.visitDate"></el-date-picker>
                </el-form-item>
                <el-form-item prop="medicalInfo" label="病因">
                    <el-input type="textarea" :rows="5" v-model="form.medicalInfo" autocomplete="off" style="width: 550px"></el-input>
                </el-form-item>
                <el-form-item prop="diagnosis" label="诊断记录">
                    <el-input type="textarea" :rows="5" v-model="form.diagnosis" autocomplete="off" style="width: 550px"></el-input>
                </el-form-item>
                <el-form-item prop="adviceInfo" label="医嘱">
                    <el-input type="textarea" :rows="5" v-model="form.adviceInfo" autocomplete="off" style="width: 550px"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "DocMake",
        data() {
            return {
                tableData: [],  // 所有的数据
                pageNum: 1,   // 当前的页码
                pageSize: 10,  // 每页显示的个数
                total: 0,
                status: null,
                fromVisible: false,
                form: {},
                rules: {

                },
                ids: [],
                showId:false,
                sexOptions: [
                    {
                        value: '0',
                        label: '公'
                    }, {
                        value: '1',
                        label: '母'
                    }
                ],
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            makeInfo(id){
                this.form = {}
                this.form.registerId = id
                this.fromVisible = true   // 打开弹窗
            },
            save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
                this.$refs.formRef.validate((valid) => {
                    if (valid) {
                        this.$request({
                            url: '/medicalCase/add',
                            method: 'POST',
                            data: this.form
                        }).then(res => {
                            if (res.code === '200') {  // 表示成功保存
                                this.$message.success('登记成功')
                                this.load(1)
                                this.fromVisible = false
                            } else {
                                this.$message.error(res.msg)  // 弹出错误的信息
                            }
                        })
                    }
                })
            },
            handleSelectionChange(rows) {   // 当前选中的所有的行数据
                this.ids = rows.map(v => v.id)   //  [1,2]
            },
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/registerInfo/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        status: this.status,
                    }
                }).then(res => {
                    this.tableData = res.data?.list
                    this.total = res.data?.total
                })
            },
            reset() {
                this.status = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>

</style>
