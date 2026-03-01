package com.xzj.service.chat;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzj.entity.chat.QFAccessTokenBean;
import com.xzj.entity.chat.QFConts;
import com.xzj.entity.chat.QianFanChatBean;
import com.xzj.entity.chat.QianFanResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class QianFanService {
    //webflux的client
    private WebClient webClient;

    @Value("${bdqfllm.API_KEY}")
    private String API_KEY;

    @Value("${bdqfllm.SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${bdqfllm.API_HOST}")
    private String API_HOST;

    @Value("${bdqfllm.API_URI}")
    private String API_URI;

    @Value("${bdqfllm.TOKEN_URL}")
    private String TOKEN_URL;

    //用于读取第三方的返回结果
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void postConstruct() {
        //创建webflux的client
        this.webClient = WebClient.builder()
                //填写对应的api地址
                .baseUrl(API_HOST)
                //设置默认请求类型
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    //请求stream的主题
    public Flux<QianFanResponseBean> doQianFanStream(String content) {
        String requestURI = API_URI.replace("ACCESS_TOKEN",getAccessToken());
        //组装请求参数
        QianFanChatBean requestBean =  getRequestData(content);
        //构建请求json
        String paramJson = JSONUtil.toJsonStr(requestBean);;
        //使用webClient发送消息
        return this.webClient.post()
                //请求uri
                .uri(requestURI)
                //设置流式响应
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.defaultCharset())
                .body(BodyInserters.fromValue(paramJson))
                .retrieve()
                .bodyToFlux(String.class)
                //接收到消息的处理方法
                .flatMap(result -> handleWebClientResponse(result));

    }

    private Flux<QianFanResponseBean> handleWebClientResponse(String resp) {
        log.info("百度千帆返回:======>{}",resp);
        QianFanResponseBean result = JSON.parseObject(resp, QianFanResponseBean.class);
        //is_end=true结束标识
        if (result.isIs_end()){
            return Flux.empty();
        }
        try {
            //返回获得的结果
            return Flux.just(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public  String getAccessToken(){
        String REQ_URL = TOKEN_URL.replace("API_KEY",API_KEY)
                .replace("SECRET_KEY",SECRET_KEY);
        String result = HttpRequest.get(REQ_URL).execute().body();
        QFAccessTokenBean tokenBean = JSON.parseObject(result, QFAccessTokenBean.class);
        return tokenBean.getAccess_token();
    }

    public static QianFanChatBean getRequestData(String content) {
        QianFanChatBean requestBean = new QianFanChatBean();
        List<QianFanChatBean.QFMessage> messages = new ArrayList<>();
        QianFanChatBean.QFMessage message = new QianFanChatBean.QFMessage();
        message.setRole(QFConts.QFRole.USER);
        message.setContent(content);
        messages.add(message);
        requestBean.setMessages(messages);
        requestBean.setStream(true);
        return requestBean;
    }
}
