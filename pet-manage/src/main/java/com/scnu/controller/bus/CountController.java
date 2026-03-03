package com.scnu.controller.bus;

import com.scnu.common.Result;
import com.scnu.entity.bus.RegisterCount;
import com.scnu.service.bus.RegisterInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xuzhijun
 * @date 2025年06月24日 17:09
 */
@RestController
@RequestMapping(value = {"/count", "/front/count"})
public class CountController {

    @Resource
    private RegisterInfoService registerInfoService;


    @GetMapping("/selectRegisterApply")
    public Result selectRegisterApply() {
        Map<String,Object> map = registerInfoService.selectRegisterApply();
        return Result.success(map);
    }


    @GetMapping("/selectRegisterStatus")
    public Result selectRegisterStatus() {
        List<RegisterCount> list = registerInfoService.selectRegisterStatus();
        return Result.success(list);
    }



}
