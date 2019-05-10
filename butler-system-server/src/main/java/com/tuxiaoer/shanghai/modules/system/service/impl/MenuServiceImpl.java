package com.tuxiaoer.shanghai.modules.system.service.impl;

import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.dao.MenuDao;
import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import com.tuxiaoer.shanghai.modules.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/17 11:06
 * @description：菜单服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public Result<Menu> getMenuById(Menu menu) {
        menu = menuDao.getMenuById(menu);
        return Result.success(menu);
    }

    @Override
    public Result<List<Menu>> getMenuList(Menu menu) {
        // 查询并进行排序
        List<Menu> list = new ArrayList<>();
        List<Menu> sourceList = menuDao.getMenuList(menu);
        Menu.sortList(list, sourceList, 1L);
        return Result.success(list);
    }

    @Override
    public Result<List<Menu>> getMenuByUserId(Long userId, Integer isShow, Boolean isAdmin) {
        List<Menu> menus;
        if (isAdmin) {
            Menu menu = new Menu();
            menu.setIsShow(isShow);
            menus = menuDao.getMenuList(menu);
        } else {
            menus = menuDao.getMenuByUserId(userId, isShow);
        }
        List<Menu> list = new ArrayList<>();
        Menu.sortList(list, menus, 1L);
        return Result.success(list);
    }

    @Override
    public Result<Menu> insertMenu(Menu menu) {
        menuDao.insertMenu(menu);
        return Result.success(menu);
    }

    @Override
    public Result<Boolean> upMenuById(Menu menu) {
        menuDao.upMenuById(menu);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delMenuById(Menu menu) {
        // 因为需要删除该菜单下的所有子菜单以及子子菜单等等，先查询
        menu = menuDao.getMenuById(menu);
        // 递归删除子菜单、子子菜单等等
        if (menu != null && menu.getChildren() != null && menu.getChildren().size() > 0) {
            for(Menu childMenu : menu.getChildren()){
                delMenuById(childMenu);
            }
        }
        menuDao.delMenuById(menu);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delMenusByIds(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                delMenuById(new Menu(id));
            }
        }
        return Result.success(true);
    }
}
