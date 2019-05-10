package com.tuxiaoer.shanghai.modules.system.service;

import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/16 15:43
 * @description：菜单Service
 */
public interface MenuService {

    /**
     * 通过菜单ID查询菜单信息
     * @param menu
     * @return
     */
    Result<Menu> getMenuById(Menu menu);

    /**
     * 查询所有菜单, 并进行排序
     * @param menu
     * @return
     */
    Result<List<Menu>> getMenuList(Menu menu);

    /**
     * 查询用户对应的菜单权限
     * @param userId
     * @param isShow null:所有菜单， 0：隐藏的菜单， 1：显示的菜单
     * @return
     */
    Result<List<Menu>> getMenuByUserId(Long userId, Integer isShow, Boolean isAdmin);

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    Result<Menu> insertMenu(Menu menu);

    /**
     * 根据菜单Id修改菜单
     * @param menu
     * @return
     */
    Result<Boolean> upMenuById(Menu menu);

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    Result<Boolean> delMenuById(Menu menu);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Result<Boolean> delMenusByIds(Long[] ids);

}
