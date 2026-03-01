<template>
    <div class="main-content" style="width: 70%">
        <!-- 搜索及操作区域 -->
        <div class="search-bar">
            <el-input
                    placeholder="请输入资讯名称查询"
                    v-model="title"
                    style="width: 200px; margin-right: 10px;"
            ></el-input>
            <el-button type="primary" @click="load(1)">查询</el-button>
            <el-button @click="handleReset">重置</el-button>
        </div>

        <!-- 帖子列表 -->
        <el-card
                v-for="(item, index) in newList"
                :key="index"
                class="forum-card"
        >
            <div class="card-container">
                <div v-if="item.cover" class="card-image" @click="$router.push('/front/busNewsDetail?id=' + item.id)">
                    <img :src="item.cover" alt="pet" class="pet-image">
                </div>
                <div class="card-content">
                    <h3 class="title">{{ item.title }}</h3>
                    <p class="desc">{{ item.descr }}</p>
                    <div class="meta-info">
                        <span class="author">{{ item.user }}</span>
                        <span class="view-count">浏览</span>
                        <span class="time">{{ item.time }}</span>
                    </div>
                </div>
            </div>
        </el-card>

        <!-- 分页 -->
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
</template>

<script>
    export default {
        name: 'News1',
        data() {
            return {
                pageNum: 1,   // 当前的页码
                pageSize: 5,  // 每页显示的个数
                total: 0,
                title: null,
                newList: [],
            }
        },
        created() {
            this.load(1);
        },
        methods: {
            load(pageNum) {  // 分页查询
                if (pageNum) this.pageNum = pageNum
                this.$request.get('/front/busnews/selectPage', {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        title: this.title,
                        type: '康复信息',
                    }
                }).then(res => {
                    this.newList = res.data?.list
                    this.total = res.data?.total
                })
            },
            handleReset() {
                this.title = null
                this.load(1)
            },
            handleCurrentChange(pageNum) {
                this.load(pageNum)
            },
        }
    }
</script>

<style scoped>
    .search-bar {
        margin: 20px 0;
        display: flex;
        align-items: center;
    }
    .forum-card {
        margin-bottom: 20px;
        padding: 15px;
    }
    .card-container {
        display: flex;
        align-items: flex-start;
    }
    .card-image {
        width: 200px;
        height: 150px;
        margin-right: 20px;
        flex-shrink: 0;
        border-radius: 4px;
        overflow: hidden;
        cursor: pointer;
        transition: all 0.3s ease;
    }
    .pet-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .card-content {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        min-height: 150px;
    }
    .title {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
    }
    .desc {
        font-size: 14px;
        color: #666;
        line-height: 1.6;
        margin-bottom: 15px;
        flex: 1;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
    }
    .meta-info {
        font-size: 12px;
        color: #999;
        display: flex;
        align-items: center;
    }
    .author {
        margin-left: 8px;
        color: #409EFF;
    }
    .view-count, .time {
        margin-left: 15px;
        display: flex;
        align-items: center;
    }
    .view-count::before {
        content: '👁️';
        margin-right: 3px;
    }
    .time::before {
        content: '🕒';
        margin-right: 3px;
    }
</style>