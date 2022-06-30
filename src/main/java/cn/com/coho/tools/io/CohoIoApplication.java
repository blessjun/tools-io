package cn.com.coho.tools.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author scott
 */
@EnableScheduling
@SpringBootApplication
public class CohoIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CohoIoApplication.class, args);
    }
}
