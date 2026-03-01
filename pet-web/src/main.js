import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router/permisson'  //引入 vue-router

// 全局配置
import '@/assets/css/global.css'
import 'element-ui/lib/theme-chalk/index.css'

// 第三方包
import ElementUI from 'element-ui'
Vue.use(ElementUI, {size: "small"})


import request from "@/utils/request"


Vue.config.productionTip = false

Vue.prototype.$request = request
Vue.prototype.$baseUrl = process.env.VUE_APP_BASEURL


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
