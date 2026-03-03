package com.scnu.entity.chat;

import lombok.Data;

import java.util.List;


@Data
public class QianFanChatBean {
    // 聊天上下文信息
    private List<QFMessage> messages;
    // 一个可触发函数的描述列表
    private List<QFFunction> functions;
    //（1）较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定
    //（2）默认0.8，范围 (0, 1.0]，不能为0
    //（3）建议该参数和top_p只设置1个
    //（4）建议top_p和temperature不要同时更改
    private float temperature = 0.8f;
    //（1）影响输出文本的多样性，取值越大，生成文本的多样性越强
    //（2）默认0.8，取值范围 [0, 1.0]
    //（3）建议该参数和temperature只设置1个
    //（4）建议top_p和temperature不要同时更改
    private float top_p = 0.8f;
    // 通过对已生成的token增加惩罚，减少重复生成的现象。说明：
    //（1）值越大表示惩罚越大
    //（2）默认1.0，取值范围：[1.0, 2.0]
    private float penalty_score = 1.0f;
    // 是否以流式接口的形式返回数据，默认false
    private boolean stream = false;
    // 模型人设，主要用于人设设定
    private String system;
    // 生成停止标识，当模型生成结果以stop中某个元素结尾时，停止文本生成。说明：
    //（1）每个元素长度不超过20字符
    //（2）最多4个元素
    private List<String> stop;
    // 是否强制关闭实时搜索功能，默认false，表示不关闭
    private boolean disable_search = false;
    // 是否开启上角标返回，说明：
    //（1）开启后，有概率触发搜索溯源信息search_info，search_info内容见响应参数介绍
    //（2）默认false，不开启
    private boolean enable_citation = false;
    // 指定响应内容的格式，说明：
    //（1）可选值：
    //· json_object：以json格式返回，可能出现不满足效果情况
    //· text：以文本格式返回
    //（2）如果不填写参数response_format值，默认为text
    private String response_format;
    // 最终用户的唯一标识符
    private String user_id;
    @Data
    public static class QFMessage{
        // 对话角色设定
        private String role;
        // 对话内容
        private String content;
        // message作者；当role=function时，必填，且是响应内容中function_call中的name
        private String name;
        // 函数调用
        private String function_call;
    }
    @Data
    public static class QFFunction{
        // 函数名
        private String name;
        // 函数描述
        private String description;
        // 函数请求参数
        private String parameters;
        // 函数响应参数
        private String responses;
        // function调用的一些历史示例
        private List<QFMessage> examples;
    }
    @Data
    public static class QFFunctionCall{
        // 触发的function名
        private String name;
        // 请求参数
        private String arguments;
        // 模型思考过程
        private String thoughts;
    }
}
