package com.scnu.mapper.system;

import com.scnu.entity.system.SysLogin;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface SysLoginMapper {
    /**
     * 新增
     */
    void add(SysLogin sysLogin);

    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询
     */
    SysLogin selectById(Integer id);

    /**
     * 查询所有
     */
    List<SysLogin> selectAll(SysLogin sysLogin);
}
