package com.scnu.mapper.bus;

import com.scnu.entity.bus.BusComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BusCommentMapper {

    /**
      * 新增
    */
    int insert(BusComment busComment);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(BusComment busComment);

    /**
      * 根据ID查询
    */
    BusComment selectById(Integer id);

    /**
      * 查询所有
    */
    List<BusComment> selectAll(BusComment comment);

    List<BusComment> selectOneAll(BusComment comment);

    List<BusComment> selectTree(@Param("fid") Integer fid, @Param("module") String module);

    @Select("select count(*) from bus_comment where fid = #{fid} and module = #{module}")
    Integer selectCount(@Param("fid") Integer fid, @Param("module") String module);

    @Select("select * from bus_comment where pid = #{pid}")
    List<BusComment> selectByPid(Integer pid);
}