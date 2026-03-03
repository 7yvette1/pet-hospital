package com.scnu.common.enums;

public enum ResultCodeEnum {
    SUCCESS("200", "处理成功!"),

    SYSTEM_ERROR("500", "服务器内部错误!"),
    ;

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
