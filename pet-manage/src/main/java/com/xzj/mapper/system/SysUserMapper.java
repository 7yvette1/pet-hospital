package com.xzj.mapper.system;

import com.xzj.entity.system.SysUser;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysUserMapper {
    /**
     * 新增
     */
    void add(SysUser sysUser);

    /**
     * 根据ID修改
     */
    void updateById(SysUser sysUser);

    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询
     */
    SysUser selectById(Integer id);

    /**
     * 查询所有
     */
    List<SysUser> selectAll(SysUser sysUser);

    List<SysUser> selectDoc(SysUser sysUser);

    /**
     * 根据用户名和密码查询用户
     */
    SysUser selectByUserAndPassword(SysUser sysUser);

    void updatePassword(SysUser sysUser);
}
