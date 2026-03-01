package com.xzj.mapper.system;

import com.xzj.entity.system.SysMenu;
import com.xzj.entity.system.SysMenuSimple;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysMenuMapper {
    /**
     * 新增
     */
    void add(SysMenu sysMenu);

    /**
     * 根据ID修改
     */
    void updateById(SysMenu sysMenu);

    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询
     */
    SysMenu selectById(Integer id);

    /**
     * 根据parentId查询
     */
    SysMenu selectByParentId(Integer parentId);

    /**
     * 查询所有
     */
    List<SysMenu> selectAll(SysMenu sysMenu);

    /**
     * 查询当前用户所有菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    /**
     * 获取目录菜单
     */
    List<SysMenuSimple> selectDirMenuList(SysMenu sysMenu);
}
