package com.xzj.service.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.base.ChatList;
import com.xzj.mapper.base.ChatListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatListService {

    @Resource
    private ChatListMapper chatListMapper;

    /**
     * 新增
     */
    public void add(ChatList chatList) {
        chatListMapper.insert(chatList);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        chatListMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            chatListMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(ChatList chatList) {
        chatListMapper.updateById(chatList);
    }

    /**
     * 根据ID查询
     */
    public ChatList selectById(Integer id) {
        return chatListMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<ChatList> selectAll(ChatList chatList) {
        return chatListMapper.selectAll(chatList);
    }

    /**
     * 分页查询
     */
    public PageInfo<ChatList> selectPage(ChatList chatList, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChatList> list = chatListMapper.selectAll(chatList);
        return PageInfo.of(list);
    }

}