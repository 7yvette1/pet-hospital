package com.xzj.common.enums;

/**
 * 业务异常提示信息枚举类
 * @author xuzhijun
 * @date 2024年01月23日 17:32
 */
public enum BusinessMsgEnum {
    /** 500 */
    UNEXPECTED_EXCEPTION("500", "系统发生异常，请联系管理员！"),

    TOKEN_INVALID_ERROR("401", "无效的token！"),

    NOT_FOUND("404", "未找到该资源!"),

    USER_ACCOUNT_ERROR("5000", "账号或密码错误！"),

    USER_STATUS_ERROR("5001", "用户状态异常，请联系管理员！"),

    USER_REPEAT_ERROR("5002", "用户名重复！"),

    VERIFICATION_CODE_EXPIRED("5003", "验证码已过期！"),

    VERIFICATION_CODE_ERROR("5004", "验证码错误！"),

    FROMUSERID_CODE_ERROR("5006", "发送用户不存在！"),

    TOUSERID_CODE_ERROR("5007", "接收用户不存在！"),

    PHONE_EMPTY("5008", "手机号不能为空！"),

    REGISTER_INFO_EMPTY("5009", "信息填写不完整！");



    /**
     * 消息码
     */
    public String code;
    /**
     * 消息内容
     */
    public String msg;

    BusinessMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
