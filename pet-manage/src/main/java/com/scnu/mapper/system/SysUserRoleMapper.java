package com.scnu.mapper.system;

import com.scnu.entity.system.SysUserRole;


/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysUserRoleMapper {

    /**
     * 新增
     */
    void add(SysUserRole sysUserRole);


    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据
     */
    SysUserRole selectByUserId(Integer id);
}




