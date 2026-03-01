package com.xzj.controller.base;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.base.ChatMessage;
import com.xzj.service.base.ChatMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/chatMessage", "/front/chatMessage"})
public class ChatMessageController {

    @Resource
    private ChatMessageService chatMessageService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody ChatMessage chatMessage) {
        chatMessageService.add(chatMessage);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        chatMessageService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        chatMessageService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody ChatMessage chatMessage) {
        chatMessageService.updateById(chatMessage);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        ChatMessage chatMessage = chatMessageService.selectById(id);
        return Result.success(chatMessage);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(ChatMessage chatMessage) {
        List<ChatMessage> list = chatMessageService.selectAll(chatMessage);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ChatMessage chatMessage,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<ChatMessage> page = chatMessageService.selectPage(chatMessage, pageNum, pageSize);
        return Result.success(page);
    }


    @GetMapping("/getMessageList")
    public Result getMessageList(ChatMessage chatMessage) {
        List<ChatMessage> list = chatMessageService.getMessageList(chatMessage);
        return Result.success(list);
    }

}