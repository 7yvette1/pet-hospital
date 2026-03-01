<template>
    <div style="width: 70%; margin: 10px auto">
        <el-button
                type="danger"
                round
                @click="chatUser">
            <span>聊天</span>
        </el-button>


        <!--抽屉 聊天侧边栏-->
        <div>
            <el-drawer :with-header="false" @close="closeChat" :show-close="false" destroy-on-close :visible.sync="chatVisiable" direction="rtl">
                <Chat :chat-list-item="chatListItem" @close="closeChat" :product-id="productInfo.id"></Chat>
            </el-drawer>
        </div>
    </div>
</template>

<script>
import Chat from '@/components/front/Chat.vue'


    export default {
        name: "ChatList",
        components: {Chat},
        data() {
            return {
                user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
                chatVisiable: false,
                chatListItem: {},
                productInfo: {
                    userInfo: {
                        nickName: ''
                    },
                    productVoucher: {}
                },
            };
        },
        mounted(){

        },
        methods: {
            chatUser(){
                this.chatVisiable = true;
            },
            closeChat() {
                this.chatVisiable = false;
            },
        }
    }
</script>

<style scoped>

</style>