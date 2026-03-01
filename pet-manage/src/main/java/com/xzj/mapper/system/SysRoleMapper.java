package com.xzj.mapper.system;

import com.xzj.entity.system.SysRole;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysRoleMapper {
    /**
     * 新增
     */
    void add(SysRole sysRole);

    /**
     * 根据ID修改
     */
    void updateById(SysRole sysRole);

    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询
     */
    SysRole selectById(Integer id);

    /**
     * 查询所有
     */
    List<SysRole> selectAll(SysRole sysRole);

    /**
     * 查询用户拥有的角色
     */
    List<SysRole> selectUserRole(Integer userId);
}
