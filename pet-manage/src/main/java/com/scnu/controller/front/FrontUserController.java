package com.scnu.controller.front;

import cn.hutool.core.util.ObjectUtil;
import com.scnu.common.Result;
import com.scnu.common.enums.BusinessMsgEnum;
import com.scnu.entity.front.FrontUser;
import com.scnu.service.front.FrontUserService;
import com.scnu.service.front.SmsService; // 直接调用你的 SmsService
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 前台用户控制器（重构版，使用 SmsService 处理验证码）
 */
@RestController
@RequestMapping("/front")
@RequiredArgsConstructor
public class FrontUserController {

    private final FrontUserService frontUserService;
    private final SmsService smsService;

    /**
     * 发送短信验证码
     */
    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");

        if (ObjectUtil.isEmpty(phone)) {
            return Result.error(BusinessMsgEnum.PHONE_EMPTY.code, BusinessMsgEnum.PHONE_EMPTY.msg);
        }

        try {
            smsService.sendLoginCode(phone); // 调用 SmsService 发送验证码
            return Result.success("验证码已发送");
        } catch (RuntimeException e) {
            return Result.error("5012", e.getMessage());
        }
    }

    /**
     * 校验短信验证码（注册/登录时调用）
     */
    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        String code = map.get("code");

        if (ObjectUtil.isEmpty(phone) || ObjectUtil.isEmpty(code)) {
            return Result.error("5013", "手机号或验证码不能为空");
        }

        boolean ok = smsService.verifyCode(phone, code); // 调用 SmsService 校验

        if (!ok) {
            return Result.error("5014", "验证码校验失败");
        }

        return Result.success("验证成功");
    }

    /**
     * 登录
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
     * 注册（带短信验证码校验）
     */
    @PostMapping("/register")
    public Result register(@RequestBody FrontUser frontUser) {
        if (ObjectUtil.isEmpty(frontUser.getUserName())
                || ObjectUtil.isEmpty(frontUser.getPassword())
                || ObjectUtil.isEmpty(frontUser.getPhone())
                || ObjectUtil.isEmpty(frontUser.getCode())) {

            return Result.error(
                    BusinessMsgEnum.REGISTER_INFO_EMPTY.code,
                    BusinessMsgEnum.REGISTER_INFO_EMPTY.msg
            );
        }

        // 调用 SmsService 校验验证码
        boolean ok = smsService.verifyCode(frontUser.getPhone(), frontUser.getCode());
        if (!ok) {
            return Result.error(
                    BusinessMsgEnum.VERIFICATION_CODE_ERROR.code,
                    BusinessMsgEnum.VERIFICATION_CODE_ERROR.msg
            );
        }

        // 执行注册
        frontUserService.register(frontUser);

        return Result.success("注册成功");
    }

    // 其他 CRUD 或查询方法可以保留原有逻辑
}