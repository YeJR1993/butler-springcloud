package com.tuxiaoer.shanghai.modules.system.dao;

import com.tuxiaoer.shanghai.modules.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/15 11:11
 * @description：角色DAO
 */
@Mapper
public interface RoleDao {

    /**
     * 通过角色Id获取角色信息
     * @param role
     * @return
     */
    Role getRoleById(Role role);

    /**
     * 通过roleName获取角色信息
     * @param roleName
     * @return
     */
    Role getRoleByName(@Param("roleName") String roleName);

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    List<Role> getRoleList(Role role);

    /**
     * 查询用户的所有角色
     * @param userId
     * @return
     */
    List<Role> getUserAllRole(@Param("userId") Long userId);

    /**
     * 保存角色
     * @param role
     * @return
     */
    Integer insertRole(Role role);

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    Integer upRoleById(Role role);

    /**
     * 根据角色Id删除角色
     * @param role
     * @return
     */
    Integer delRoleById(Role role);

    /**
     * 删除角色菜单关联数据
     * @param role
     * @return
     */
    Integer delRoleMenuById(Role role);

    /**
     * 保存角色菜单关联数据
     * @param role
     * @return
     */
    Integer insertRoleMenu(Role role);

    /**
     * 删除角色用户关联数据
     * @param role
     * @return
     */
    Integer delRoleUserById(Role role);
}
