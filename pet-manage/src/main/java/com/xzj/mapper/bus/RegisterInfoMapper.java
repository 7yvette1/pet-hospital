package com.xzj.mapper.bus;

import com.xzj.entity.bus.RegisterCount;
import com.xzj.entity.bus.RegisterInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RegisterInfoMapper {

    /**
      * 新增
    */
    int insert(RegisterInfo registerInfo);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(RegisterInfo registerInfo);

    /**
      * 根据ID查询
    */
    RegisterInfo selectById(Integer id);

    /**
      * 查询所有
    */
    List<RegisterInfo> selectAll(RegisterInfo registerInfo);
    List<RegisterInfo> selectAll1(RegisterInfo registerInfo);
    List<RegisterInfo> selectAll2(RegisterInfo registerInfo);


    List<RegisterInfo> selectRegisterApply(RegisterInfo registerInfo);

    @Select("SELECT a.status as name,COUNT(1) AS value FROM register_info a GROUP BY a.status")
    List<RegisterCount> selectRegisterStatus();
}