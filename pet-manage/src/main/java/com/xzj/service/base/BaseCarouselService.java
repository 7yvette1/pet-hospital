package com.xzj.service.base;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.base.BaseCarousel;
import com.xzj.entity.system.SysUser;
import com.xzj.mapper.base.BaseCarouselMapper;
import com.xzj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseCarouselService {

    @Resource
    private BaseCarouselMapper baseCarouselMapper;

    /**
     * 新增
     */
    public void add(BaseCarousel baseCarousel) {
        baseCarousel.setTime(DateUtil.today());
        SysUser currentUser = TokenUtils.getCurrentUser();
        baseCarousel.setUser(currentUser.getUserName());
        baseCarouselMapper.insert(baseCarousel);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        baseCarouselMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            baseCarouselMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(BaseCarousel baseCarousel) {
        baseCarouselMapper.updateById(baseCarousel);
    }

    /**
     * 根据ID查询
     */
    public BaseCarousel selectById(Integer id) {
        return baseCarouselMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<BaseCarousel> selectAll(BaseCarousel baseCarousel) {
        return baseCarouselMapper.selectAll(baseCarousel);
    }

    /**
     * 分页查询
     */
    public PageInfo<BaseCarousel> selectPage(BaseCarousel baseCarousel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BaseCarousel> list = baseCarouselMapper.selectAll(baseCarousel);
        return PageInfo.of(list);
    }

}