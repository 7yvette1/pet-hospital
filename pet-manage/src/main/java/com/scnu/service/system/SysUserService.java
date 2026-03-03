package com.scnu.service.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.common.enums.BusinessMsgEnum;
import com.scnu.common.exception.BusinessErrorException;
import com.scnu.entity.system.SysLogin;
import com.scnu.entity.system.SysRole;
import com.scnu.entity.system.SysUser;
import com.scnu.entity.system.SysUserRole;
import com.scnu.mapper.system.SysLoginMapper;
import com.scnu.mapper.system.SysRoleMapper;
import com.scnu.mapper.system.SysUserMapper;
import com.scnu.mapper.system.SysUserRoleMapper;
import com.scnu.utils.JWTUtils;
import com.scnu.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;
    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    SysLoginMapper sysLoginMapper;
    @Resource
    private Cache<String, Object> caffeineCache;

    /**
     * 新增
     */
    public void add(SysUser sysUser) {
        if("".equals(sysUser.getPassword())){
            sysUser.setPassword("123456");
        }
        sysUser.setCreateBy(TokenUtils.getCurrentUser().getUserId().toString());
        sysUser.setCreateTime(DateUtil.now());
        sysUserMapper.add(sysUser);
    }

    /**
     * 根据ID修改
     */
    public void updateById(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 根据ID删除
     */
    public void deleteById(Integer id) {
        //先删除用户配置的角色
        sysUserRoleMapper.deleteById(id);
        sysUserMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sysUserMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public SysUser selectById(Integer id) {
        return sysUserMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<SysUser> selectAll(SysUser sysUser) {
        return sysUserMapper.selectAll(sysUser);
    }

    public List<SysUser> selectDoc(SysUser sysUser) {
        return sysUserMapper.selectDoc(sysUser);
    }

    /**
     * 分页查询
     */
    public PageInfo<SysUser> selectPage(SysUser sysUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.selectAll(sysUser);
        for (SysUser user : list) {
            List<SysRole> roleList = sysRoleMapper.selectUserRole(user.getUserId());
            if (!CollectionUtils.isEmpty(roleList)) {
                user.setRoleIdList(roleList.stream().map(SysRole::getRoleId).collect(Collectors.toList()));
                user.setRoleNameList(roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
            }
        }
        return PageInfo.of(list);
    }

    public PageInfo<SysUser> selectDocPage(SysUser sysUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.selectDoc(sysUser);
        return PageInfo.of(list);
    }

    /**
     * 分配角色
     */
    public void grantRole(SysUser sysUser) {
        if (Objects.equals(sysUser.getUserName(), "admin")) {
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }

        //先删除用户对应下的角色（原来的）
        Integer userId = sysUser.getUserId();
        sysUserRoleMapper.deleteById(userId);

        //再新增用户对应下的角色（新的）
        List<Integer> roleIdList = sysUser.getRoleIdList();
        for (Integer roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRoleMapper.add(sysUserRole);
        }
    }


    /**
     * 登录
     */
    public Map<String, String> login(SysUser sysUser) {
        String flagKey = sysUser.getFlagKey();
        String code = flagKey != null ? (String) caffeineCache.asMap().get(flagKey) : null;
        if (code == null) {
            throw new BusinessErrorException(BusinessMsgEnum.VERIFICATION_CODE_EXPIRED);
        }
        if (!Objects.equals(sysUser.getVerificationCode(), code)) {
            throw new BusinessErrorException(BusinessMsgEnum.VERIFICATION_CODE_ERROR);
        }
        SysUser user = sysUserMapper.selectByUserAndPassword(sysUser);
        if (Objects.isNull(user)) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_ACCOUNT_ERROR);
        }

        //判断用户是否停用
        if (Objects.equals(user.getStatus(), "1")) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_STATUS_ERROR);
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("userId", user.getUserId().toString());
        payload.put("userName", user.getUserName());
        String jwt = JWTUtils.getToken(payload);

        try {
            //在登录表中插入数据
            SysLogin sysLogin = new SysLogin();
            sysLogin.setUserName(user.getUserName());
            sysLogin.setLoginTime(DateUtil.now());
            sysLogin.setLoginAddr(InetAddress.getLocalHost().getHostAddress());
            sysLoginMapper.add(sysLogin);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Map<String, String> data = new HashMap<>();
        data.put("token", jwt);
        return data;
    }


    /**
     * 返回用户信息
     */
    public SysUser getUserInfo(String token) {
        String userId = JWTUtils.parseForValue(token, "userId", String.class);
        SysUser sysUser = sysUserMapper.selectById(Integer.valueOf(userId));
        List<SysRole> roleList = sysRoleMapper.selectUserRole(Integer.valueOf(userId));
        sysUser.setRoleNameList(roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
        return sysUser;
    }


    /**
     *
     * @author xuzhijun
     * @date 2024/8/4 18:34
     */
    public Map<String, Object> getUserCount() {
        Map<String, Object> map = new HashMap<>();
        //查询系统所有用户
        SysUser user = new SysUser();
        user.setCreateTime(new SimpleDateFormat("yyyy").format(new Date()));
        List<SysUser> sysUsers = sysUserMapper.selectAll(user);

        //男
        List<SysUser> sysUserNan = sysUsers.stream().filter(sysUser -> "0".equals(sysUser.getSex())).collect(Collectors.toList());
        ArrayList<Integer> nanList = this.listSetValue(sysUserNan);

        //女
        List<SysUser> sysUserNv = sysUsers.stream().filter(sysUser -> "1".equals(sysUser.getSex())).collect(Collectors.toList());
        ArrayList<Integer> nvList = this.listSetValue(sysUserNv);

        //登录量
        SysLogin sysLogin = new SysLogin();
        sysLogin.setLoginTime(new SimpleDateFormat("yyyy").format(new Date()));
        List<SysLogin> sysLogins = sysLoginMapper.selectAll(sysLogin);
        ArrayList<Integer> loginList = this.loginSetValue(sysLogins);

        map.put("nan", nanList);
        map.put("nv", nvList);
        map.put("login", loginList);
        return map;
    }


    private ArrayList<Integer> listSetValue(List<SysUser> list) {
        ArrayList<Integer> nanList = CollUtil.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            SysUser sysUser = list.get(i);
            String createTime = sysUser.getCreateTime();
            String month = createTime.substring(5,7);
            if("01".equals(month)){
                nanList.set(0,nanList.get(0) + 1);
            }else if("02".equals(month)){
                nanList.set(1,nanList.get(1) + 1);
            }else if("03".equals(month)){
                nanList.set(2,nanList.get(2) + 1);
            }else if("04".equals(month)){
                nanList.set(3,nanList.get(3) + 1);
            }else if("05".equals(month)){
                nanList.set(4,nanList.get(4) + 1);
            }else if("06".equals(month)){
                nanList.set(5,nanList.get(5) + 1);
            }else if("07".equals(month)){
                nanList.set(6,nanList.get(6) + 1);
            }else if("08".equals(month)){
                nanList.set(7,nanList.get(7) + 1);
            }else if("09".equals(month)){
                nanList.set(8,nanList.get(8) + 1);
            }else if("10".equals(month)){
                nanList.set(9,nanList.get(9) + 1);
            }else if("11".equals(month)){
                nanList.set(10,nanList.get(10) + 1);
            }else if("12".equals(month)){
                nanList.set(11,nanList.get(11) + 1);
            }
        }
        return nanList;
    }


    private ArrayList<Integer> loginSetValue(List<SysLogin> list) {
        ArrayList<Integer> loginList = CollUtil.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            SysLogin sysLogin = list.get(i);
            String loginTime = sysLogin.getLoginTime();
            String month = loginTime.substring(5,7);
            if("01".equals(month)){
                loginList.set(0,loginList.get(0) + 1);
            }else if("02".equals(month)){
                loginList.set(1,loginList.get(1) + 1);
            }else if("03".equals(month)){
                loginList.set(2,loginList.get(2) + 1);
            }else if("04".equals(month)){
                loginList.set(3,loginList.get(3) + 1);
            }else if("05".equals(month)){
                loginList.set(4,loginList.get(4) + 1);
            }else if("06".equals(month)){
                loginList.set(5,loginList.get(5) + 1);
            }else if("07".equals(month)){
                loginList.set(6,loginList.get(6) + 1);
            }else if("08".equals(month)){
                Integer integer = loginList.get(7);
                loginList.set(7,loginList.get(7) + 1);
            }else if("09".equals(month)){
                loginList.set(8,loginList.get(8) + 1);
            }else if("10".equals(month)){
                loginList.set(9,loginList.get(9) + 1);
            }else if("11".equals(month)){
                loginList.set(10,loginList.get(10) + 1);
            }else if("12".equals(month)){
                loginList.set(11,loginList.get(11) + 1);
            }
        }
        return loginList;
    }

    public void updatePassword(SysUser sysUser) {
        sysUserMapper.updatePassword(sysUser);
    }


}
