package com.scnu.entity.bus;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评论表
 */
@Data
public class BusComment implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    /** 内容 */
    private String content;
    /** 评论人 */
    private Integer userId;
    /** 父级ID */
    private Integer pid;
    private Integer rootId;
    /** 评论时间 */
    private String time;
    /** 关联ID */
    private Integer fid;
    /** 模块 */
    private String module;

    private String userName;
    private String avatar;
    private List<BusComment> children;
    private String replyUser;

}
