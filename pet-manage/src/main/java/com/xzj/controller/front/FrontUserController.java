package com.xzj.controller.front;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.common.enums.BusinessMsgEnum;
import com.xzj.entity.front.FrontUser;
import com.xzj.service.front.FrontUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年08月15日 16:56
 */
@RestController
@RequestMapping("/front")
public class FrontUserController {

    @Resource
    FrontUserService frontUserService;


    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody FrontUser frontUser) {
        if (ObjectUtil.isEmpty(frontUser.getUserName()) || ObjectUtil.isEmpty(frontUser.getPassword())) {
            return Result.error(BusinessMsgEnum.USER_ACCOUNT_ERROR.code,BusinessMsgEnum.USER_ACCOUNT_ERROR.msg);
        }
        frontUser = frontUserService.login(frontUser);
        return Result.success(frontUser);
    }


    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody FrontUser frontUser) {
        if (ObjectUtil.isEmpty(frontUser.getUserName()) || ObjectUtil.isEmpty(frontUser.getPassword())) {
            return Result.error(BusinessMsgEnum.USER_ACCOUNT_ERROR.code,BusinessMsgEnum.USER_ACCOUNT_ERROR.msg);
        }
        frontUserService.register(frontUser);
        return Result.success();
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
