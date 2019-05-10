package com.tuxiaoer.shanghai.modules.system.hystrix;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.CodeMsg;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.feignclient.MenuFeignService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/5/5 11:25
 * @description：断路器
 */
@Component
public class MenuServiceHystrix implements MenuFeignService {

    @Override
    public Result<List<Menu>> getMenuByUserId(Long userId, Integer isShow, Boolean isAdmin) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new ArrayList<>(0));
    }

    @Override
    public Result<List<Menu>> menuList(Menu menu) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new ArrayList<>(0));
    }

    @Override
    public Result<Menu> menuInfo(Menu menu) {
        return Result.error(CodeMsg.PROVIDER_ERROR, new Menu());
    }

    @Override
    public Result<String> save(Menu menu) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> delete(Menu menu) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }

    @Override
    public Result<String> deleteAll(Long[] ids) {
        return Result.error(CodeMsg.PROVIDER_ERROR, SystemConstants.OPERATE_FAILED_PAGE_TIP);
    }
}
