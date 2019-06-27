package com.tuxiaoer.shanghai.common.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/5/20 16:12
 * @description：
 */
@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path-route", p -> p
                        .path("/hello")
                        .filters(f -> f.addRequestHeader("username", "ye"))
                        .uri("http://localhost:8085"))
                .route("hystrix-route", p -> p
                        //根据host路由
                        .host("*.abc.com")
                        .filters(f -> f.hystrix(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }
}
