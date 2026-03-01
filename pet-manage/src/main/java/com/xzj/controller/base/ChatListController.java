package com.xzj.controller.base;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.base.ChatList;
import com.xzj.service.base.ChatListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/chatList", "/front/chatList"})
public class ChatListController {

    @Resource
    private ChatListService chatListService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody ChatList chatList) {
        chatListService.add(chatList);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        chatListService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        chatListService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody ChatList chatList) {
        chatListService.updateById(chatList);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        ChatList chatList = chatListService.selectById(id);
        return Result.success(chatList);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(ChatList chatList) {
        List<ChatList> list = chatListService.selectAll(chatList);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ChatList chatList,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ChatList> page = chatListService.selectPage(chatList, pageNum, pageSize);
        return Result.success(page);
    }

}