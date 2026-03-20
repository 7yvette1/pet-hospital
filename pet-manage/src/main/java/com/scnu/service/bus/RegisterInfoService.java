package com.scnu.service.bus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.entity.bus.BusCategory;
import com.scnu.entity.bus.RegisterCount;
import com.scnu.entity.bus.RegisterInfo;
import com.scnu.entity.system.SysUser;
import com.scnu.mapper.bus.BusCategoryMapper;
import com.scnu.mapper.bus.RegisterInfoMapper;
import com.scnu.mapper.system.SysUserMapper;
import com.scnu.utils.TokenUtils;
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
     * 创建订单
     */
    public void add(RegisterInfo registerInfo) {

        //生成订单号
        String orderNo = "ORD" + System.currentTimeMillis();
        registerInfo.setOrderNo(orderNo);

        //创建时间
        registerInfo.setCreateTime(DateUtil.now());

        //预约时间
        registerInfo.setTime(DateUtil.now());

        //默认状态：待支付
        registerInfo.setStatus("0");

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

    /**
     * 修改状态（原有逻辑）
     */
    public void updateStatus(RegisterInfo registerInfo) {

        SysUser currentUser = TokenUtils.getCurrentUser();

        registerInfo.setReceiveId(currentUser.getUserId());

        registerInfo.setReceiveTime(DateUtil.now());

        registerInfoMapper.updateById(registerInfo);
    }

    /**
     * 用户支付订单
     */
    public void pay(Integer id){
        RegisterInfo info = registerInfoMapper.selectById(id);
        info.setStatus("1"); //已支付
        info.setPayTime(DateUtil.now());
        registerInfoMapper.updateById(info);
    }

    /**
     * 后台接单
     */
    public void accept(Integer id){
        RegisterInfo info = registerInfoMapper.selectById(id);
        SysUser currentUser = TokenUtils.getCurrentUser();
        info.setReceiveId(currentUser.getUserId());
        info.setReceiveTime(DateUtil.now());
        info.setStatus("2"); //已接单
        registerInfoMapper.updateById(info);
    }

    /**
     * 开始服务
     */
    public void start(Integer id){
        RegisterInfo info = registerInfoMapper.selectById(id);
        info.setStatus("3"); //服务中
        info.setStartTime(DateUtil.now());
        registerInfoMapper.updateById(info);
    }

    /**
     * 完成服务
     */
    public void finish(Integer id){
        RegisterInfo info = registerInfoMapper.selectById(id);
        info.setStatus("4"); //已完成
        info.setFinishTime(DateUtil.now());
        registerInfoMapper.updateById(info);
    }

    /**
     * 取消订单
     */
    public void cancel(Integer id){
        RegisterInfo info = registerInfoMapper.selectById(id);
        info.setStatus("5"); //已取消
        registerInfoMapper.updateById(info);
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
     * 后台分页查询
     */
    public PageInfo<RegisterInfo> selectPage(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<RegisterInfo> list = registerInfoMapper.selectAll(registerInfo);

        for (RegisterInfo info : list) {

            Integer clinicId = info.getClinicId();

            if (clinicId != null) {

                BusCategory busCategory = busCategoryMapper.selectById(clinicId);

                if(busCategory != null){
                    info.setClinicName(busCategory.getName());
                }
            }

            Integer docId = info.getDocId();

            if (docId != null) {

                SysUser user = sysUserMapper.selectById(docId);

                if(user != null){
                    info.setDocName(user.getNickName());
                }
            }
        }

        return PageInfo.of(list);
    }

    /**
     * 前台用户订单
     */
    public PageInfo<RegisterInfo> selectPage1(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<RegisterInfo> list = registerInfoMapper.selectAll1(registerInfo);

        return PageInfo.of(list);
    }

    /**
     * 服务人员订单
     */
    public PageInfo<RegisterInfo> selectPage2(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {

        SysUser currentUser = TokenUtils.getCurrentUser();

        if(!"admin".equals(currentUser.getUserName())){
            registerInfo.setDocId(currentUser.getUserId());
        }

        PageHelper.startPage(pageNum, pageSize);

        List<RegisterInfo> list = registerInfoMapper.selectAll2(registerInfo);

        return PageInfo.of(list);
    }

    /**
     * 接诊订单
     */
    public PageInfo<RegisterInfo> selectPage3(RegisterInfo registerInfo, Integer pageNum, Integer pageSize) {

        SysUser currentUser = TokenUtils.getCurrentUser();

        if(!"admin".equals(currentUser.getUserName())){
            registerInfo.setReceiveId(currentUser.getUserId());
        }

        PageHelper.startPage(pageNum, pageSize);

        List<RegisterInfo> list = registerInfoMapper.selectAll(registerInfo);

        for (RegisterInfo info : list) {

            Integer clinicId = info.getClinicId();

            if (clinicId != null) {

                BusCategory busCategory = busCategoryMapper.selectById(clinicId);

                if(busCategory != null){
                    info.setClinicName(busCategory.getName());
                }
            }

            Integer docId = info.getDocId();

            if (docId != null) {

                SysUser user = sysUserMapper.selectById(docId);

                if(user != null){
                    info.setDocName(user.getNickName());
                }
            }
        }

        return PageInfo.of(list);
    }

    /**
     * 订单统计
     */
    public Map<String, Object> selectRegisterApply() {

        Map<String, Object> map = new HashMap<>();

        RegisterInfo registerInfo = new RegisterInfo();

        registerInfo.setTime(new SimpleDateFormat("yyyy").format(new Date()));

        List<RegisterInfo> list = registerInfoMapper.selectRegisterApply(registerInfo);

        ArrayList<Integer> applyList = countSetValue(list);

        map.put("y", applyList);

        return map;
    }

    private ArrayList<Integer> countSetValue(List<RegisterInfo> list) {

        ArrayList<Integer> applyList = CollUtil.newArrayList(0,0,0,0,0,0,0,0,0,0,0,0);

        for (RegisterInfo apply : list) {

            String time = apply.getTime();

            String month = time.substring(5,7);

            int index = Integer.parseInt(month) - 1;

            applyList.set(index, applyList.get(index) + 1);
        }

        return applyList;
    }

    /**
     * 订单状态统计
     */
    public List<RegisterCount> selectRegisterStatus() {
        return registerInfoMapper.selectRegisterStatus();
    }
}