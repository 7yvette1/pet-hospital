package com.scnu.controller.bus;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.bus.PetHealthRecord;
import com.scnu.service.bus.PetHealthRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/front/petHealthRecord")
public class PetHealthRecordController {

    @Resource
    private PetHealthRecordService petHealthRecordService;

    /**
     * 新增健康档案
     */
    @PostMapping("/add")
    public Result add(@RequestBody PetHealthRecord record) {
        petHealthRecordService.add(record);
        return Result.success();
    }


    /**
     * 修改健康档案
     */
    @PutMapping("/update")
    public Result update(@RequestBody PetHealthRecord record) {
        petHealthRecordService.updateById(record);
        return Result.success();
    }


    /**
     * 删除健康档案
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        petHealthRecordService.deleteById(id);
        return Result.success();
    }


    /**
     * 分页查询（后台用）
     */
    @GetMapping("/selectPage")
    public Result selectPage(
            PetHealthRecord record,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        PageInfo<PetHealthRecord> pageInfo =
                petHealthRecordService.selectPage(record, pageNum, pageSize);
        return Result.success(pageInfo);
    }


    /**
     * 查询当前用户健康档案
     */
    @GetMapping("/selectMyPage")
    public Result selectMyPage(
            PetHealthRecord record,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        PageInfo<PetHealthRecord> pageInfo =
                petHealthRecordService.selectMyPage(record, pageNum, pageSize);

        return Result.success(pageInfo);
    }

}