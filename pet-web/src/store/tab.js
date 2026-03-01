//面包屑导航
export default {
    namespaced: true,
    //存储数据
    state: {
        isCollapse: false,
        //面包屑列表
        tabList: [
            {
                path: '/home',
                name: 'Home',
                label: '首页',
                icon: 's-home',
            }
        ],
    },
    //调用方法
    mutations: {
        //更新面包屑数据
        updateMenu(state, item) {
            if (item.label !== 'home') {
                let index = state.tabList.findIndex(tabItem => tabItem.label === item.label);
                if (index === -1) {
                    state.tabList = [];
                    state.tabList.push(item);
                }
            }
        }
    },

}
