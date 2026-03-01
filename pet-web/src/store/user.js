
export default {
    namespaced: true,
    //存储数据
    state: {
        userInfo: {},
    },
    getters: {},
    //调用方法
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
        }
    },
    actions: {
        async setUserInfo({commit}, userInfo) {
            try {

            } catch (error) {
                console.error(error);
            } finally {
                // 提交mutation，更新状态数据
                commit('setUserInfo', userInfo);
            }
        }
    }
}
