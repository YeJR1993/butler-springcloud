package com.tuxiaoer.shanghai.modules.system.feignclient;

import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.hystrix.MenuServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/26 11:38
 * @description：Feign 远程调用
 */
@FeignClient(value = "system-server", fallback = MenuServiceHystrix.class)
public interface MenuFeignService {


    /**
     * 获取用户对应的菜单权限
     * @param userId
     * @param isShow
     * @param isAdmin
     * @return
     */
    @RequestMapping(value = "server/system/menu/getMenuByUserId")
    Result<List<Menu>> getMenuByUserId(@RequestParam(value = "userId") Long userId,
                                       @RequestParam(value = "isShow") Integer isShow,
                                       @RequestParam(value = "isAdmin") Boolean isAdmin);

    /**
     * menu list
     * @param menu
     * @return
     */
    @RequestMapping(value = "server/system/menu/menuList")
    Result<List<Menu>> menuList(@RequestBody Menu menu);

    /**
     * 单menu查询
     * @param menu
     * @return
     */
    @RequestMapping(value = "server/system/menu/menuInfo")
    Result<Menu> menuInfo(@RequestBody Menu menu);

    /**
     * 添加或者编辑
     * @param menu
     * @return
     */
    @RequestMapping(value = "server/system/menu/save")
    Result<String> save(@RequestBody Menu menu);


    /**
     * 单个删除
     * @param menu
     * @return
     */
    @RequestMapping(value = "server/system/menu/delete")
    Result<String> delete(@RequestBody Menu menu);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "server/system/menu/deleteAll")
    Result<String> deleteAll(@RequestParam(value = "ids") Long[] ids);
}
