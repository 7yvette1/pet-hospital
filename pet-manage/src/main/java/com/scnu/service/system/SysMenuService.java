package com.scnu.service.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.common.PageResult;
import com.scnu.entity.system.SysMenu;
import com.scnu.entity.system.SysMenuSimple;
import com.scnu.entity.system.SysUserRole;
import com.scnu.mapper.system.SysMenuMapper;
import com.scnu.mapper.system.SysUserRoleMapper;
import com.scnu.utils.JWTUtils;
import com.scnu.utils.PageUtils;
import com.scnu.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class SysMenuService{

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    /**
     * 新增
     */
    public void add(SysMenu sysMenu) {
        sysMenu.setLabel(sysMenu.getMenuName());
        sysMenu.setCreateTime(DateUtil.now());
        sysMenu.setCreateBy(TokenUtils.getCurrentUser().getUserId().toString());
        sysMenuMapper.add(sysMenu);
    }

    /**
     * 根据ID修改
     */
    public void updateById(SysMenu sysMenu) {
        sysMenu.setLabel(sysMenu.getMenuName());
        sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 根据ID删除
     */
    public void deleteById(Integer id) {
        //先查询是否有子菜单
        SysMenu sysMenu = sysMenuMapper.selectByParentId(id);
        if(ObjectUtil.isNotEmpty(sysMenu)){
            throw new RuntimeException("此菜单下还有子菜单，不能删除！");
        }
        sysMenuMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sysMenuMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public SysMenu selectById(Integer id) {
        return sysMenuMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<SysMenu> selectAll(SysMenu sysMenu) {
        //查询所有的菜单
        List<SysMenu> menuList = sysMenuMapper.selectAll(sysMenu);
        menuList = menuList.stream().sorted(Comparator.comparingInt(SysMenu::getOrderNum)).collect(Collectors.toList());

        //查询第一级所有的菜单
        sysMenu.setParentId(0);
        List<SysMenu> topLeveMenuList = sysMenuMapper.selectAll(sysMenu);
        topLeveMenuList = topLeveMenuList.stream().sorted(Comparator.comparingInt(SysMenu::getOrderNum)).collect(Collectors.toList());

        for (SysMenu topLeveMenu : topLeveMenuList) {
            topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
        }
        return topLeveMenuList;
    }

    /**
     * 分页查询
     */
    public PageInfo<SysMenu> selectPage(SysMenu sysMenu, Integer pageNum, Integer pageSize) {
        //查询所有的菜单
        List<SysMenu> menuList = sysMenuMapper.selectAll(sysMenu);
        menuList = menuList.stream().sorted(Comparator.comparingInt(SysMenu::getOrderNum)).collect(Collectors.toList());

        PageHelper.startPage(pageNum, pageSize);
        //查询第一级所有的菜单
        sysMenu.setParentId(0);
        List<SysMenu> topLeveMenuList = sysMenuMapper.selectAll(sysMenu);
        topLeveMenuList = topLeveMenuList.stream().sorted(Comparator.comparingInt(SysMenu::getOrderNum)).collect(Collectors.toList());

        PageInfo<SysMenu> pageInfo = new PageInfo<>(topLeveMenuList);
        PageResult<SysMenu> pageResult = PageUtils.getPageResult(pageInfo);
        for (SysMenu topLeveMenu : pageResult.getDataList()) {
            topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
        }
        return PageInfo.of(topLeveMenuList);
    }

    private List<SysMenu> getChildrenMenu(List<SysMenu> menuList, long menuId) {
        List<SysMenu> nextLevelMenuList = menuList.stream().filter(menu -> menu.getParentId() == menuId).collect(Collectors.toList());
        for (SysMenu nextLevelMenu : nextLevelMenuList) {
            nextLevelMenu.setChildren(getChildrenMenu(menuList, nextLevelMenu.getMenuId()));
        }
        return nextLevelMenuList;
    }

    public List<SysMenu> getMenuList(String token) {
        try {
            List<SysMenu> menuList;
            String userId = JWTUtils.parseForValue(token, "userId", String.class);
            SysUserRole sysUserRole = sysUserRoleMapper.selectByUserId(Integer.valueOf(userId));

            SysMenu sysMenu = new SysMenu();
            sysMenu.setUserId(Integer.valueOf(userId));
            sysMenu.setStatus("0");

            //用户配置了超级管理员角色，默认展示所有的菜单
            if(sysUserRole != null && sysUserRole.getRoleId() == 1){
                menuList = sysMenuMapper.selectAll(sysMenu);
            }else{
                menuList = sysMenuMapper.selectMenuList(sysMenu);
            }
            if (!CollectionUtils.isEmpty(menuList)) {
                menuList = menuList.stream().sorted(Comparator.comparingInt(SysMenu::getOrderNum)).collect(Collectors.toList());
                List<SysMenu> topLeveMenuList = menuList.stream().filter(menu -> menu.getParentId() == 0).collect(Collectors.toList());
                for (SysMenu topLeveMenu : topLeveMenuList) {
                    topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
                }
                return topLeveMenuList;
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return new ArrayList<>();
    }

    public List<SysMenuSimple> getDirMenuList(String token) {
        String userId = JWTUtils.parseForValue(token, "userId", String.class);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setUserId(Integer.valueOf(userId));
        List<SysMenuSimple> dirMenuList = sysMenuMapper.selectDirMenuList(sysMenu);
        if (!CollectionUtils.isEmpty(dirMenuList)) {
            List<SysMenuSimple> topLeveMenuDirList = dirMenuList.stream().filter(menu -> Objects.equals(menu.getParentId(), "0")).collect(Collectors.toList());
            for (SysMenuSimple topLeveMenuDir : topLeveMenuDirList) {
                topLeveMenuDir.setChildren(getChildrenMenuSimple(dirMenuList, topLeveMenuDir.getMenuId()));
            }
            SysMenuSimple simpleMenu = new SysMenuSimple();
            simpleMenu.setMenuId("0");
            simpleMenu.setLabel("一级目录");
            simpleMenu.setChildren(topLeveMenuDirList);
            return Collections.singletonList(simpleMenu);
        }
        return new ArrayList<>();
    }

    private List<SysMenuSimple> getChildrenMenuSimple(List<SysMenuSimple> menuList, String menuId) {
        List<SysMenuSimple> nextLevelMenuList = menuList.stream().filter(menu ->Objects.equals(menu.getParentId(), menuId)).collect(Collectors.toList());
        for (SysMenuSimple nextLevelMenu : nextLevelMenuList) {
            nextLevelMenu.setChildren(getChildrenMenuSimple(menuList, nextLevelMenu.getMenuId()));
        }
        return nextLevelMenuList;
    }
}
