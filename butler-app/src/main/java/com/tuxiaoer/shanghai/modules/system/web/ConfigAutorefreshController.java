package com.tuxiaoer.shanghai.modules.system.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/5/17 17:51
 * @description：spring cloudconfig + spring cloud bus实现全自动刷新集群配置
 */
@RefreshScope
@RestController
public class ConfigAutorefreshController {

    @Value("${name}")
    private String name;

    @RequestMapping("/name")
    public String getName() {
        return name;
    }
}
