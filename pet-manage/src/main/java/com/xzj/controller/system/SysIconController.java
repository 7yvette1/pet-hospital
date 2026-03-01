package com.xzj.controller.system;

import com.xzj.common.Result;
import com.xzj.entity.system.SysIcon;
import com.xzj.service.system.SysIconService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月28日 16:58
 */
@RestController
@RequestMapping("/sysicon")
public class SysIconController implements Serializable {

    @Resource
    SysIconService sysIconService;


    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SysIcon sysIcon) {
        List<SysIcon> list = sysIconService.selectAll(sysIcon);
        return Result.success(list);
    }

}
