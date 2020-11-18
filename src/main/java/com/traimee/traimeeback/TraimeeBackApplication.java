package com.traimee.traimeeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TraimeeBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraimeeBackApplication.class, args);
    }

}
