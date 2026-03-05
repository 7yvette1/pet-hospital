import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)
//解决vue路由重复导航错误
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

// 完整路由代码
export default new VueRouter({
    mode: "history",
    routes: [
        {
            path: '/admin',
            name: 'Login',
            component: () => import('@/views/manage/Login'),
        },
        {
            path: '/main',
            name: 'Main',
            component: () => import('@/views/manage/Main'),
            children: [
                {   path: '/personalCenter',
                    name: 'PersonalCenter',
                    component: () => import('@/views/manage/other/PersonalCenter'),
                },
                {   path: '/404',
                    name: 'NotFound',
                    component: () => import('@/views/404'),
                },
            ]
        },
        {
            path: '/front/login',
            name: 'Login',
            component: () => import('@/views/front/Login'),
        },
        {
            path: '/front/register',
            name: 'Register',
            component: () => import('@/views/front/Register'),
        },
        {
            path: '/front',
            name: 'Front',
            component: () => import('../views/front/Index.vue'),
            children: [
                { path: '/', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
                { path: 'home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
                { path: 'aboutUs', meta: { name: '关于我们' }, component: () => import('../views/front/bus/AboutUs') },
                { path: 'person', meta: { name: '个人中心' }, component: () => import('../views/front/bus/Person') },
                { path: 'forum', meta: { name: '论坛交流' }, component: () => import('../views/front/bus/Forum') },
                { path: 'ai', meta: { name: 'AI问诊' }, component: () => import('../views/front/bus/AI') },
                { path: 'chatList', meta: { name: '聊天' }, component: () => import('../views/front/bus/ChatList') },
                { path: 'new', meta: { name: '诊所资讯' }, component: () => import('../views/front/bus/News') },
                { path: 'new1', meta: { name: '康复故事' }, component: () => import('../views/front/bus/News1') },
                { path: 'busNewsDetail', meta: { name: '新闻资讯' }, component: () => import('../views/front/bus/BusNewsDetail') },
                { path: 'busCategory', meta: { name: '诊所服务' }, component: () => import('../views/front/bus/BusCategory') },
                { path: 'busCategoryDetail', meta: { name: '诊所服务详情' }, component: () => import('../views/front/bus/BusCategoryDetail') },
                { path: 'sysDoc', meta: { name: '医疗团队' }, component: () => import('../views/front/bus/SysDoc') },
                { path: 'sysDocDetail', meta: { name: '医疗团队详情' }, component: () => import('../views/front/bus/SysDocDetail') },
                { path: 'myPetHealth', meta: { name: '宠物健康档案' }, component: () => import('../views/front/bus/MyPetHealth') },
                { path: 'myRegistInfo', meta: { name: '我的预约信息' }, component: () => import('../views/front/bus/MyRegistInfo') },
                { path: 'myMedicalCase', meta: { name: '宠物病历信息' }, component: () => import('../views/front/bus/MyMedicalCase') },
            ]
        },
    ]
})
