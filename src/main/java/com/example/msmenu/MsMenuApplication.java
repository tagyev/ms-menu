package com.example.msmenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.example.msmenu.feignClient"})
@SpringBootApplication
public class MsMenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMenuApplication.class, args);
    }

}
