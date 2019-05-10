package com.tuxiaoer.shanghai.modules.system.hystrix;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.CodeMsg;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.feignclient.RoleFeignService;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/5/5 11:25
 * @description：断路器
 */
@Component
public class RoleServiceHystrix implements RoleFeignService {

    @Override
    public Result<PageInfo<Role>> rolePageList(Role role, Integer pageNum, Integer pageSize) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new PageInfo<>());
    }

    @Override
    public Result<Role> roleInfo(Role role) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new Role());
    }

    @Override
    public Result<String> save(Role role) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> delete(Role role) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> deleteAll(Long[] ids) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<Boolean> verifyRoleName(String roleName, String oldRoleName) {
        return Result.error(CodeMsg.PROVIDER_ERROR);
    }

    @Override
    public Result<HashMap<String, Object>> auth(Role role) {
        return Result.error(CodeMsg.ERROR);
    }

    @Override
    public Result<String> authSave(Role role) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }
}
