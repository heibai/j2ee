package com.example.shujuku;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class ShujukuApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShujukuApplication.class, args);
        log.info("=============== 项目已启动 ===============");
    }

}
