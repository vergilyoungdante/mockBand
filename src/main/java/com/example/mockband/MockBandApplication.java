package com.example.mockband;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mockband.mapper")
public class MockBandApplication {
//111111
    public static void main(String[] args) {
        SpringApplication.run(MockBandApplication.class, args);
    }

}
