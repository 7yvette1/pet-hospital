package com.xzj.controller.bus;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.bus.RegisterInfo;
import com.xzj.service.bus.RegisterInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/registerInfo", "/front/registerInfo"})
public class RegisterInfoController {

    @Resource
    private RegisterInfoService registerInfoService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody RegisterInfo registerInfo) {
        registerInfoService.add(registerInfo);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        registerInfoService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        registerInfoService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody RegisterInfo registerInfo) {
        registerInfoService.updateById(registerInfo);
        return Result.success();
    }

    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestBody RegisterInfo registerInfo) {
        registerInfoService.updateStatus(registerInfo);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        RegisterInfo registerInfo = registerInfoService.selectById(id);
        return Result.success(registerInfo);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(RegisterInfo registerInfo) {
        List<RegisterInfo> list = registerInfoService.selectAll(registerInfo);
        return Result.success(list);
    }


    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(RegisterInfo registerInfo,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RegisterInfo> page = registerInfoService.selectPage(registerInfo, pageNum, pageSize);
        return Result.success(page);
    }


    @GetMapping("/selectPage1")
    public Result selectPage1(RegisterInfo registerInfo,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RegisterInfo> page = registerInfoService.selectPage1(registerInfo, pageNum, pageSize);
        return Result.success(page);
    }


    @GetMapping("/selectPage2")
    public Result selectPage2(RegisterInfo registerInfo,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RegisterInfo> page = registerInfoService.selectPage2(registerInfo, pageNum, pageSize);
        return Result.success(page);
    }


    @GetMapping("/selectPage3")
    public Result selectPage3(RegisterInfo registerInfo,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RegisterInfo> page = registerInfoService.selectPage3(registerInfo, pageNum, pageSize);
        return Result.success(page);
    }


}