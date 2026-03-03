package com.scnu.mapper.bus;

import com.scnu.entity.bus.BusNews;

import java.util.List;

public interface BusNewsMapper {

    /**
      * 新增
    */
    int insert(BusNews busNews);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(BusNews busNews);

    /**
      * 根据ID查询
    */
    BusNews selectById(Integer id);

    /**
      * 查询所有
    */
    List<BusNews> selectAll(BusNews busNews);

    /**
     * 最新资讯
     */
    List<BusNews> selectTopNews();
}