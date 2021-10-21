package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ldy
 * @version 1.0
 */
@EnableDiscoveryClient // 当前版本可以不写
@EnableFeignClients
@SpringBootApplication
public class CrowdMainClass {

    public static void main(String[] args) {
        SpringApplication.run(com.atguigu.crowd.CrowdMainClass.class, args);
    }
}
