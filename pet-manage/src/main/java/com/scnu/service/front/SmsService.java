package com.scnu.service.front;

public interface SmsService {

    void sendLoginCode(String phone);

    boolean verifyCode(String phone, String code);

}
