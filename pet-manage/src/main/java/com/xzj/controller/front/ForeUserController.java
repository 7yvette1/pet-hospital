package com.xzj.controller.front;

import com.github.pagehelper.PageInfo;
import com.xzj.common.Result;
import com.xzj.entity.front.FrontUser;
import com.xzj.service.front.FrontUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xuzhijun
 * @date 2024年08月15日 16:56
 */
@RestController
@RequestMapping("/fore")
public class ForeUserController {

    @Resource
    FrontUserService frontUserService;


    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(FrontUser frontUser,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<FrontUser> page = frontUserService.selectPage(frontUser, pageNum, pageSize);
        return Result.success(page);
    }
}
