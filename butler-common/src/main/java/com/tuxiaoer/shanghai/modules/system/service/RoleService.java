package com.tuxiaoer.shanghai.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Role;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/16 10:17
 * @description：角色service
 */
public interface RoleService {

    /**
     * 通过角色Id获取角色
     * @param role
     * @return
     */
    Result<Role> getRoleByRoleId(Role role);

    /**
     * 分页查询角色列表
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result<PageInfo<Role>> getRoleListByPage(Role role, Integer pageNum, Integer pageSize);

    /**
     * 查询所有角色
     * @param role
     * @return
     */
    Result<List<Role>> getAllRoleList(Role role);

    /**
     * 查询用户的所有角色
     * @param userId
     * @return
     */
    Result<List<Role>> getUserAllRole(Long userId);

    /**
     * 保存角色
     * @param role
     * @return
     */
    Result<Role> insertRole(Role role);

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    Result<Boolean> upRoleById(Role role);

    /**
     * 根据角色Id删除角色
     * @param role
     * @return
     */
    Result<Boolean> delRoleByRoleId(Role role);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    Result<Boolean> delRolesByIds(Long[] ids);

    /**
     * 校验角色名是否重复
     * @param roleName
     * @param oldRoleName
     * @return
     */
    Result<Boolean> verifyRoleName(String roleName, String oldRoleName);

    /**
     * 更新角色菜单关系
     * @param role
     */
    Result<Boolean> authSave(Role role);

}
