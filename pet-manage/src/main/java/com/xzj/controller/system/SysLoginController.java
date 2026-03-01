package com.xzj.controller.system;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.other.VerificationCodeVo;
import com.xzj.entity.system.SysLogin;
import com.xzj.service.system.SysLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 16:58
 */
@RestController
@RequestMapping("/syslogin")
public class SysLoginController {

    @Resource
    SysLoginService sysLoginService;


    /**
     * 根据ID删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        sysLoginService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        sysLoginService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        SysLogin sysLogin = sysLoginService.selectById(id);
        return Result.success(sysLogin);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SysLogin sysLogin) {
        List<SysLogin> list = sysLoginService.selectAll(sysLogin);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(SysLogin sysLogin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SysLogin> page = sysLoginService.selectPage(sysLogin, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * 获取验证码
     */
    @GetMapping("/getVerificationCode")
    public Result getVerificationCode() {
        VerificationCodeVo vo = sysLoginService.getVerificationCode();
        return Result.success(vo);
    }

}
