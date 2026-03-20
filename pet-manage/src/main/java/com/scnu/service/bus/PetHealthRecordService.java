package com.scnu.service.bus;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.entity.bus.PetHealthRecord;
import com.scnu.mapper.bus.PetHealthRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PetHealthRecordService {

    @Resource
    private PetHealthRecordMapper petHealthRecordMapper;

    /**
     * 新增
     */
    public void add(PetHealthRecord record) {
        petHealthRecordMapper.insert(record);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        petHealthRecordMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            petHealthRecordMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(PetHealthRecord record) {
        petHealthRecordMapper.updateById(record);
    }

    /**
     * 根据ID查询
     */
    public PetHealthRecord selectById(Integer id) {
        return petHealthRecordMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<PetHealthRecord> selectAll(PetHealthRecord record) {
        return petHealthRecordMapper.selectAll(record);
    }

    /**
     * 分页查询
     */
    public PageInfo<PetHealthRecord> selectPage(PetHealthRecord record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PetHealthRecord> list = petHealthRecordMapper.selectAll(record);
        return PageInfo.of(list);
    }

    /**
     * 我的健康档案
     */
    public PageInfo<PetHealthRecord> selectMyPage(PetHealthRecord record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PetHealthRecord> list = petHealthRecordMapper.selectMyPage(record);
        return PageInfo.of(list);
    }
}