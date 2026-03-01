package com.xzj.entity.system;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户登录
 * @author xuzhijun
 * @date 2024年05月21日 19:47
 */
@Data
public class SysLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userName;
    private String loginAddr;
    private String loginTime;
}
