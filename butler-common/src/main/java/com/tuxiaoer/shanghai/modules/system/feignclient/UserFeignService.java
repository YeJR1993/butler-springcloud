package com.tuxiaoer.shanghai.modules.system.feignclient;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.hystrix.UserServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/26 11:38
 * @description：Feign 远程调用
 */
@FeignClient(value = "system-server", fallback = UserServiceHystrix.class)
public interface UserFeignService {

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    @RequestMapping(value = "server/system/user/getUserByUsername")
    Result<User> getUserByUsername(@RequestParam(value = "username") String username);

    /**
     * 用户分页
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "server/system/user/userPageList")
    Result<PageInfo<User>> userPageList(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "pageNum") Integer pageNum,
                                        @RequestParam(value = "pageSize") Integer pageSize);

    /**
     * 用户列表
     * @param user
     * @return
     */
    @RequestMapping(value = "server/system/user/userList")
    Result<List<User>> userList(@RequestBody User user);

    /**
     * 查询当前用户以及角色信息
     * @param user
     * @return
     */
    @RequestMapping(value = "server/system/user/userAndRole")
    Result<HashMap<String, Object>> userAndRole(@RequestBody User user);

    /**
     * 保存用户
     * @param user
     * @return
     */
    @RequestMapping(value = "server/system/user/save")
    Result<String> save(@RequestBody User user);

    /**
     * 单个删除用户
     * @param user
     * @return
     */
    @RequestMapping(value = "server/system/user/delete")
    Result<String> delete(@RequestBody User user);


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "server/system/user/deleteAll")
    Result<String> deleteAll(@RequestParam(value = "ids") Long[] ids);

    /**
     * 校验用户名是不是新的
     * @param username
     * @param oldUsername
     * @return
     */
    @RequestMapping(value = "server/system/user/verifyUsername")
    Result<Boolean> verifyUsername(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "oldUsername") String oldUsername);


}
