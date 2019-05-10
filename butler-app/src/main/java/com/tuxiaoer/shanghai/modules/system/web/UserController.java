package com.tuxiaoer.shanghai.modules.system.web;

import com.github.pagehelper.PageInfo;
import com.tuxiaoer.shanghai.common.utils.Result;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.feignclient.UserFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/26 11:42
 * @description：
 */
@RestController
public class UserController {

    @Resource
    private UserFeignService userFeignService;

    @RequestMapping("/hello")
    public List<User> index(String username) {
        return userFeignService.userPageList(username, 10000, 2).getData().getList();
    }

}
