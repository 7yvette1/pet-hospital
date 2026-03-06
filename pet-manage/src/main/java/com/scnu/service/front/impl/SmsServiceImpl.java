package com.scnu.service.front.impl;


import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.CheckSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.scnu.service.front.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final Client dypnsClient;
    private final StringRedisTemplate redisTemplate;

    private static final String SMS_LOCK_PREFIX = "sms:lock:";       // 发送锁
    private static final long SMS_LOCK_EXPIRE = 60;                  // 发送锁：60秒

    @Override
    public void sendLoginCode(String phone) {
        String lockKey = SMS_LOCK_PREFIX + phone;

        // 发送频率限制
        if (redisTemplate.hasKey(lockKey)) {
            throw new RuntimeException("操作过于频繁，请稍后再试");
        }

        try {
            // 调用阿里云短信发送接口
            SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest()
                    .setPhoneNumber(phone)
                    .setSignName("速通互联验证码")
                    .setTemplateCode("100001")
                    .setTemplateParam("{\"code\":\"##code##\",\"min\":\"5\"}");

            RuntimeOptions runtime = new RuntimeOptions();
            SendSmsVerifyCodeResponse response = dypnsClient.sendSmsVerifyCodeWithOptions(request, runtime);

            if ("OK".equals(response.getBody().getCode())) {

                // 设置发送锁
                redisTemplate.opsForValue().set(lockKey, "1", SMS_LOCK_EXPIRE, TimeUnit.SECONDS);
            } else {
                throw new RuntimeException("短信发送失败：" + response.getBody().getMessage());
            }

        } catch (TeaException e) {
            System.err.println("阿里云接口调用异常: " + e.getMessage());
            throw new RuntimeException("短信发送异常", e);
        } catch (Exception e) {
            System.err.println("系统异常: " + e.getMessage());
            throw new RuntimeException("短信发送异常", e);
        }
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        try {
            // 1. 使用 CheckSmsVerifyCodeRequest (不需要 SmsToken)
            var request = new CheckSmsVerifyCodeRequest()
                    .setPhoneNumber(phone)
                    .setVerifyCode(code); // 这个方法名在 CheckRequest 中是 setVerifyCode

            var runtime = new com.aliyun.teautil.models.RuntimeOptions();

            // 2. 调用 checkSmsVerifyCodeWithOptions
            var response = dypnsClient.checkSmsVerifyCodeWithOptions(request, runtime);

            // 3. 判断结果
            return response.getBody() != null && "OK".equals(response.getBody().getCode());

        } catch (Exception e) {
            System.err.println("校验异常: " + e.getMessage());
            return false;
        }
    }
}
