package com.tuxiaoer.shanghai.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.dao.RoleDao;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/17 10:44
 * @description：角色服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Result<Role> getRoleByRoleId(Role role) {
        return Result.success(roleDao.getRoleById(role));
    }

    @Override
    public Result<PageInfo<Role>> getRoleListByPage(Role role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Role> page = new PageInfo<>(roleDao.getRoleList(role));
        return Result.success(page);
    }

    @Override
    public Result<List<Role>> getAllRoleList(Role role) {
        List<Role> roleList = roleDao.getRoleList(role);
        return Result.success(roleList);
    }

    @Override
    public Result<List<Role>> getUserAllRole(Long userId) {
        List<Role> list = new ArrayList<>();
        if (userId != null) {
            list = roleDao.getUserAllRole(userId);
        }
        return Result.success(list);
    }

    @Override
    public Result<Role> insertRole(Role role) {
        roleDao.insertRole(role);
        return Result.success(role);
    }

    @Override
    public Result<Boolean> upRoleById(Role role) {
        roleDao.upRoleById(role);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delRoleByRoleId(Role role) {
        // 删除当前角色
        roleDao.delRoleById(role);
        // 删除角色菜单关系
        roleDao.delRoleMenuById(role);
        // 删除角色用户关系
        roleDao.delRoleUserById(role);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delRolesByIds(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                delRoleByRoleId(new Role(id));
            }
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> verifyRoleName(String roleName, String oldRoleName) {
        Boolean result = false;
        if (StringUtils.isNotBlank(roleName)) {
            if (roleName.equals(oldRoleName)) {
                result = true;
            }
            Role role = roleDao.getRoleByName(roleName);
            if (role == null) {
                result = true;
            }
        }
        return Result.success(result);
    }

    @Override
    public Result<Boolean> authSave(Role role) {
        // 先删除角色原有的对应的菜单
        roleDao.delRoleMenuById(role);
        //接着插入新的对应关系
        if (role.getMenus() != null && role.getMenus().size() > 0) {
            roleDao.insertRoleMenu(role);
        }
        return Result.success(true);
    }
}
