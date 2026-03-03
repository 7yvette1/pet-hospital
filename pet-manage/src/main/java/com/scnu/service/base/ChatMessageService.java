package com.scnu.service.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.entity.base.ChatMessage;
import com.scnu.mapper.base.ChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatMessageService {
    @Resource
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 新增
     */
    public void add(ChatMessage chatMessage) {
        chatMessageMapper.insert(chatMessage);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        chatMessageMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            chatMessageMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(ChatMessage chatMessage) {
        chatMessageMapper.updateById(chatMessage);
    }

    /**
     * 根据ID查询
     */
    public ChatMessage selectById(Integer id) {
        return chatMessageMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<ChatMessage> selectAll(ChatMessage chatMessage) {
        return chatMessageMapper.selectAll(chatMessage);
    }

    /**
     * 分页查询
     */
    public PageInfo<ChatMessage> selectPage(ChatMessage chatMessage, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChatMessage> list = chatMessageMapper.selectAll(chatMessage);
        return PageInfo.of(list);
    }

    public boolean saveList(List<String> messageList) {
        return true;
    }

    public List<ChatMessage> getMessageList(ChatMessage chatMessage) {
        //判断redis中是否还有数据没有保存数据库
        String chatListId = chatMessage.getChatListId();
        List<ChatMessage> redisMessages = new ArrayList<>();
        ListOperations<String, String> operations = redisTemplate.opsForList();
        List<String> messageList = operations.range("chat:message:" + chatListId, 0, -1);
        if(messageList!=null){
            redisMessages = _getMessageListFromRedis(messageList);
        }
        List<ChatMessage> dbMessages = chatMessageMapper.getMessageList(chatMessage);
        dbMessages.addAll(redisMessages);
        dbMessages.sort(Comparator.comparing(ChatMessage::getSendTime));
        return dbMessages;
    }








    public List<ChatMessage> _getMessageListFromRedis(List<String> messageList) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        if(!messageList.isEmpty()){
            for (String message : messageList) {
                ChatMessage chatMessage = new ChatMessage();
                JSONObject jsonObject = JSON.parseObject(message);
                chatMessage.setChatListId(jsonObject.getString("chatListId"));
                chatMessage.setContent(jsonObject.getString("content"));
                chatMessage.setFromUserId(jsonObject.getString("fromUserId"));
                chatMessage.setToUserId(jsonObject.getString("toUserId"));
                chatMessage.setIsRead(jsonObject.getString("isRead"));
                chatMessage.setSendTime(jsonObject.getString("sendTime"));
                chatMessages.add(chatMessage);
            }
        }
        return chatMessages;
    }
}