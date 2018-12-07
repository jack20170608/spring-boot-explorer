package com.github.fangming.springboot.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJdbcTransactionApp implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(SpringBootJdbcTransactionApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJdbcTransactionApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("The default logger run successfully.");
    }
}
