import router from './index'
import store from '@/store'

/**
 * 动态路由
 * @param view
 * @returns {function(*=): any}
 */

const loadView = (view) => { // 路由懒加载
    return (resolve) => require([`@/${view}`], resolve)
}

router.beforeEach(async (to, from, next) => {
        console.log(to.path);
        const isToken = window.sessionStorage.getItem('token');
        const whiteList = ['Login']
        if(to.path.includes('front')){
            next();
        }else if((to.path == '/')){
            next('front');
        }else{
            if(whiteList.includes(to.name)) {
                next()
            }else if (isToken && !store.state.menu.menuRoute.length) {
                const {data} = await store.dispatch('menu/setMenuRoute')
                if (!data.length){
                    next('/admin')
                    return;
                }
                addRoute(data || [])
                next({
                    ...to,
                    replace:true
                })
            }else if(!isToken){
                next('/admin')
            }else{
                next()
            }
        }
    }
)


router.onError((error) => {
    router.push('/404');
});

function mapMenu(data) {
    const asyncArr = []
    data.map(item => {
            asyncArr.push({
                name: item.routeName,
                path: item.path,
                component: item.component,
                meta: {
                    title: item.label
                },
                children: []
            })
            if (item.children) asyncArr.push(...mapMenu(item.children))
        }
    )
    return asyncArr
}


function addRoute(data) {
    const newRoutes = router.options.routes
    const _data = mapMenu(data).filter(item => {
        if (!item.component) return false
        item.component = loadView(item.component)
        return true
    })

    newRoutes[1].children.push(..._data)

    router.addRoutes([...newRoutes])
}

export default router
