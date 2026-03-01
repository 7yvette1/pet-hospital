package com.xzj.mapper.bus;

import com.xzj.entity.bus.BusCategory;
import java.util.List;


public interface BusCategoryMapper {

    /**
      * 新增
    */
    int insert(BusCategory busCategory);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(BusCategory busCategory);

    /**
      * 根据ID查询
    */
    BusCategory selectById(Integer id);

    /**
      * 查询所有
    */
    List<BusCategory> selectAll(BusCategory busCategory);

    List<BusCategory> selectTop(BusCategory busCategory);

}