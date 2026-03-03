package com.scnu.service.base;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.entity.base.BaseNotice;
import com.scnu.entity.system.SysUser;
import com.scnu.mapper.base.BaseNoticeMapper;
import com.scnu.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseNoticeService {

    @Resource
    private BaseNoticeMapper baseNoticeMapper;

    /**
     * 新增
     */
    public void add(BaseNotice baseNotice) {
        baseNotice.setTime(DateUtil.today());
        SysUser currentUser = TokenUtils.getCurrentUser();
        baseNotice.setUser(currentUser.getUserName());
        baseNoticeMapper.insert(baseNotice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        baseNoticeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            baseNoticeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(BaseNotice baseNotice) {
        baseNoticeMapper.updateById(baseNotice);
    }

    /**
     * 根据ID查询
     */
    public BaseNotice selectById(Integer id) {
        return baseNoticeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<BaseNotice> selectAll(BaseNotice baseNotice) {
        return baseNoticeMapper.selectAll(baseNotice);
    }

    /**
     * 分页查询
     */
    public PageInfo<BaseNotice> selectPage(BaseNotice baseNotice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BaseNotice> list = baseNoticeMapper.selectAll(baseNotice);
        return PageInfo.of(list);
    }

}