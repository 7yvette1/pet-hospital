package com.xzj.service.bus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.bus.BusCategory;
import com.xzj.entity.bus.RegisterCount;
import com.xzj.entity.bus.RegisterInfo;
import com.xzj.entity.system.SysUser;
import com.xzj.mapper.bus.BusCategoryMapper;
import com.xzj.mapper.bus.RegisterInfoMapper;
import com.xzj.mapper.system.SysUserMapper;
import com.xzj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RegisterInfoService {

    @Resource
    private RegisterInfoMapper registerInfoMapper;
    @Resource
    private BusCategoryMapper busCategoryMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 新增
     */
    public void add(RegisterInfo registerInfo) {
        registerInfo.setTime(DateUtil.now());
        registerInfoMapper.insert(registerInfo);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        registerInfoMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            registerInfoMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(RegisterInfo registerInfo) {
        registerInfoMapper.updateById(registerInfo);
    }

    public void updateStatus(RegisterInfo registerInfo) {
        SysUser currentUser = TokenUtils.getCurrentUser();
        Integer userId = currentUser.getUserId();
        registerInfo.setReceiveId(userId);
        registerInfo.setReceiveTime(DateUtil.now());
        registerInfoMapper.updateById(registerInfo);
    }

    /**
     * 根据ID查询
     */
    public RegisterInfo selectById(Integer id) {
        return registerInfoMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<RegisterInfo> selectAll(RegisterInfo registerInfo) {
        return registerInfoMapper.selectAll(registerInfo);
    }

    /**
     * 分页查询
     */
    public PageInfo<RegisterInfo> selectPage(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RegisterInfo> list = registerInfoMapper.selectAll(registerInfo);
        for (int i = 0; i < list.size(); i++) {
            RegisterInfo registerInfo1 = list.get(i);
            String clinicId = registerInfo1.getClinicId();
            if(!"".equals(clinicId) && clinicId != null){
                BusCategory busCategory = busCategoryMapper.selectById(Integer.valueOf(clinicId));
                String name = busCategory.getName();
                registerInfo1.setClinicName(name);
            }
            String docId = registerInfo1.getDocId();
            if(!"".equals(docId) && docId != null){
                SysUser user = sysUserMapper.selectById(Integer.valueOf(docId));
                String name = user.getNickName();
                registerInfo1.setDocName(name);
            }
        }
        return PageInfo.of(list);
    }


    /**
     * 分页查询
     */
    public PageInfo<RegisterInfo> selectPage1(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RegisterInfo> list = registerInfoMapper.selectAll1(registerInfo);
        return PageInfo.of(list);
    }


    /**
     * 分页查询
     */
    public PageInfo<RegisterInfo> selectPage2(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {
        SysUser currentUser = TokenUtils.getCurrentUser();
        String userName = currentUser.getUserName();
        if(!"admin".equals(userName)){
            Integer userId = currentUser.getUserId();
            registerInfo.setDocId(String.valueOf(userId));
        }

        PageHelper.startPage(pageNum, pageSize);
        List<RegisterInfo> list = registerInfoMapper.selectAll2(registerInfo);
        return PageInfo.of(list);
    }



    /**
     * 分页查询
     */
    public PageInfo<RegisterInfo> selectPage3(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {
        SysUser currentUser = TokenUtils.getCurrentUser();
        String userName = currentUser.getUserName();
        if(!"admin".equals(userName)){
            Integer userId = currentUser.getUserId();
            registerInfo.setReceiveId(userId);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<RegisterInfo> list = registerInfoMapper.selectAll(registerInfo);
        for (int i = 0; i < list.size(); i++) {
            RegisterInfo registerInfo1 = list.get(i);
            String clinicId = registerInfo1.getClinicId();
            if(!"".equals(clinicId) && clinicId != null){
                BusCategory busCategory = busCategoryMapper.selectById(Integer.valueOf(clinicId));
                String name = busCategory.getName();
                registerInfo1.setClinicName(name);
            }
            String docId = registerInfo1.getDocId();
            if(!"".equals(docId) && docId != null){
                SysUser user = sysUserMapper.selectById(Integer.valueOf(docId));
                String name = user.getNickName();
                registerInfo1.setDocName(name);
            }
        }
        return PageInfo.of(list);
    }

    public Map<String, Object> selectRegisterApply() {
        Map<String, Object> map = new HashMap<>();
        //订单量
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setTime(new SimpleDateFormat("yyyy").format(new Date()));
        List<RegisterInfo> list = registerInfoMapper.selectRegisterApply(registerInfo);
        ArrayList<Integer> applyList = this.countSetValue(list);
        map.put("y", applyList);
        return map;
    }


    private ArrayList<Integer> countSetValue(List<RegisterInfo> list) {
        ArrayList<Integer> applyList = CollUtil.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            RegisterInfo apply = list.get(i);
            String time = apply.getTime();
            String month = time.substring(5,7);
            if("01".equals(month)){
                applyList.set(0,applyList.get(0) + 1);
            }else if("02".equals(month)){
                applyList.set(1,applyList.get(1) + 1);
            }else if("03".equals(month)){
                applyList.set(2,applyList.get(2) + 1);
            }else if("04".equals(month)){
                applyList.set(3,applyList.get(3) + 1);
            }else if("05".equals(month)){
                applyList.set(4,applyList.get(4) + 1);
            }else if("06".equals(month)){
                applyList.set(5,applyList.get(5) + 1);
            }else if("07".equals(month)){
                applyList.set(6,applyList.get(6) + 1);
            }else if("08".equals(month)){
                applyList.set(7,applyList.get(7) + 1);
            }else if("09".equals(month)){
                applyList.set(8,applyList.get(8) + 1);
            }else if("10".equals(month)){
                applyList.set(9,applyList.get(9) + 1);
            }else if("11".equals(month)){
                applyList.set(10,applyList.get(10) + 1);
            }else if("12".equals(month)){
                applyList.set(11,applyList.get(11) + 1);
            }
        }
        return applyList;
    }

    public List<RegisterCount> selectRegisterStatus() {
        return registerInfoMapper.selectRegisterStatus();
    }
}