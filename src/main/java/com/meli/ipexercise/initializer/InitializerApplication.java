package com.meli.ipexercise.initializer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@ComponentScan(basePackages = {"com.meli.ipexercise"})
@EnableConfigurationProperties
@EnableAsync
public class InitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }
}

