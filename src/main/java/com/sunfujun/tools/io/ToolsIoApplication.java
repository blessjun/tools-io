package com.sunfujun.tools.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author scott
 */
@EnableScheduling
@SpringBootApplication
public class ToolsIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsIoApplication.class, args);
    }
}
