package com.scnu.controller.front;

import cn.hutool.core.util.ObjectUtil;
import com.scnu.common.Result;
import com.scnu.common.enums.BusinessMsgEnum;
import com.scnu.entity.front.FrontUser;
import com.scnu.service.front.FrontUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xuzhijun
 * @date 2024年08月15日 16:56
 */
@RestController
@RequestMapping("/front")
public class FrontUserController {

    @Resource
    private FrontUserService frontUserService;

    // ✅ 注入 Redis
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * ==============================
     * 发送注册短信验证码
     * ==============================
     */
    @PostMapping("/sendCode")
    public Result sendCode(@RequestParam String phone) {

        if (ObjectUtil.isEmpty(phone)) {
            return Result.error(BusinessMsgEnum.PHONE_EMPTY.code, BusinessMsgEnum.PHONE_EMPTY.msg);
        }

        // 生成6位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 存入 Redis，5分钟过期
        stringRedisTemplate.opsForValue()
                .set("register:code:" + phone, code, 5, TimeUnit.MINUTES);

        // 🚨 这里应该接入阿里云短信服务
        // 目前先打印到控制台测试
        System.out.println("注册验证码是：" + code);

        return Result.success("验证码发送成功");
    }


    /**
     * ==============================
     * 登录
     * ==============================
     */
    @PostMapping("/login")
    public Result login(@RequestBody FrontUser frontUser) {

        if (ObjectUtil.isEmpty(frontUser.getUserName())
                || ObjectUtil.isEmpty(frontUser.getPassword())) {

            return Result.error(
                    BusinessMsgEnum.USER_ACCOUNT_ERROR.code,
                    BusinessMsgEnum.USER_ACCOUNT_ERROR.msg
            );
        }

        frontUser = frontUserService.login(frontUser);
        return Result.success(frontUser);
    }


    /**
     * ==============================
     * 注册（带短信验证码）
     * ==============================
     */
    @PostMapping("/register")
    public Result register(@RequestBody FrontUser frontUser) {

        if (ObjectUtil.isEmpty(frontUser.getUserName())
                || ObjectUtil.isEmpty(frontUser.getPassword())
                || ObjectUtil.isEmpty(frontUser.getPhone())
                || ObjectUtil.isEmpty(frontUser.getCode())) {

            return Result.error(BusinessMsgEnum.REGISTER_INFO_EMPTY.code, BusinessMsgEnum.REGISTER_INFO_EMPTY.msg);
        }

        // 1️⃣ 从 Redis 获取验证码
        String redisCode = stringRedisTemplate.opsForValue()
                .get("register:code:" + frontUser.getPhone());

        if (redisCode == null) {
            return Result.error(BusinessMsgEnum.VERIFICATION_CODE_EXPIRED.code, BusinessMsgEnum.VERIFICATION_CODE_EXPIRED.msg);
        }

        if (!redisCode.equals(frontUser.getCode())) {
            return Result.error(BusinessMsgEnum.VERIFICATION_CODE_ERROR.code, BusinessMsgEnum.VERIFICATION_CODE_ERROR.msg);
        }

        // 2️⃣ 删除验证码（防止重复使用）
        stringRedisTemplate.delete("register:code:" + frontUser.getPhone());

        // 3️⃣ 正常注册
        frontUserService.register(frontUser);

        return Result.success("注册成功");
    }


    /**
     * 根据ID修改
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody FrontUser frontUser) {
        frontUserService.updateById(frontUser);
        return Result.success();
    }


    /**
     * 根据ID删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        frontUserService.deleteById(id);
        return Result.success();
    }


    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        frontUserService.deleteBatch(ids);
        return Result.success();
    }


    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        FrontUser frontUser = frontUserService.selectById(id);
        return Result.success(frontUser);
    }


    /**
     * 修改用户密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody FrontUser frontUser) {
        frontUserService.updatePassword(frontUser);
        return Result.success();
    }
}