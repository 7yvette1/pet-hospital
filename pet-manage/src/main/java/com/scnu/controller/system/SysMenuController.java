package com.scnu.controller.system;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.system.SysMenu;
import com.scnu.entity.system.SysMenuSimple;
import com.scnu.service.system.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 16:58
 */
@RestController
@RequestMapping("/sysmenu")
public class SysMenuController {

    @Resource
    SysMenuService sysMenuService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysMenu sysMenu) {
        sysMenuService.add(sysMenu);
        return Result.success();
    }

    /**
     * 根据ID修改
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.success();
    }

    /**
     * 根据ID删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        sysMenuService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        sysMenuService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        SysMenu sysMenu = sysMenuService.selectById(id);
        return Result.success(sysMenu);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.selectAll(sysMenu);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(SysMenu sysMenu,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SysMenu> page = sysMenuService.selectPage(sysMenu, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * 查询当前用户所有菜单列表
     */
    @GetMapping("/getMenuList")
    public Result getMenuList(@RequestHeader("token") String token){
        List<SysMenu> list = sysMenuService.getMenuList(token);
        return Result.success(list);
    }


    /**
     * 获取目录菜单
     */
    @GetMapping("/getDirMenuList")
    public Result getDirMenuList(@RequestHeader("token") String token){
        List<SysMenuSimple> list = sysMenuService.getDirMenuList(token);
        return Result.success(list);
    }

}
