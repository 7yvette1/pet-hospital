<template>
    <div class="container" style="transition: background-color 0.4s ease 0s;hsla(0,0%,100%,0.98);">
        <div class="chat-container">
            <header class="chat-header">
                <div class="header-left"></div>
                <div class="header-user">
                    <span style="margin-left: 5px;">聊天人</span>
                </div>
                <div class="header-tool">

                </div>

            </header>
            <div class="order-detail">
                <div class="details-box">
                    <el-image  style=" width: 100%;height: 100%;border-radius: 5px;" :src="productInfo.image"></el-image>
                </div>
                <div class="info" >
                    <div class="product-info">
                        <div class="interaction-price">￥ 价格</div>
                        <div class="address">地址：</div>
                        <div class="postType">简介：</div>
                    </div>
                </div>
            </div>
            <main class="chat-main">
                <div class="chat-record" ref="ChatRef" >
                    <div v-for="(item, index) in chatMessageList" :key="index">
                        <div>
                            <div class="message-my-item" v-if="item.fromUserId === userInfo.userId">
                                <div class="message-my-item">
                                    <div class="message-my-conent">{{ item.content }}</div>
                                    <div class="user-avatar">
                                        <el-image  class="avatar-item" style="width: 40px;height: 40px;border-radius: 100%" :src="userInfo.avatar"></el-image>
                                    </div>
                                </div>
                            </div>
                            <div class="message-item" v-else>
                                <div class="message-item">
                                    <div class="user-avatar">
                                        <el-image class="avatar-item" style="width: 40px;height: 40px;border-radius: 100%" :src="toUserInfo.avatar"></el-image>
                                    </div>
                                    <div class="message-conent">{{ item.content }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <hr color="#f4f4f4"/>
                <div class="chat-input">
                    <div class="comment-wrapper active comment-comp">
                        <div class="input-wrapper">
                            <input class="comment-input" v-model="content" type="text" placeholder="想跟TA说点什么..." @keyup.enter="submit"/>
                            <div class="input-buttons" @click="removeContent">
                                <i class="el-icon-circle-close"></i>
                            </div>
                        </div>
                        <button class="submit" @click="submit">发送</button>
                    </div>
                </div>
            </main>
        </div>

    </div>
</template>
<script>
    import websocket from "@/utils/websocket";

    export default {
        props: {
            chatListItem: {},
            productId: '',
        },
        watch:{

        },
        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                userInfo: {},
                toUserInfo: {
                    avatar:""
                },
                content: '',
                toUserId: '',
                chatMessageList: [],
                productInfo:{
                    image:''
                }
            }
        },
        mounted() {
            this.userInfo = this.user;
            websocket.Init(this.chatListItem.id, this.user.userId);
            this.getProductInfo();
            this.getUserInfo();
            this.getMessage();
            this.receiveMessage();
        },
        destroyed() {
            this.close()
        },
        methods: {
            getProductInfo(){

            },
            getUserInfo() {

            },
            receiveMessage() {
                let ws = websocket.getwebsocket();
                ws.onmessage = (e) => {
                    //心跳消息不做处理
                    if (e.data === 'ok') {
                        return
                    }
                    let message = JSON.parse(e.data);
                    this.chatMessageList.push(message)
                }
            },
            getMessage() {
                this.$request.get('/front/chatMessage/getMessageList', {
                    params: {
                        chatListId: this.chatListItem.id,
                    }
                }).then(res => {
                    this.chatMessageList = res.data || []
                })
            },
            removeContent(){
                this.content = ''
            },
            close(){
                websocket.Close().then(res => {
                    this.$emit('close')
                })
            },
            submit() {
                let message = {
                    fromUserId: this.user.userId,
                    content: this.content,
                    toUserId: "2",
                    chatListId: this.chatListItem.id,
                    sendTime: String(new Date().getTime())
                }
                websocket.Send(message)
                this.chatMessageList.push(message)
                this.content = ''
            },
        },

    }
