package com.xzj.common.config;

import com.xzj.common.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;

/**
 * MVC 配置
 * @author xuzhijun
 * @date 2024年01月24日 17:02
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Resource
    private JWTInterceptor jwtInterceptor;

    /**
     * 注册登录拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/manage/login/**")
                .excludePathPatterns("/syslogin/getVerificationCode")
                .excludePathPatterns("/front/**")
                .excludePathPatterns("/chat/**")
                .excludePathPatterns("/files/**");
    }

}
