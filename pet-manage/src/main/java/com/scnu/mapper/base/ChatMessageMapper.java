package com.scnu.mapper.base;

import com.scnu.entity.base.ChatMessage;

import java.util.List;

public interface ChatMessageMapper {

 /**
      * 新增
    */
    int insert(ChatMessage chatMessage);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(ChatMessage chatMessage);

    /**
      * 根据ID查询
    */
    ChatMessage selectById(Integer id);

    /**
      * 查询所有
    */
    List<ChatMessage> selectAll(ChatMessage chatMessage);

    List<ChatMessage> getMessageList(ChatMessage chatMessage);
}