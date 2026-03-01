package com.xzj.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色表
 * @author xuzhijun
 * @date 2024年05月21日 19:47
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String status;
    private String createBy;
    private String createTime;
    private String remark;

    /**
     * 角色下菜单ID
     */
    private List<Integer> permissionList;
}
