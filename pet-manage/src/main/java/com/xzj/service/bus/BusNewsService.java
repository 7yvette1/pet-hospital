package com.xzj.service.bus;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.bus.BusNews;
import com.xzj.mapper.bus.BusNewsMapper;
import com.xzj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BusNewsService {

    @Resource
    private BusNewsMapper busNewsMapper;

    /**
     * 新增
     */
    public void add(BusNews busNews) {
        busNews.setTime(DateUtil.now());
        busNews.setUser(TokenUtils.getCurrentUser().getUserName());
        busNewsMapper.insert(busNews);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        busNewsMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            busNewsMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(BusNews busNews) {
        busNewsMapper.updateById(busNews);
    }

    /**
     * 根据ID查询
     */
    public BusNews selectById(Integer id) {
        return busNewsMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<BusNews> selectAll(BusNews busNews) {
        return busNewsMapper.selectAll(busNews);
    }

    /**
     * 分页查询
     */
    public PageInfo<BusNews> selectPage(BusNews busNews, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BusNews> list = busNewsMapper.selectAll(busNews);
        return PageInfo.of(list);
    }

    /**
     * 最新资讯
     */
    public List<BusNews> selectTopNews() {
        return busNewsMapper.selectTopNews();
    }
}