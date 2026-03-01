import Vue from 'vue'
import Vuex from 'vuex'
import user from "./user";
import menu from "./menu";
import tab from './tab'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        user,
        menu,
        tab,
    },
    //持久化插件，解决刷新页面vuex中数据丢失
    plugins: [createPersistedState({
        storage: window.sessionStorage,
        paths: ['user']
    })]
})
