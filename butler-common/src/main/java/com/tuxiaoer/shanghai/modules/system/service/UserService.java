package com.tuxiaoer.shanghai.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.User;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/4 9:45
 * @descideaription：系统用户Service接口
 */
public interface UserService {

    /**
     * 通过用户名获取用户信息
     * @param user
     * @return
     */
    Result<User> getUserByUserId(User user);


    /**
     * 通过用户名查询
     * @param user
     * @return
     */
    Result<User> getUserByName(User user);

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    Result<List<User>> getUserList(User user);

    /**
     * （分页）查询用户列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result<PageInfo<User>> getUserListByPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 保存用户
     * @param user
     * @return
     */
    Result<User> insertUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Result<Boolean> upUserByUserId(User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    Result<Boolean> delUserByUserId(User user);

    /**
     * 批量删除
     * @param ids
     */
    Result<Boolean> delUsersByIds(Long[] ids);

    /**
     * 校验用户名是否重复
     * @param username
     * @param oldUsername
     * @return
     */
    Result<Boolean> verifyUsername(String username, String oldUsername);
}
