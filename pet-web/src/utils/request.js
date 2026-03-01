import axios from 'axios'
import router from "@/router";

// 创建可一个新的axios对象
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,   // 后端的接口地址  ip:port
    timeout: 30000                          // 30s请求超时
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    if (config.url !== '/manage/login' && config.url !== '/syslogin/getVerificationCode') {
        if(config.url.indexOf("front") != -1){
            let user = JSON.parse(localStorage.getItem("xm-user") || '{}')
            config.headers['token'] = user.token;// 设置请求头
        }else{
            config.headers['token'] = window.sessionStorage.getItem('token');// 设置请求头
        }
    }
    return config
}, error => {
    console.error('response error: ' + error) // for debug
    return Promise.reject(error)
});


// 默认响应拦截器，拦截异常
request.interceptors.response.use(res =>{
    console.log("响应拦截器执行====>",res);
    if (res.data.code === '200' || res.data.code === '500') {
        return res.data
    } else if (res.data.code === '401') {
        if(res.config.url.indexOf("/front") != -1){
            router.push('/front/login');
        }else{
            router.push({name: 'Login'});
        }
        return res.data
    } else {
        return res.data
    }
}, err => {
    if (err && err.response) {
        switch (err.response.status) {
            case 400:
                err.message = '请求错误(400)'
                break
            case 401:
                err.message = '未授权，请重新登录(401)'
                break
            case 403:
                err.message = '拒绝访问(403)'
                break
            case 404:
                err.message = '请求出错(404)'
                break
            case 408:
                err.message = '请求超时(408)'
                break
            case 500:
                err.message = '服务器错误(500)'
                break
            case 501:
                err.message = '服务未实现(501)'
                break
            case 502:
                err.message = '网络错误(502)'
                break
            case 503:
                err.message = '服务不可用(503)'
                break
            case 504:
                err.message = '网络超时(504)'
                break
            case 505:
                err.message = 'HTTP版本不受支持(505)'
                break
            default:
                err.message = `连接出错(${err.response.status})!`
        }
    } else {
        err.message = '连接服务器失败!'
    }
    return Promise.reject(err)
})


export default request