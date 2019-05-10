package com.tuxiaoer.shanghai.modules.system.web;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.service.MenuService;
import com.tuxiaoer.shanghai.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/18 16:03
 * @description：角色controller
 */
@RestController
@RequestMapping(value = "server/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 角色分页
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "rolePageList")
    public Result<PageInfo<Role>> rolePageList(@RequestBody Role role, Integer pageNum, Integer pageSize) {
        return roleService.getRoleListByPage(role, pageNum, pageSize);
    }

    /**
     * 单个角色信息
     * @param role
     * @return
     */
    @RequestMapping(value = "roleInfo")
    public Result<Role> roleInfo(@RequestBody Role role) {
        if (role.getId() != null) {
            Result<Role> roleResult = roleService.getRoleByRoleId(role);
            return roleResult;
        }
        return Result.success(new Role());
    }

    /**
     * 添加或者编辑
     * @param role
     * @return
     */
    @RequestMapping(value = "save")
    public Result<String> save(@RequestBody Role role) {
        if (role.getId() == null) {
            roleService.insertRole(role);
        } else {
            roleService.upRoleById(role);
        }
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 单个删除
     * @param role
     * @return
     */
    @RequestMapping(value = "delete")
    public Result<String> delete(@RequestBody Role role) {
        roleService.delRoleByRoleId(role);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteAll")
    public Result<String> deleteAll(Long[] ids) {
        roleService.delRolesByIds(ids);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 校验角色名是不是新的
     *
     * @param roleName
     * @param oldRoleName
     * @return
     */
    @RequestMapping(value = "verifyRoleName")
    public Result<Boolean> verifyRoleName(String roleName, String oldRoleName) {
        Result<Boolean> booleanResult = roleService.verifyRoleName(roleName, oldRoleName);
        return booleanResult;
    }

    /**
     * 进入权限设置页面
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "auth")
    public Result<HashMap<String, Object>> auth(@RequestBody Role role) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        Result<Role> roleResult = roleService.getRoleByRoleId(role);
        Result<List<Menu>> menuListResult = menuService.getMenuList(new Menu());
        hashMap.put("role", roleResult.getData());
        hashMap.put("menuList", menuListResult.getData());
        return Result.success(hashMap);
    }

    /**
     * 权限保存
     * @param role
     * @return
     */
    @RequestMapping(value = "authSave")
    public Result<String> authSave(@RequestBody Role role) {
        roleService.authSave(role);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

}
