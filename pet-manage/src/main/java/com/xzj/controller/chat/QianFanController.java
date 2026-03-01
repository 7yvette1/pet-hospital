package com.xzj.controller.chat;

import com.xzj.entity.chat.QianFanResponseBean;
import com.xzj.service.chat.QianFanService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * 文心一言-千帆大模型
 * @author xuzhijun
 * @date 2024/9/3 10:55
 */
@RestController
public class QianFanController {

    //用于流式请求第三方的实现类
    @Resource
    QianFanService qianFanService;

    //通过stream返回流式数据
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<QianFanResponseBean>> getChat(@RequestParam("content")String content) {
        //实现类发送消息并获取返回结果
        return qianFanService.doQianFanStream(content)
                //进行结果的封装，再返回给前端
                .map(aiAnswerDTO -> ServerSentEvent.<QianFanResponseBean>builder()
                        .data(aiAnswerDTO)
                        .build()
                )
                //发生异常时发送空对象
                .onErrorResume(e -> Flux.empty());
    }

}
