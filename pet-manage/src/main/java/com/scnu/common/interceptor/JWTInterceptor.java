package com.scnu.common.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.scnu.common.enums.BusinessMsgEnum;
import com.scnu.common.exception.BusinessErrorException;
import com.scnu.utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求路径
        String uri = request.getRequestURI();

        /**
         * 放行接口（不需要token）
         */
        if (uri.contains("/login")
                || uri.contains("/register")
                || uri.contains("/front/sendCode")
                || uri.contains("/error")) {
            return true;
        }

        // 获取请求头 token
        String token = request.getHeader("token");

        if (ObjectUtil.isEmpty(token)) {
            throw new BusinessErrorException(BusinessMsgEnum.TOKEN_INVALID_ERROR);
        }

        try {
            // 验证 token
            JWTUtils.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessErrorException(BusinessMsgEnum.TOKEN_INVALID_ERROR);
        }

        return true; // 放行
    }
}