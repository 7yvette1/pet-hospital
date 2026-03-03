package com.scnu.controller;

import com.scnu.common.Result;
import com.scnu.entity.system.SysUser;
import com.scnu.service.system.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xuzhijun
 * @date 2024年05月20日 16:33
 */
@RestController
@RequestMapping("/manage")
public class LoginController {

    @Resource
    SysUserService sysUserService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody SysUser sysUser) {
        Map<String, String> data = sysUserService.login(sysUser);
        return Result.success(data);
    }
}
