package com.tuxiaoer.shanghai.modules.system.hystrix;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.CodeMsg;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.feignclient.UserFeignService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/5/5 11:25
 * @description：断路器
 */
@Component
public class UserServiceHystrix implements UserFeignService {

    @Override
    public Result<User> getUserByUsername(String username) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new User());
    }

    @Override
    public Result<PageInfo<User>> userPageList(String username, Integer pageNum, Integer pageSize) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new PageInfo<>());
    }

    @Override
    public Result<List<User>> userList(User user) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new ArrayList<>(0));
    }

    @Override
    public Result<HashMap<String, Object>> userAndRole(User user) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new HashMap<>(0));
    }

    @Override
    public Result<String> save(User user) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> delete(User user) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> deleteAll(Long[] ids) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<Boolean> verifyUsername(String username, String oldUsername) {
        return Result.error(CodeMsg.PROVIDER_ERROR);
    }
}
