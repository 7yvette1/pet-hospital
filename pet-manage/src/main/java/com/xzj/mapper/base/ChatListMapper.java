package com.xzj.mapper.base;

import com.xzj.entity.base.ChatList;

import java.util.List;

public interface ChatListMapper {

 /**
      * 新增
    */
    int insert(ChatList chatList);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(ChatList chatList);

    /**
      * 根据ID查询
    */
    ChatList selectById(Integer id);

    /**
      * 查询所有
    */
    List<ChatList> selectAll(ChatList chatList);

}