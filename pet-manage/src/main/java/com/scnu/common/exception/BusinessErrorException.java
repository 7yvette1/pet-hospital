package com.scnu.common.exception;

import com.scnu.common.enums.BusinessMsgEnum;

/**
 * 自定义业务异常
 * @author xuzhijun
 * @date 2024年01月23日 17:31
 */
public class BusinessErrorException extends RuntimeException{
    private static final long serialVersionUID = -7480022450501760611L;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常提示信息
     */
    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessErrorException(BusinessMsgEnum businessMsgEnum) {
        this.code = businessMsgEnum.code;
        this.message = businessMsgEnum.msg;
    }
}
