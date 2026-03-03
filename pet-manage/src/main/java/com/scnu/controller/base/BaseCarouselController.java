package com.scnu.controller.base;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.base.BaseCarousel;
import com.scnu.service.base.BaseCarouselService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/basecarousel", "/front/basecarousel"})
public class BaseCarouselController {

    @Resource
    private BaseCarouselService baseCarouselService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BaseCarousel baseCarousel) {
        baseCarouselService.add(baseCarousel);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        baseCarouselService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        baseCarouselService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody BaseCarousel baseCarousel) {
        baseCarouselService.updateById(baseCarousel);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BaseCarousel baseCarousel = baseCarouselService.selectById(id);
        return Result.success(baseCarousel);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BaseCarousel baseCarousel) {
        List<BaseCarousel> list = baseCarouselService.selectAll(baseCarousel);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BaseCarousel baseCarousel,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BaseCarousel> page = baseCarouselService.selectPage(baseCarousel, pageNum, pageSize);
        return Result.success(page);
    }

}