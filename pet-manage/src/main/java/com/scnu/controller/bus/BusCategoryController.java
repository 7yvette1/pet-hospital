package com.scnu.controller.bus;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.bus.BusCategory;
import com.scnu.service.bus.BusCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/buscategory", "/front/buscategory"})
public class BusCategoryController {

    @Resource
    private BusCategoryService busCategoryService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BusCategory busCategory) {
        busCategoryService.add(busCategory);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        busCategoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        busCategoryService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody BusCategory busCategory) {
        busCategoryService.updateById(busCategory);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BusCategory busCategory = busCategoryService.selectById(id);
        return Result.success(busCategory);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(@RequestParam(required = false) String serviceType, BusCategory busCategory) {
        // 如果传了 serviceType，就把它设置到 busCategory
        List<BusCategory> list = busCategoryService.selectAll(busCategory, serviceType);
        return Result.success(list);
    }

    @GetMapping("/selectTop")
    public Result selectTop(@RequestParam(required = false) String serviceType) {
        List<BusCategory> list = busCategoryService.selectTop(serviceType);
        return Result.success(list);
    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(
            @RequestParam(required = false) String serviceType,
            BusCategory busCategory,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BusCategory> page = busCategoryService.selectPage(busCategory, pageNum, pageSize, serviceType);
        return Result.success(page);
    }

}