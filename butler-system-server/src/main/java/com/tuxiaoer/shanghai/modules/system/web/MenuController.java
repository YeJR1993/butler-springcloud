package com.tuxiaoer.shanghai.modules.system.web;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/18 16:18
 * @description：菜单controller
 */
@RestController
@RequestMapping(value = "server/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取用户对应的菜单权限
     * @param userId
     * @param isShow
     * @param isAdmin
     * @return
     */
    @RequestMapping(value = "getMenuByUserId")
    public Result<List<Menu>> getMenuByUserId(Long userId, Integer isShow, Boolean isAdmin) {
        return menuService.getMenuByUserId(userId, isShow, isAdmin);
    }

    /**
     * menu list
     * @param menu
     * @return
     */
    @RequestMapping(value = "menuList")
    public Result<List<Menu>> menuList(@RequestBody Menu menu) {
        Result<List<Menu>> menuListResult = menuService.getMenuList(menu);
        return menuListResult;
    }

    /**
     * 单menu查询
     * @param menu
     * @return
     */
    @RequestMapping(value = "menuInfo")
    public Result<Menu> form(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            Result<Menu> menuResult = menuService.getMenuById(menu);
            menu = menuResult.getData();
        }
        // 添加下级菜单，需要获取父菜单信息
        if (menu.getParentId() != null) {
            Menu parent = new Menu(menu.getParentId());
            Result<Menu> menuResult = menuService.getMenuById(parent);
            menu.setParent(menuResult.getData());
        }
        return Result.success(menu);
    }

    /**
     * 添加或者编辑
     * @param menu
     * @return
     */
    @RequestMapping(value = "save")
    public Result<String> save(@RequestBody Menu menu) {
        if (menu.getId() == null) {
            menuService.insertMenu(menu);
        } else {
            menuService.upMenuById(menu);
        }
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 单个删除
     * @param menu
     * @return
     */
    @RequestMapping(value = "delete")
    public Result<String> delete(@RequestBody Menu menu) {
        menuService.delMenuById(menu);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteAll")
    public Result<String> deleteAll(Long[] ids) {
        menuService.delMenusByIds(ids);
        return Result.success(SystemConstants.OPERATE_SUCCESS_PAGE_TIP);
    }

}
