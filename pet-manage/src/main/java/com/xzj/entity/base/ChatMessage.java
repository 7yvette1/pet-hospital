package com.xzj.entity.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 聊天记录表
*/
@Data
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private String chatListId;
    private String fromUserId;
    private String toUserId;
    private String fromUserNick;
    private String toUserNick;
    private String content;
    private String sendTime;
    private String isRead;


}