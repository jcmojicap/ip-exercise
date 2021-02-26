package com.meli.ipexercise.initializer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@ComponentScan(basePackages = {"com.meli.ipexercise"})
@EnableConfigurationProperties
@EntityScan("com.meli.ipexercise.persistence")
@EnableJpaRepositories("com.meli.ipexercise.persistence")
public class InitializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitializerApplication.class, args);
    }
}

