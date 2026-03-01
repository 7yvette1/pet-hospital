package com.xzj.socket;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xzj.common.enums.BusinessMsgEnum;
import com.xzj.common.exception.BusinessErrorException;
import com.xzj.service.base.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{roomId}/{userId}")
@Component
@Slf4j
public class WebSocketServer implements ApplicationContextAware {

	public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

	private static ChatMessageService chatMessageService;
	private static StringRedisTemplate redisTemplate;
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        // 初始化静态变量
        if (redisTemplate == null) {
            redisTemplate = context.getBean(StringRedisTemplate.class);
        }
        if (chatMessageService == null) {
            chatMessageService = context.getBean(ChatMessageService.class);
        }
    }

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		if (StrUtil.isNotEmpty(userId)) {
			// 加入新用户
			if (sessionMap.containsKey(userId)) {
				sessionMap.remove(userId);
			}
			sessionMap.put(userId, session);
			log.info("上线，userId={}, 当前在线人数为：{}", userId, sessionMap.size());
		}
	}

	@OnMessage
	public void onMessage(String message, Session session, @PathParam("userId") String userId) {
		log.info("服务端收到id={}的消息:{}", userId, message);
		JSONObject jsonObject = JSON.parseObject(message);
		String toUserId = jsonObject.getString("toUserId");
		if(StrUtil.isEmpty(toUserId)) throw new BusinessErrorException(BusinessMsgEnum.TOUSERID_CODE_ERROR);
		String fromUserId = jsonObject.getString("fromUserId");
		if (StrUtil.isEmpty(fromUserId)) throw new BusinessErrorException(BusinessMsgEnum.FROMUSERID_CODE_ERROR);
		String roomId = jsonObject.getString("chatListId");
		Session toSession = sessionMap.get(toUserId);
		if (null != toSession) {
			jsonObject.put("isRead","1");
			redisTemplate.opsForList().rightPush("chat:message:" + roomId, jsonObject.toJSONString());
			this.sendMessage(message, toSession);
			log.info("发送给用户userId={}，消息：{}", toUserId, message);
		} else {
			jsonObject.put("isRead", "0");
			redisTemplate.opsForList().rightPush("chat:message:" + roomId, jsonObject.toJSONString());
			log.info("发送失败，用户username={}未上线", toUserId);
		}
	}
	/**
	 * 服务端发送消息给客户端
	 */
	private void sendMessage(String message, Session toSession) {
		try {
			log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
			toSession.getBasicRemote().sendText(message);
		} catch (Exception e) {
			log.error("服务端发送消息给客户端失败", e);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("roomId") String roomId, @PathParam("userId") String userId) {
		ListOperations<String, String> operations = redisTemplate.opsForList();
		List<String> messageList = operations.range("chat:message:" + roomId, 0, -1);
		if(!messageList.isEmpty()){
			boolean result = chatMessageService.saveList(messageList);
			if (result) redisTemplate.delete("chat:message:" + roomId);
		}
		sessionMap.remove(userId);
		log.info("userId={}下线, 当前在线人数为：{}", userId, sessionMap.size());
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		log.error(error.toString());
	}
}
