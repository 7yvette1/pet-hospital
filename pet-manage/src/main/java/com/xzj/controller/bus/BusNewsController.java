package com.xzj.controller.bus;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.bus.BusNews;
import com.xzj.service.bus.BusNewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/busnews", "/front/busnews"})
public class BusNewsController {

    @Resource
    private BusNewsService busNewsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BusNews busNews) {
        busNewsService.add(busNews);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        busNewsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        busNewsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody BusNews busNews) {
        busNewsService.updateById(busNews);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BusNews busNews = busNewsService.selectById(id);
        return Result.success(busNews);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BusNews busNews) {
        List<BusNews> list = busNewsService.selectAll(busNews);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BusNews busNews,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BusNews> page = busNewsService.selectPage(busNews, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * 最新资讯
     */
    @GetMapping("/selectTopNews")
    public Result selectTopNews() {
        List<BusNews> list = busNewsService.selectTopNews();
        return Result.success(list);
    }

}