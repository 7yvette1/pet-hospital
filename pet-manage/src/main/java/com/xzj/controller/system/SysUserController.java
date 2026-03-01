package com.xzj.controller.system;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.system.SysUser;
import com.xzj.service.system.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xuzhijun
 * @date 2024年06月06日 17:08
 */
@RestController
@RequestMapping(value = {"/sysuser", "/front/sysuser"})
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysUser sysUser) {
        sysUserService.add(sysUser);
        return Result.success();
    }

    /**
     * 根据ID修改
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return Result.success();
    }

    /**
     * 根据ID删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        sysUserService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        sysUserService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        SysUser sysUser = sysUserService.selectById(id);
        return Result.success(sysUser);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SysUser sysUser) {
        List<SysUser> list = sysUserService.selectAll(sysUser);
        return Result.success(list);
    }

    @GetMapping("/selectDoc")
    public Result selectDoc(SysUser sysUser) {
        List<SysUser> list = sysUserService.selectDoc(sysUser);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(SysUser sysUser,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SysUser> page = sysUserService.selectPage(sysUser, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectDocPage")
    public Result selectDocPage(SysUser sysUser,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SysUser> page = sysUserService.selectDocPage(sysUser, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分配角色
     */
    @PostMapping("/grantRole")
    public Result grantRole(@RequestBody SysUser sysUser) {
        sysUserService.grantRole(sysUser);
        return Result.success();
    }


    /**
     * 返回用户信息
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam("token") String token) {
        SysUser userInfo = sysUserService.getUserInfo(token);
        return Result.success(userInfo);
    }


    /**
     * 首页用户情况展示
     */
    @GetMapping("/getUserCount")
    public Result getUserCount() {
        Map<String,Object> map = sysUserService.getUserCount();
        return Result.success(map);
    }


    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody SysUser sysUser) {
        sysUserService.updatePassword(sysUser);
        return Result.success();
    }

}
