package com.xzj.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 * @author xuzhijun
 * @date 2024年05月21日 15:12
 */
@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private String routeName;
    private String menuName;
    private String label;
    private Integer parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private String menuType;
    private String status;
    private String icon;
    private String createBy;
    private String createTime;
    private String remark;

    //虚拟字段
    private List<SysMenu> children;
    private Integer userId;
}
