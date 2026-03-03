
package com.xzj.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;

public class SmsUtil {

    @Value("${aliyun.sms.accessKeyId}")
    private static String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private static String accessKeySecret;

    public static void sendSms(String phone, String code) throws Exception {

        IClientProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                accessKeyId,
                accessKeySecret
        );

        DefaultProfile.addEndpoint(
                "cn-hangzhou",
                "cn-hangzhou",
                "Dysmsapi",
                "dysmsapi.aliyuncs.com"
        );

        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);

        request.setSignName("你的签名名称");   // 必须和阿里云一致
        request.setTemplateCode("SMS_332111001"); // 必须填你的模板编号

        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        client.getAcsResponse(request);
    }
}