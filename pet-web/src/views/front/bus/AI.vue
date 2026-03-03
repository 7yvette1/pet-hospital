<template>
  <div class="ai-wrapper">
    <el-row>
      <!-- 左侧模型信息 -->
      <el-col :span="6" class="ai-left">
        <img src="@/assets/front_images/ai.png" class="ai-logo" />
        <div class="ai-model">
          模型：文心一言
        </div>
      </el-col>

      <!-- 右侧聊天区 -->
      <el-col :span="18">
        <el-card class="chat-card">

          <!-- 消息区域 -->
          <div class="chat-body" ref="chatBody">
            <div
                v-for="(message, index) in messages"
                :key="index"
                class="message-row"
                :class="{ mine: message.isMine }"
            >
              <div class="bubble">
                {{ message.text }}
              </div>
            </div>
          </div>

        </el-card>

        <!-- 输入区域 -->
        <div class="chat-input">
          <textarea
              v-model="inputText"
              @keydown.enter.exact.prevent="sendSSEMessage"
              @keydown.ctrl.enter="inputText += '\n'"
              placeholder="请输入您的问题..."
              class="inputQus"
          />
          <button class="inputButton" @click="sendSSEMessage">
            发送
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
            if (!this.user.userId) {
              this.$message.warning('请先登录')
              return
            }

            if (!this.inputText) return

            // 关闭旧连接
            if (this.eventSource) {
              this.eventSource.close()
              this.eventSource = null
            }

            this.messages.push({ text: this.inputText, isMine: true })
            this.messages.push({ text: "", isMine: false })

            const content = this.inputText
            this.inputText = ""

            this.scrollToBottom()

            this.eventSource = new EventSource(
                'http://127.0.0.1:8888/chat?content=' + encodeURIComponent(content)
            )

            this.eventSource.onmessage = (event) => {
              const data = JSON.parse(event.data)
              this.messages[this.messages.length - 1].text += data.result
              this.scrollToBottom()
            }

            this.eventSource.onerror = () => {
              this.eventSource.close()
              this.eventSource = null
            }
          },
          scrollToBottom() {
            this.$nextTick(() => {
              const container = this.$refs.chatBody
              if (container) {
                container.scrollTop = container.scrollHeight
              }
            })
          }



        }
    }
</script>

<style scoped>
.ai-wrapper {
  width: 75%;
  margin: 20px auto;
}

.ai-left {
  text-align: center;
  padding-top: 40px;
}

.ai-logo {
  width: 120px;
}

.ai-model {
  margin-top: 10px;
  font-weight: bold;
  font-size: 15px;
}

.chat-card {
  height: 550px;
}

.chat-body {
  height: 500px;
  overflow-y: auto;
  padding: 15px;
  background: #f7f9fc;
}

.message-row {
  display: flex;
  margin-bottom: 15px;
}

.message-row.mine {
  justify-content: flex-end;
}

.bubble {
  max-width: 60%;
  padding: 10px 15px;
  border-radius: 15px;
  white-space: pre-wrap;
  font-size: 14px;
  line-height: 1.6;
  background: #e4e6eb;
}

.message-row.mine .bubble {
  background: #409eff;
  color: #fff;
}

.chat-input {
  margin-top: 10px;
  display: flex;
  align-items: center;
}

.inputQus {
  flex: 1;
  border-radius: 25px;
  border: 1px solid #dcdfe6;
  padding: 10px 15px;
  outline: none;
  resize: none;
  min-height: 40px;
  transition: 0.3s;
}

.inputQus:focus {
  border-color: #409eff;

}

.inputButton {
  margin-left: 10px;
  border-radius: 25px;
  background-color: #409eff;
  color: white;
  padding: 8px 18px;
  border: none;
  cursor: pointer;
  transition: 0.3s;
}

.inputButton:hover {
  background-color: #66b1ff;
}

</style>