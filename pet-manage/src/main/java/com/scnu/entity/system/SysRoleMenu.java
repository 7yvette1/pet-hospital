package com.scnu.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色权限关联表
 * @author xuzhijun
 * @date 2024年05月21日 15:12
 */
@Data
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;

}