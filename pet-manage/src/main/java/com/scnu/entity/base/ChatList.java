package com.scnu.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 聊天关联表
*/
@Data
public class ChatList implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private String productId;
    private String productImage;

    private String fromUserId;
    private String fromUserAvatar;
    private String fromUserNick;

    private String toUserId;
    private String toUserNick;
    private String toUserAvatar;

    private String createTime;
    private String updateTime;


}