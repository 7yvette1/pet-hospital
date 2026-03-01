package com.xzj.utils;

import cn.hutool.core.util.ObjectUtil;
import com.xzj.common.enums.BusinessMsgEnum;
import com.xzj.common.exception.BusinessErrorException;
import com.xzj.entity.front.FrontUser;
import com.xzj.entity.system.SysUser;
import com.xzj.service.front.FrontUserService;
import com.xzj.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 */
@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static SysUserService staticUserService;
    private static FrontUserService staticFrontUserService;

    @Resource
    SysUserService sysUserService;
    @Resource
    FrontUserService frontUserService;

    @PostConstruct
    public void setUserService() {
        staticUserService = sysUserService;
        staticFrontUserService = frontUserService;
    }

    /**
     * 获取当前登录的用户信息
     */
    public static SysUser getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (ObjectUtil.isNotEmpty(token)) {
                String userId = JWTUtils.parseForValue(token, "userId", String.class);
                SysUser sysUser = staticUserService.selectById(Integer.valueOf(userId));
                return sysUser;
            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错", e);
            throw new BusinessErrorException(BusinessMsgEnum.TOKEN_INVALID_ERROR);
        }
        return new SysUser();  // 返回空的账号对象
    }


    /**
     * 获取前端登录的用户信息
     */
    public static FrontUser getFrontCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (ObjectUtil.isNotEmpty(token)) {
                String userId = JWTUtils.parseForValue(token, "userId", String.class);
                FrontUser frontUser = staticFrontUserService.selectById(Integer.valueOf(userId));
                return frontUser;
            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错", e);
            throw new BusinessErrorException(BusinessMsgEnum.TOKEN_INVALID_ERROR);
        }
        return new FrontUser();  // 返回空的账号对象
    }
}

