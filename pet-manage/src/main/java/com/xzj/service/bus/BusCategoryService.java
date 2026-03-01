package com.xzj.service.bus;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.bus.BusCategory;
import com.xzj.mapper.bus.BusCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BusCategoryService {

    @Resource
    private BusCategoryMapper busCategoryMapper;

    /**
     * 新增
     */
    public void add(BusCategory busCategory) {
        busCategoryMapper.insert(busCategory);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        busCategoryMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            busCategoryMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(BusCategory busCategory) {
        busCategoryMapper.updateById(busCategory);
    }

    /**
     * 根据ID查询
     */
    public BusCategory selectById(Integer id) {
        return busCategoryMapper.selectById(id);
    }

    /**
     * 查询所有，按服务类型筛选
     */
    public List<BusCategory> selectAll(BusCategory busCategory, String serviceType) {
        if (serviceType != null && !serviceType.isEmpty()) {
            busCategory.setServiceType(serviceType);
        }
        return busCategoryMapper.selectAll(busCategory);
    }

    public List<BusCategory> selectTop(String serviceType) {
        BusCategory busCategory = new BusCategory();
        if (serviceType != null && !serviceType.isEmpty()) {
            busCategory.setServiceType(serviceType);
        }
        return busCategoryMapper.selectTop(busCategory);
    }

    /**
     * 分页查询
     */
    public PageInfo<BusCategory> selectPage(BusCategory busCategory, Integer pageNum, Integer pageSize, String serviceType) {
        if (serviceType != null && !serviceType.isEmpty()) {
            busCategory.setServiceType(serviceType);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BusCategory> list = busCategoryMapper.selectAll(busCategory);
        return PageInfo.of(list);
    }

}