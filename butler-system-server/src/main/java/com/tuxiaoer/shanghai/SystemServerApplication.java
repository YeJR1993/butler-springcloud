package com.tuxiaoer.shanghai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/24 11:00
 * @description：provider
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServerApplication.class, args);
    }
}
