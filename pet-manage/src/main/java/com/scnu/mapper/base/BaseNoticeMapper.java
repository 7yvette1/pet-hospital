package com.scnu.mapper.base;

import com.scnu.entity.base.BaseNotice;
import java.util.List;

public interface BaseNoticeMapper {

 /**
      * 新增
    */
    int insert(BaseNotice baseNotice);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(BaseNotice baseNotice);

    /**
      * 根据ID查询
    */
    BaseNotice selectById(Integer id);

    /**
      * 查询所有
    */
    List<BaseNotice> selectAll(BaseNotice baseNotice);

}