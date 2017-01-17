package com.springerNautre.watermark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by ganeshnagarajan on 1/12/17.
 */
@SpringBootApplication
@EnableScheduling
public class WatermarkApplication {
    private static final Logger log = LoggerFactory.getLogger(WatermarkApplication.class);

    public static void main(String[] args){
        SpringApplication.run(WatermarkApplication.class,args);
        log.info("WatermarkApplication started successfully");
    }
}
