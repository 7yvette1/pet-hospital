package com.xzj.entity.front;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuzhijun
 * @date 2024年08月15日 16:58
 */
@Data
public class FrontUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String userName;
    private String nickName;
    private String password;
    private String status;
    private String email;
    private String phone;
    private String sex;
    private String avatar;
    private String createBy;
    private String createTime;
    private String remark;

    private String token;
    private String newPassword;
}
