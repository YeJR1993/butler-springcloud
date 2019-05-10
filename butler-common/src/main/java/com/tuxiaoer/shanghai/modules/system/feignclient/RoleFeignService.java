package com.tuxiaoer.shanghai.modules.system.feignclient;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.Role;
import com.tuxiaoer.shanghai.modules.system.hystrix.RoleServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/26 11:38
 * @description：Feign 远程调用
 */
@FeignClient(value = "system-server", fallback = RoleServiceHystrix.class)
public interface RoleFeignService {

    /**
     * 角色分页
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "server/system/role/rolePageList")
    Result<PageInfo<Role>> rolePageList(@RequestBody Role role,
                                        @RequestParam(value = "pageNum") Integer pageNum,
                                        @RequestParam(value = "pageSize") Integer pageSize);


    /**
     * 单个角色信息
     * @param role
     * @return
     */
    @RequestMapping(value = "server/system/role/roleInfo")
    Result<Role> roleInfo(@RequestBody Role role);

    /**
     * 添加或者编辑
     * @param role
     * @return
     */
    @RequestMapping(value = "server/system/role/save")
    Result<String> save(@RequestBody Role role);

    /**
     * 单个删除
     * @param role
     * @return
     */
    @RequestMapping(value = "server/system/role/delete")
    Result<String> delete(@RequestBody Role role);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "server/system/role/deleteAll")
    Result<String> deleteAll(@RequestParam(value = "ids") Long[] ids);


    /**
     * 校验角色名是不是新的
     *
     * @param roleName
     * @param oldRoleName
     * @return
     */
    @RequestMapping(value = "server/system/role/verifyRoleName")
    Result<Boolean> verifyRoleName(@RequestParam(value = "roleName") String roleName,
                                   @RequestParam(value = "oldRoleName") String oldRoleName);


    /**
     * 进入权限设置页面
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "server/system/role/auth")
    Result<HashMap<String, Object>> auth(@RequestBody Role role);

    /**
     * 权限保存
     * @param role
     * @return
     */
    @RequestMapping(value = "server/system/role/authSave")
    Result<String> authSave(@RequestBody Role role);
}
