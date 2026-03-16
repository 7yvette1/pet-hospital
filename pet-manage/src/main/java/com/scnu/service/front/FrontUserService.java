package com.scnu.service.front;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.common.enums.BusinessMsgEnum;
import com.scnu.common.exception.BusinessErrorException;
import com.scnu.entity.front.FrontUser;
import com.scnu.mapper.front.FrontUserMapper;
import com.scnu.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class FrontUserService {

    @Resource
    private FrontUserMapper frontUserMapper;

    /**
     * 新增
     */
    public void add(FrontUser frontUser) {
        FrontUser user = frontUserMapper.selectByUsername(frontUser.getUserName());
        if (!Objects.isNull(user)) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_REPEAT_ERROR);
        }

        if("".equals(frontUser.getPassword())){
            frontUser.setPassword("123456");
        }
        frontUser.setCreateBy(frontUser.getUserName());
        frontUser.setCreateTime(DateUtil.now());
        frontUserMapper.add(frontUser);
    }

    /**
     * 根据ID修改
     */
    public void updateById(FrontUser frontUser) {
        frontUserMapper.updateById(frontUser);
    }

    /**
     * 根据ID删除
     */
    public void deleteById(Integer id) {
        frontUserMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            frontUserMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public FrontUser selectById(Integer id) {
        return frontUserMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<FrontUser> selectAll(FrontUser frontUser) {
        return frontUserMapper.selectAll(frontUser);
    }

    /**
     * 分页查询
     */
    public PageInfo<FrontUser> selectPage(FrontUser frontUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FrontUser> list = frontUserMapper.selectAll(frontUser);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public FrontUser login(FrontUser frontUser) {
        FrontUser user = frontUserMapper.selectByUserAndPassword(frontUser);
        if (Objects.isNull(user)) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_ACCOUNT_ERROR);
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("userId", user.getUserId().toString());
        payload.put("userName", user.getUserName());
        String jwt = JWTUtils.getToken(payload);

        user.setToken(jwt);
        return user;
    }

    public FrontUser loginByCode(FrontUser frontUser) {

        // 根据手机号查询用户
        FrontUser user = frontUserMapper.selectByPhone(frontUser.getPhone());

        if (Objects.isNull(user)) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_ACCOUNT_ERROR);
        }

        // 生成JWT
        Map<String, String> payload = new HashMap<>();
        payload.put("userId", user.getUserId().toString());
        payload.put("userName", user.getUserName());

        String jwt = JWTUtils.getToken(payload);

        user.setToken(jwt);

        return user;
    }

    /**
     * 注册
     */
    public void register(FrontUser frontUser) {
        add(frontUser);
    }

    /**
     * 修改用户密码
     */
    public void updatePassword(FrontUser frontUser) {
        frontUserMapper.updatePassword(frontUser);
    }
}
