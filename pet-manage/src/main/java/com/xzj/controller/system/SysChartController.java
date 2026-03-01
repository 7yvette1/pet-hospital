package com.xzj.controller.system;

import com.xzj.common.Result;
import com.xzj.service.system.SysChartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xuzhijun
 * @date 2024年07月26日 10:35
 */
@RestController
@RequestMapping("/syschart")
public class SysChartController {

    @Resource
    SysChartService sysChartService;

    /**
     * 查询折线图数据
     */
    @GetMapping("/selectline")
    public Result selectline() {
        Map<String,Object> map = sysChartService.selectline();
        return Result.success(map);
    }


    /**
     * 查询柱状图数据
     */
    @GetMapping("/selectbar")
    public Result selectbar() {
        Map<String,Object> map = sysChartService.selectbar();
        return Result.success(map);
    }

    /**
     * 查询饼状图数据
     */
    @GetMapping("/selectpie")
    public Result selectpie() {
        List<String> list = sysChartService.selectpie();
        return Result.success(list);
    }

    /**
     * 查询雷达图数据
     */
    @GetMapping("/selectradar")
    public Result selectradar() {
        Map<String, Object> map = sysChartService.selectradar();
        return Result.success(map);
    }

}
