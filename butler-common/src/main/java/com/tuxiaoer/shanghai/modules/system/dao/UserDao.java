package com.tuxiaoer.shanghai.modules.system.dao;

import com.tuxiaoer.shanghai.modules.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/4 15:04
 * @description：系统用户Dao
 */
@Mapper
public interface UserDao {

    /**
     * 通过用户名查询用户
     * @param user
     * @return
     */
    User getUserById(User user);

    /**
     * 通过用户名查询
     * @param user
     * @return
     */
    User getUserByName(User user);

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    List<User> getUserList(User user);

    /**
     * 保存用户
     * @param user
     * @return
     */
    Integer insertUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer upUserById(User user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    Integer delUserById(User user);

    /**
     * 删除用户角色关系
     * @param user
     * @return
     */
    Integer delUserRoleById(User user);

    /**
     * 保存用户角色关系
     * @param user
     * @return
     */
    Integer insertUserRole(User user);

}
