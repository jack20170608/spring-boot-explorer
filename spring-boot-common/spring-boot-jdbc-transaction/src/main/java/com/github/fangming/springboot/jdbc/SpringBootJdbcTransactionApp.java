package com.github.fangming.springboot.jdbc;

import com.github.fangming.springboot.jdbc.model.Account;
import com.github.fangming.springboot.jdbc.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class SpringBootJdbcTransactionApp implements ApplicationRunner {

    public static final Logger logger = LoggerFactory.getLogger(SpringBootJdbcTransactionApp.class);

    private final AccountService accountService;

    @Autowired
    public SpringBootJdbcTransactionApp(AccountService accountService) {
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJdbcTransactionApp.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("The default logger run successfully.");
        Account sourceAccount = accountService.create(Account.builder()
            .setName("Lucy")
            .setBalance(BigDecimal.ZERO)
            .setInsertDt(LocalDate.now())
            .setLastUpdateTs(LocalDateTime.now())
            .build());
        Account targetAccount = accountService.create(Account.builder()
            .setName("Lily")
            .setBalance(BigDecimal.ZERO)
            .setInsertDt(LocalDate.now())
            .setLastUpdateTs(LocalDateTime.now())
            .build());

        logger.info("All Account info before account transfer .");
        accountService.getAll().forEach(account -> System.out.println(account.toString()));

        logger.info("Begin to do account transfer for 1000 times.");
        for(int i = 0; i < 1000; i++ ) {
            accountService.transfer(sourceAccount, targetAccount, new BigDecimal(new Random().nextInt(10)));
        }

        logger.info("After do account transfer Account info.");
        accountService.getAll().forEach(account -> System.out.println(account.toString()));
    }
}
