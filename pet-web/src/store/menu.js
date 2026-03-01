import request from "@/utils/request"

export default {
    namespaced: true,
    state: {
        isCollapse: false,
        menuRoute: [],
        menuList: []
    },
    getters: {
        getMenuList(state){
            return state.menuList;
        }
    },
    mutations: {
        //菜单栏展开和收收起
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        },
        SETMENUROUTE(state, data) {
            state.menuRoute = data
        },
        REFRESHMENU(state,data){
            state.menuList = data;
        }
    },
    actions: {
        async setMenuRoute({commit}) {
            let {code, data} = await request.get('/sysmenu/getMenuList')
            commit('SETMENUROUTE', data || [])
            return {code, data}
        },
        async refreshMenu({commit}) {
            let {data} = await request.get('/sysmenu/getMenuList')
            commit('REFRESHMENU', data || [])
        },
    }
}

