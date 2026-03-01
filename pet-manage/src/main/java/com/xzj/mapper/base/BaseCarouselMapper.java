package com.xzj.mapper.base;

import com.xzj.entity.base.BaseCarousel;

import java.util.List;

public interface BaseCarouselMapper {

 /**
      * 新增
    */
    int insert(BaseCarousel baseCarousel);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(BaseCarousel baseCarousel);

    /**
      * 根据ID查询
    */
    BaseCarousel selectById(Integer id);

    /**
      * 查询所有
    */
    List<BaseCarousel> selectAll(BaseCarousel baseCarousel);

}