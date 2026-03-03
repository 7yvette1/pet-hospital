package com.scnu.controller.bus;

import com.github.pagehelper.PageInfo;
import com.scnu.common.Result;
import com.scnu.entity.bus.MedicalCase;
import com.scnu.entity.bus.RegisterInfo;
import com.scnu.entity.system.SysUser;
import com.scnu.service.bus.MedicalCaseService;
import com.scnu.service.bus.RegisterInfoService;
import com.scnu.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = {"/medicalCase", "/front/medicalCase"})
public class MedicalCaseController {

    @Resource
    private MedicalCaseService medicalCaseService;
    @Resource
    private RegisterInfoService registerInfoService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody MedicalCase medicalCase) {
        String registerId = medicalCase.getRegisterId();
        RegisterInfo registerInfo = registerInfoService.selectById(Integer.valueOf(registerId));
        registerInfo.setStatus("已完成");
        registerInfoService.updateById(registerInfo);

        SysUser currentUser = TokenUtils.getCurrentUser();
        Integer userId = currentUser.getUserId();
        medicalCase.setDoctorId(userId.toString());
        medicalCaseService.add(medicalCase);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        medicalCaseService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        medicalCaseService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody MedicalCase medicalCase) {
        medicalCaseService.updateById(medicalCase);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        MedicalCase medicalCase = medicalCaseService.selectById(id);
        return Result.success(medicalCase);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(MedicalCase medicalCase) {
        List<MedicalCase> list = medicalCaseService.selectAll(medicalCase);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(MedicalCase medicalCase,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MedicalCase> page = medicalCaseService.selectPage(medicalCase, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * 分页查询（前端）
     */
    @GetMapping("/selectMyPage")
    public Result selectMyPage(MedicalCase medicalCase,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MedicalCase> page = medicalCaseService.selectMyPage(medicalCase, pageNum, pageSize);
        return Result.success(page);
    }

}