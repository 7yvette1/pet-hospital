package com.xzj.controller.bus;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.bus.BusComment;
import com.xzj.service.bus.BusCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/buscomment", "/front/buscomment"})
public class BusCommentController {

    @Resource
    private BusCommentService busCommentService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BusComment comment) {
        busCommentService.add(comment);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        busCommentService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deepDelete/{id}")
    public Result deepDelete(@PathVariable Integer id) {
        busCommentService.deepDelete(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        busCommentService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody BusComment comment) {
        busCommentService.updateById(comment);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BusComment comment = busCommentService.selectById(id);
        return Result.success(comment);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BusComment comment ) {
        List<BusComment> list = busCommentService.selectAll(comment);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BusComment comment,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BusComment> page = busCommentService.selectPage(comment, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询前台评论列表
     */
    @GetMapping("/selectTree/{fid}/{module}")
    public Result selectTree(@PathVariable Integer fid, @PathVariable String module) {
        List<BusComment> list = busCommentService.selectTree(fid, module);
        return Result.success(list);
    }

    /**
     * 查询前台评论列表(分页)
     */
    @GetMapping("/selectTreeByPage/{fid}/{module}")
    public Result selectTreeByPage( @PathVariable Integer fid,
                                    @PathVariable String module,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BusComment> page = busCommentService.selectTreeByPage(fid, module,pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询前台评论数量
     */
    @GetMapping("/selectCount/{fid}/{module}")
    public Result selectCount(@PathVariable Integer fid, @PathVariable String module) {
        Integer count = busCommentService.selectCount(fid, module);
        return Result.success(count);
    }

}