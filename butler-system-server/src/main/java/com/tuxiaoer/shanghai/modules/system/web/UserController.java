package com.tuxiaoer.shanghai.modules.system.web;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.service.RoleService;
import com.tuxiaoer.shanghai.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/24 11:45
 * @description：
 */
@RestController
@RequestMapping(value = "server/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    @RequestMapping(value = "getUserByUsername")
    public Result<User> getUserByUsername(String username) {
        Result<User> result = userService.getUserByName(new User(username));
        return result;
    }

    /**
     * 用户分页列表
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "userPageList")
    public Result<PageInfo<User>> userPageList(String username, Integer pageNum, Integer pageSize) {
        User user = new User(username);
        return userService.getUserListByPage(user, pageNum, pageSize);
    }

    /**
     * 用户列表
     * @param user
     * @return
     */
    @RequestMapping(value = "userList")
    public Result<List<User>> userList(@RequestBody User user) {
        Result<List<User>> listResult = userService.getUserList(user);
        return listResult;
    }

    /**
     * 获取用户及对应的角色信息
     * @param user
     * @return
     */
    @RequestMapping(value = "userAndRole")
    public Result<HashMap<String, Object>> userAndRole(@RequestBody User user) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        if (user.getId() != null) {
            Result<User> reuslt = userService.getUserByUserId(user);
            hashMap.put("user", reuslt.getData());
            Result<List<Role>> userAllRole = roleService.getUserAllRole(user.getId());
            hashMap.put("allRoles", userAllRole.getData());
        } else {
            Role role = new Role();
            role.setStatus(1);
            Result<List<Role>> roleList = roleService.getAllRoleList(role);
            hashMap.put("user", new User());
            hashMap.put("allRoles", roleList.getData());
        }
        return Result.success(hashMap);
    }

    /**
     * 添加或者编辑用户
     * @param user
     * @return
     */
    @RequestMapping(value = "save")
    public Result<String> save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.insertUser(user);
        } else {
            userService.upUserByUserId(user);
        }
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 单个删除
     * @param user
     * @return
     */
    @RequestMapping(value = "delete")
    public Result<String> delete(@RequestBody User user) {
        userService.delUserByUserId(user);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteAll")
    public Result<String> deleteAll(Long[] ids) {
        userService.delUsersByIds(ids);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }


    /**
     * 校验用户名是不是新的
     * @param username
     * @param oldUsername
     * @return
     */
    @RequestMapping(value = "verifyUsername")
    public Result<Boolean> verifyUsername(String username, String oldUsername) {
        Result<Boolean> booleanResult = userService.verifyUsername(username, oldUsername);
        return booleanResult;
    }
}
