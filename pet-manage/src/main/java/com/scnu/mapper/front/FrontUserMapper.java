package com.scnu.mapper.front;

import com.scnu.entity.front.FrontUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xuzhijun
 * @date 2024年05月21日 15:22
 */
public interface FrontUserMapper {
    /**
     * 新增
     */
    void add(FrontUser frontUser);

    /**
     * 根据ID修改
     */
    void updateById(FrontUser frontUser);

    /**
     * 根据ID删除
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询
     */
    FrontUser selectById(Integer id);

    /**
     * 查询所有
     */
    List<FrontUser> selectAll(FrontUser frontUser);

    /**
     * 根据用户名和密码查询用户
     */
    FrontUser selectByUserAndPassword(FrontUser frontUser);

    /**
     * 修改用户密码
     */
    void updatePassword(FrontUser frontUser);

    /**
     * 根据用户名查询用户
     */
    @Select("select * from front_user where user_name = #{userName}")
    FrontUser selectByUsername(String userName);

    /**
     *
     * 根据手机号查询用户
     */
    @Select("select * from front_user where phone = #{phone}")
    FrontUser selectByPhone(String phone);

}
