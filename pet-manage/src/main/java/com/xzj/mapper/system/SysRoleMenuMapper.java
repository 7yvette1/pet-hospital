package com.xzj.mapper.system;

import com.xzj.entity.system.SysRoleMenu;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysRoleMenuMapper{

    List<Integer> selectPermissions(Integer roleId);

    /**
     * 新增
     */
    void add(SysRoleMenu sysRoleMenu);


    /**
     * 根据ID删除
     */
    void deleteById(Integer id);
}




