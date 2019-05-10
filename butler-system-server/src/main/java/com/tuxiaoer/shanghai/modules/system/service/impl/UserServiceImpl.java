package com.tuxiaoer.shanghai.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.common.utils.UserUtil;
import com.tuxiaoer.shanghai.modules.system.dao.UserDao;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/4 17:44
 * @description：系统用户服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result<User> getUserByUserId(User user) {
        user = userDao.getUserById(user);
        return Result.success(user);
    }

    @Override
    public Result<User> getUserByName(User user) {
        return Result.success(userDao.getUserByName(user));
    }

    @Override
    public Result<List<User>> getUserList(User user) {
        List<User> userList = userDao.getUserList(user);
        return Result.success(userList);
    }

    @Override
    public Result<PageInfo<User>> getUserListByPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.getUserList(user));
        return Result.success(pageInfo);
    }

    @Override
    public Result<User> insertUser(User user) {
        String newPassword = UserUtil.encryptPassword(user.getUsername(), user.getPassword());
        user.setPassword(newPassword);
        Integer result = userDao.insertUser(user);
        if (result != null && result > 0 && user.getRoleIds() != null) {
            userDao.insertUserRole(user);
        }
        return Result.success(user);
    }

    @Override
    public Result<Boolean> upUserByUserId(User user) {
        if (StringUtils.isNoneBlank(user.getPassword())) {
            String newPassword = UserUtil.encryptPassword(user.getUsername(), user.getPassword());
            user.setPassword(newPassword);
        }
        userDao.upUserById(user);
        // 删除用户的关联关系
        userDao.delUserRoleById(user);
        if (user.getRoleIds() != null && user.getRoleIds().size() > 0) {
            //插入新的用户角色关联关系
            userDao.insertUserRole(user);
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delUserByUserId(User user) {
        // 删除用户
        userDao.delUserById(user);
        // 删除用户角色关系
        userDao.delUserRoleById(user);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delUsersByIds(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                delUserByUserId(new User(id));
            }
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> verifyUsername(String username, String oldUsername) {
        Boolean result = false;
        if (StringUtils.isNotBlank(username)) {
            if (username.equals(oldUsername)) {
                result = true;
            }
            User user = userDao.getUserByName(new User(username));
            if (user == null) {
                result = true;
            }
        }
        return Result.success(result);
    }


}
