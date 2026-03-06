package com.scnu.common.config;

import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunSmsConfig {

    // 建议将 AK/SK 放在 application.yml 中
    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Bean
    public com.aliyun.dypnsapi20170525.Client dypnsClient() throws Exception {
        Config config = new Config()
                // 必填：您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填：您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);

        // 访问的域名（Endpoint）
        config.endpoint = "dypnsapi.aliyuncs.com";

        return new com.aliyun.dypnsapi20170525.Client(config);
    }
}