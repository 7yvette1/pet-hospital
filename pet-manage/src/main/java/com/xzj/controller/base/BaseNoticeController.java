package com.xzj.controller.base;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.base.BaseNotice;
import com.xzj.service.base.BaseNoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/basenotice", "/front/basenotice"})
public class BaseNoticeController {

    @Resource
    private BaseNoticeService baseNoticeService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BaseNotice baseNotice) {
        baseNoticeService.add(baseNotice);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        baseNoticeService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        baseNoticeService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody BaseNotice baseNotice) {
        baseNoticeService.updateById(baseNotice);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BaseNotice baseNotice = baseNoticeService.selectById(id);
        return Result.success(baseNotice);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BaseNotice baseNotice) {
        List<BaseNotice> list = baseNoticeService.selectAll(baseNotice);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BaseNotice baseNotice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BaseNotice> page = baseNoticeService.selectPage(baseNotice, pageNum, pageSize);
        return Result.success(page);
    }

}