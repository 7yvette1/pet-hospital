<template>
    <div style="width: 70%; margin: 10px auto">
        <el-row>
            <el-col :span="8">
                <div>
                    <div>
                        <img src="@/assets/front_images/ai.png" alt="" />
                    </div>
                    <div>
                        <div style="font-weight: bold; font-size: 15px; margin-left: 10px">模型：文心一言</div>
                    </div>
                </div>
            </el-col>
            <el-col :span="16">
                <el-card>
                    <div style="flex: 1 1 0%; overflow-y: auto; padding: 0.5rem; height: 550px">
                        <div style="display: flex; flex-direction: column;">
                            <div v-for="(message, index) in messages" :key="index" style="display: flex; " :class="{'isMine': message.isMine}">
                                <div :class="{'isMine': message.isMine, 'isAI': !message.isMine}"
                                     style="white-space: pre-wrap; max-width: 22rem; padding: 0.5rem; margin-top: 0.2rem; margin-bottom: 1rem;">
                                    {{ message.text }}
                                </div>
                            </div>
                        </div>
                    </div>
                </el-card>

                <div style="margin-top: 10px; display: flex; align-items: center; padding: 1rem; --tw-bg-opacity: 1;  background-color: rgb(255 255 255 / var(--tw-bg-opacity));">
                    <input type="text" v-model="inputText" placeholder="请输入您的问题或需求..." class="inputQus">
                    <button class="inputButton" @click="sendSSEMessage">
                        <svg width="15px" height="15px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#ffffff"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M11.5003 12H5.41872M5.24634 12.7972L4.24158 15.7986C3.69128 17.4424 3.41613 18.2643 3.61359 18.7704C3.78506 19.21 4.15335 19.5432 4.6078 19.6701C5.13111 19.8161 5.92151 19.4604 7.50231 18.7491L17.6367 14.1886C19.1797 13.4942 19.9512 13.1471 20.1896 12.6648C20.3968 12.2458 20.3968 11.7541 20.1896 11.3351C19.9512 10.8529 19.1797 10.5057 17.6367 9.81135L7.48483 5.24303C5.90879 4.53382 5.12078 4.17921 4.59799 4.32468C4.14397 4.45101 3.77572 4.78336 3.60365 5.22209C3.40551 5.72728 3.67772 6.54741 4.22215 8.18767L5.24829 11.2793C5.34179 11.561 5.38855 11.7019 5.407 11.8459C5.42338 11.9738 5.42321 12.1032 5.40651 12.231C5.38768 12.375 5.34057 12.5157 5.24634 12.7972Z" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                    </button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "AI",
        data(){
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                inputText: null,//要发送的问题
                // 对话数组
                messages: [
                    { text: "你好", isMine: true },
                    { text: "你好，我是智能AI问诊，有什么我能帮助你的吗？", isMine: false },
                ],
                responseFirst: true, // 对话第一次回复
                eventSource: null,
            };
        },
        beforeUnmount() {
            if (this.eventSource) {
                this.eventSource.close();
            }
        },
        methods:{
            sendSSEMessage() {
                if (this.user.userId === undefined){
                    this.$message.warning('请先登录')
                    return;
                }
                // 只有当eventSource不存在时才创建新的EventSource连接
                if (!this.eventSource) {
                    this.messages.push({text: this.inputText, isMine: true});
                    this.messages.push({text: "", isMine: false});
                    // 创建新的EventSource连接
                    this.eventSource = new EventSource('http://127.0.0.1:8888/chat?content='+this.inputText);

                    // 设置消息接收的回调函数
                    this.eventSource.onmessage = (event) => {
                        const data = JSON.parse(event.data);
                        console.info(data.result)
                        this.messages[this.messages.length - 1].text += data.result;
                    };

                    // 可选：监听错误事件，以便在出现问题时能够重新连接或处理错误
                    this.eventSource.onerror = (event) => {
                        console.error("EventSource failed:", event);
                        this.eventSource.close(); // 关闭出错的连接
                        this.eventSource = null; // 重置eventSource变量，允许重建连接
                    };
                }
            }
        }
    }
</script>

<style scoped>
    .isMine{
        background-color: #e3e3e3;
        justify-content: end;
        border-radius: 0.5rem;
    }
    .isAI{
        background-color: #ccdeff;
        border-radius: 0.5rem;
    }
    .inputQus{
        flex: 1 1 0%;
        border-radius: 9999px;
        border-width: 1px;
        padding: 0.5rem 0.5rem 0.5rem 1rem;
        outline: 2px solid transparent;
        outline-offset: 2px;
        outline: 2px solid transparent;
        outline-offset: 2px;
    }
    .inputButton{
        margin-left: 0.5rem;
        border-radius: 9999px;
        --tw-bg-opacity: 1;
        background-color: rgb(59 130 246 / var(--tw-bg-opacity));
        padding: 0.5rem;
        --tw-text-opacity: 1;
        color: rgb(255 255 255 / var(--tw-text-opacity));
        outline: 2px solid transparent;
        outline-offset: 2px;
        outline: 2px solid transparent;
        outline-offset: 2px;
    }
</style>