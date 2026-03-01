<template>
    <div class="main-content" style="width: 60%">
        <div>
            <div class="card" style="margin: 10px 0">
                <div style="margin-bottom: 20px">
                    <el-input type="textarea" placeholder="请输入内容" v-model="form.content"></el-input>
                    <div style="margin-top: 5px; text-align: right">
                        <el-button type="primary" @click="addComment(null)">发布</el-button>
                    </div>
                </div>

                <div>
                    <div v-for="item in commentList" :key="item.id" style="display: flex; grid-gap: 15px; margin-bottom: 20px">
                        <img :src="item.avatar" alt="" style="width: 35px; height: 35px; border-radius: 50%">
                        <div style="flex: 1;">
                            <div style="margin-bottom: 5px; color: #666">{{ item.userName }}</div>
                            <div style="margin-bottom: 5px">{{ item.content }}</div>
                            <div style="font-size: 13px; color: #666; margin-bottom: 10px">
                                <span>{{ item.time }}</span>
                                <span style="margin: 0 20px; cursor: pointer" @click="handleReply(item)" :class="{ 'highLight' : item.showReply }">
                                <i class="el-icon-s-comment"></i>回复
                            </span>
                                <span style="cursor: pointer" @click="del(item.id)" v-if="user.userId === item.userId">
                                <i class="el-icon-delete"></i>删除
                          </span>
                            </div>
                            <div style="margin-bottom: 10px" v-if="item.showReply">
                                <el-input type="textarea" v-model="item.reply" placeholder="请输入回复内容"></el-input>
                                <div style="margin-top: 5px; text-align: right">
                                    <el-button type="primary" size="mini" @click="addComment(item)">回复</el-button>
                                </div>
                            </div>

                            <div v-if="item.children.length">
                                <el-button style="margin-bottom: 10px; color: #505050; font-size:12px; width: auto; height: 30px; border: none;" @click="queryChildComment(item)">
                                    <span>查看全部 {{item.children.length}} 条回复</span>
                                    <img style="width: 12px; vertical-align: middle;" src="@/assets/front_images/xia.png"/>
                                </el-button>
                            </div>

                            <div v-if="item.childCommentFlag">
                                <div v-for="sub in item.children" :key="sub.id" style="display: flex; grid-gap: 15px; margin-bottom: 10px">
                                    <img :src="sub.avatar" alt="" style="width: 25px; height: 25px; border-radius: 50%">
                                    <div style="flex: 1;">
                                        <div style="margin-bottom: 5px; color: #666">{{ sub.userName }}</div>
                                        <div style="margin-bottom: 5px">{{ sub.content }}</div>
                                        <div style="font-size: 13px; color: #666; margin-bottom: 10px">
                                            <span>{{ sub.time }}</span>
                                            <span style="margin: 0 20px; cursor: pointer" @click="handleReply(sub)" :class="{ 'highLight' : sub.showReply }">
                                          <i class="el-icon-s-comment"></i>回复
                                        </span>
                                            <span style="cursor: pointer" @click="del(sub.id)" v-if="user.userId === sub.userId">
                                          <i class="el-icon-delete"></i>删除
                                        </span>
                                        </div>
                                        <div style="margin-bottom: 10px" v-if="sub.showReply">
                                            <el-input type="textarea" v-model="sub.reply" placeholder="请输入回复内容"></el-input>
                                            <div style="margin-top: 5px; text-align: right">
                                                <el-button type="primary" size="mini" @click="addComment(sub)">回复</el-button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Forum",
        data() {
            return {
                pageNum: 1,   // 当前的页码
                pageSize: 5,  // 每页显示的个数
                total: 0,
                form: {},
                commentList: [],
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                childCommentFlag: false,
            }
        },
        created() {
            this.load(1)
        },
        methods: {
            load(pageNum) {
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/buscomment/selectTreeByPage/' + 0 + '/' + 'forum',{
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                    }
                }).then(res => {
                    this.commentList = res.data?.list
                    this.total = res.data?.total
                })
            },
            del(id) {   // 单个删除
                this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
                    this.$request.delete('/front/buscomment/deepDelete/' + id).then(res => {
                        if (res.code === '200') {   // 表示操作成功
                            this.$message.success('操作成功')
                            this.load()
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }).catch(() => {
                })
            },
            addComment(pComment) {  // pComment表示父级的评论对象
                this.form.module = 'forum'
                this.form.fid = 0
                this.form.userId = this.user.userId
                if (pComment) {   // 表示回复
                    this.form.pid = pComment.id
                    this.form.content = pComment.reply
                }
                if (this.user.userId === undefined){
                    this.$message.warning('请先登录')
                    return
                }

                this.$request.post('/front/buscomment/add', this.form).then(res => {
                    if (res.code === '200') {
                        this.$message.success('操作成功')
                        this.form = {}
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            },
            queryChildComment(comment){
                this.$set(comment, 'childCommentFlag',  !comment.childCommentFlag)
            },
            handleReply(comment) {
                this.$set(comment, 'showReply',  !comment.showReply)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>

</style>