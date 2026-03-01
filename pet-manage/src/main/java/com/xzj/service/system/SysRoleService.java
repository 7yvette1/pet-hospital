package com.xzj.service.system;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.entity.system.SysRole;
import com.xzj.entity.system.SysRoleMenu;
import com.xzj.mapper.system.SysRoleMapper;
import com.xzj.mapper.system.SysRoleMenuMapper;
import com.xzj.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuzhijun
 * @date 2024/5/21 16:48
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 新增
     */
    public void add(SysRole sysRole) {
        sysRole.setCreateTime(DateUtil.now());
        sysRole.setCreateBy(TokenUtils.getCurrentUser().getUserId().toString());
        sysRoleMapper.add(sysRole);
    }

    /**
     * 根据ID修改
     */
    public void updateById(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * 根据ID删除
     */
    public void deleteById(Integer id) {
        sysRoleMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            sysRoleMapper.deleteById(id);
        }
    }

    /**
     * 根据ID查询
     */
    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<SysRole> selectAll(SysRole sysRole) {
        return sysRoleMapper.selectAll(sysRole);
    }

    /**
     * 分页查询
     */
    public PageInfo<SysRole> selectPage(SysRole sysRole, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> list = sysRoleMapper.selectAll(sysRole);
        for (SysRole role : list) {
            List<Integer> permissions = sysRoleMenuMapper.selectPermissions(role.getRoleId());
            role.setPermissionList(permissions);
        }
        return PageInfo.of(list);
    }

    /**
     * 分配权限
     */
    public void grantPermissions(SysRole sysRole) {
        //先删除角色对应下的菜单（原来的）
        Integer roleId = sysRole.getRoleId();
        sysRoleMenuMapper.deleteById(roleId);

        //再新增角色对应下的菜单（新的）
        List<Integer> permissionList = sysRole.getPermissionList();
        if(!CollectionUtils.isEmpty(permissionList)){
            for (int i = 0; i < permissionList.size(); i++){
                Integer menuId = permissionList.get(i);
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getRoleId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenuMapper.add(sysRoleMenu);
            }
        }
    }
}
