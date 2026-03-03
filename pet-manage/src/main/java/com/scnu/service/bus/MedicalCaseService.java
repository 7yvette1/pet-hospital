package com.scnu.service.bus;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.entity.bus.MedicalCase;
import com.scnu.entity.system.SysUser;
import com.scnu.mapper.bus.MedicalCaseMapper;
import com.scnu.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MedicalCaseService {

    @Resource
    private MedicalCaseMapper medicalCaseMapper;

    /**
     * 新增
     */
    public void add(MedicalCase medicalCase) {
        medicalCaseMapper.insert(medicalCase);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        medicalCaseMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            medicalCaseMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(MedicalCase medicalCase) {
        medicalCaseMapper.updateById(medicalCase);
    }

    /**
     * 根据ID查询
     */
    public MedicalCase selectById(Integer id) {
        return medicalCaseMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<MedicalCase> selectAll(MedicalCase medicalCase) {
        return medicalCaseMapper.selectAll(medicalCase);
    }

    /**
     * 分页查询
     */
    public PageInfo<MedicalCase> selectPage(MedicalCase medicalCase, Integer pageNum, Integer pageSize) {
        SysUser currentUser = TokenUtils.getCurrentUser();
        Integer userId = currentUser.getUserId();
        String userName = currentUser.getUserName();
        if(!"admin".equals(userName)){
            medicalCase.setDoctorId(userId.toString());
        }


        PageHelper.startPage(pageNum, pageSize);
        List<MedicalCase> list = medicalCaseMapper.selectAll(medicalCase);
        return PageInfo.of(list);
    }


    public PageInfo<MedicalCase> selectMyPage(MedicalCase medicalCase, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MedicalCase> list = medicalCaseMapper.selectMyPage(medicalCase);
        return PageInfo.of(list);
    }

}