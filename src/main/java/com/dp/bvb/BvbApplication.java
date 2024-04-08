package com.dp.bvb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"com.dp.bvb.entity"})
@EnableScheduling
public class BvbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BvbApplication.class, args);
    }

}
