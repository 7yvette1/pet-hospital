package com.scnu.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuzhijun
 * @date 2024年06月06日 23:45
 */
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

}
