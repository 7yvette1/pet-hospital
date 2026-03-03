package com.scnu.controller.system;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.system.SysRole;
import com.scnu.service.system.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 16:58
 */
@RestController
@RequestMapping("/sysrole")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysRole sysRole) {
        sysRoleService.add(sysRole);
        return Result.success();
    }

    /**
     * 根据ID修改
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.success();
    }

    /**
     * 根据ID删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        sysRoleService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        sysRoleService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        SysRole sysRole = sysRoleService.selectById(id);
        return Result.success(sysRole);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SysRole sysRole) {
        List<SysRole> list = sysRoleService.selectAll(sysRole);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(SysRole sysRole,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SysRole> page = sysRoleService.selectPage(sysRole, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * 分配权限
     */
    @PostMapping("/grantPermissions")
    public Result grantPermissions(@RequestBody SysRole sysRole) {
        sysRoleService.grantPermissions(sysRole);
        return Result.success();
    }
}
