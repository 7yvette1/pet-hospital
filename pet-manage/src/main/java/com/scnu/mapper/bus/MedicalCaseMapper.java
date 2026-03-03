package com.scnu.mapper.bus;

import com.scnu.entity.bus.MedicalCase;

import java.util.List;


public interface MedicalCaseMapper {

    /**
      * 新增
    */
    int insert(MedicalCase medicalCase);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(MedicalCase medicalCase);

    /**
      * 根据ID查询
    */
    MedicalCase selectById(Integer id);

    /**
      * 查询所有
    */
    List<MedicalCase> selectAll(MedicalCase medicalCase);
    List<MedicalCase> selectMyPage(MedicalCase medicalCase);

}