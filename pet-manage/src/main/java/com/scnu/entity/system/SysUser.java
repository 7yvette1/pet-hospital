package com.scnu.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 * @author xuzhijun
 * @date 2024年06月05日 22:47
 */
@Data
public class SysUser implements Serializable {

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
    private String job;
    private String descr;
    private List<String> beGoodAt;

    private String flagKey;
    private String verificationCode;

    private String newPassword;

    /**
     * 用户角色ID
     */
    private List<Integer> roleIdList;

    /**
     * 用户角色名称
     */
    private List<String> roleNameList;

}
