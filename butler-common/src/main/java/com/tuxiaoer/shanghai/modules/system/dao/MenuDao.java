package com.tuxiaoer.shanghai.modules.system.dao;

import com.tuxiaoer.shanghai.modules.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/16 9:54
 * @description：菜单DAO
 */
@Mapper
public interface MenuDao {

    /**
     * 通过菜单ID查询菜单信息
     * @param menu
     * @return
     */
    Menu getMenuById(Menu menu);

    /**
     * 查询所有菜单列表
     * @param menu
     * @return
     */
    List<Menu> getMenuList(Menu menu);

    /**
     * 查询用户对应的菜单权限
     * @param userId
     * @param isShow
     * @return
     */
    List<Menu> getMenuByUserId(@Param("userId") Long userId, @Param("isShow") Integer isShow);

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    Integer insertMenu(Menu menu);

    /**
     * 根据菜单Id修改菜单
     * @param menu
     * @return
     */
    Integer upMenuById(Menu menu);

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    Integer delMenuById(Menu menu);

    /**
     * 删除菜单角色关系
     * @param menu
     * @return
     */
    Integer delMenuRoleById(Menu menu);

}