</script>
<style scoped>
    .icon-item {
        width: 1.2em;
        height: 1.2em;
        margin-right: 5px;
        color: rgba(51, 51, 51, 0.8);
    }

    .container {
        width: 100%;
        height: 100%;

        .chat-container {
            width: 100%;
            height: 100%;

            transition: transform 0.4s ease 0s, width 0.4s ease 0s;
            background-color: #fff;

            .chat-header {
                height: 45px;
                width: 100%;
                background-color: #fff;
                display: flex;
                justify-content: space-between;
                align-items: center;

                .header-user {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
            }

            .order-detail {
                height: 13%;
                width: 100%;
                border-bottom: 1px solid rgba(0, 0, 0, 0.08);
                flex-grow: 1;
                flex-shrink: 1;
                display: flex;
                flex-direction: row;
                padding: 10px;


                .details-box {
                    width: 25%;
                    cursor: pointer;
                }

                .info {
                    margin-left: 10px;
                    display: flex;
                    justify-content: space-between;
                    width: 75%;

                    .product-info{
                        cursor: pointer;
                        padding: 10px 0;
                        display: flex;
                        flex-direction: column;
                        justify-content: space-between;
                        height: 100%; /* 让子项充满高度，以便垂直对齐 */
                        .interaction-price {
                            font-weight: bolder;
                            font-size: 16px;
                            color: red;
                        }
                        .address{
                            font-size: 13px;
                        }
                        .postType{
                            font-size: 13px;
                        }
                    }
                    .buy {
                        margin-top: 13%;
                    }
                }
            }

            .chat-main {
                height: 80%;
                padding: 0 15px;

                .chat-record {
                    height: 85%;
                    overflow-y: scroll;
                    .message-item {
                        display: flex;
                        justify-content: left;
                        align-items: center;
                        margin: 2px 0;

                        .message-conent {
                            margin-left: 5px;
                            padding: 4px 10px;
                            border: 1px solid #f4f4f4;
                            background-color: #fff;
                            border-radius: 8px;
                            font-size: 16px;
                        }
                    }

                    .message-my-item {
                        display: flex;
                        justify-content: right;
                        align-items: center;
                        margin: 2px 0;

                        .message-my-conent {
                            margin-right: 5px;
                            padding: 4px 10px;
                            color: #fff;
                            background-color: rgb(0, 170, 255);
                            border-radius: 8px;
                            font-size: 16px;
                        }
                    }

                }

                .chat-input {
                    height: 10%;
                    .input-tool {
                        display: flex;
                        justify-content: space-between;
                        height: 30px;
                        padding: 0 5px;
                        background-color: #9e9e9e;
                    }

                    .comment-wrapper {
                        &.active {
                            .input-wrapper {
                                flex-shrink: 1;
                            }
                        }
                    }

                    .comment-wrapper {
                        display: flex;
                        font-size: 16px;
                        overflow: hidden;

                        .input-wrapper {
                            display: flex;
                            position: relative;
                            width: 100%;
                            flex-shrink: 0;
                            transition: flex 0.3s;

                            .comment-input:placeholder-shown {
                                background-image: none;
                                padding: 12px 92px 12px 16px;
                                background-repeat: no-repeat;
                                background-size: 16px 16px;
                                background-position: 16px 12px;
                                color: rgba(51, 51, 51, 0.3);
                            }

                            .comment-input {
                                padding: 12px 92px 12px 16px;
                                width: 100%;
                                height: 40px;
                                line-height: 16px;
                                background: rgba(0, 0, 0, 0.03);
                                caret-color: rgba(51, 51, 51, 0.3);
                                border-radius: 22px;
                                border: none;
                                outline: none;
                                resize: none;
                                color: #333;
                            }

                            .input-buttons {
                                cursor: pointer;
                                position: absolute;
                                right: 0;
                                top: 0;
                                height: 40px;
                                display: flex;
                                align-items: center;
                                justify-content: center;
                                width: 40px;
                                color: rgba(51, 51, 51, 0.3);
                            }
                        }

                        .submit {
                            margin-left: 8px;
                            width: 60px;
                            height: 40px;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            color: #fff;
                            font-weight: 600;
                            cursor: pointer;
                            flex-shrink: 0;
                            background: #3d8af5;
                            border-radius: 44px;
                            font-size: 16px;
                        }
                    }

                    .comment-comp {
                        margin-top: 20px;
                    }
                    .input-content {
                        width: 100%;
                        padding: 0 10px;
                        resize: none;
                        border: 0;
                        outline: none;
                    }
                }
            }
        }

        .close-cricle {
            left: 18vw;
            top: 1.3vw;
            position: fixed;
            display: flex;
            z-index: 100;
            cursor: pointer;

            .close-mask-white {
                box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.04), 0 1px 2px 0 rgba(0, 0, 0, 0.02);
                border: 1px solid rgba(0, 0, 0, 0.08);
            }

            .close {
                display: flex;
                justify-content: center;
                align-items: center;
                border-radius: 100%;
                width: 40px;
                height: 40px;
                border-radius: 40px;
                cursor: pointer;
                transition: all 0.3s;
                background-color: #fff;
            }
        }
    }
</style>
