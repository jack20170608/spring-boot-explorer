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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        accountService.deleteAll();

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

        final Long sourceAccountId = sourceAccount.getId();
        final Long targetAccountId = targetAccount.getId();

//        logger.info("Begin to do account transfer for 1000 times in the main thread.");
//        for (int i = 0; i < 1000; i++) {
//            Account source = accountService.getById(sourceAccountId);
//            Account target = accountService.getById(targetAccountId);
//            accountService.transfer(source, target, new BigDecimal(new Random().nextInt(10)));
//        }
//        logger.info("After do account transfer Account info.");
//        accountService.getAll().forEach(account -> System.out.println(account.toString()));
        logger.info("------------------------------------------------------------");

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        logger.info("Begin to do account transfer for 1000 times in the multiple thread.");
        for (int i = 0; i < 1; i++) {
            //Multiple thread execution
            int finalI = i;
            executorService.submit(() -> {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
                logger.info("thread:" + Thread.currentThread().getName() + ",time:" + format.format(new Date()) + ",num:" + finalI);
                Account source = accountService.getById(sourceAccountId);
                Account target = accountService.getById(targetAccountId);
                accountService.transfer(source, target, new BigDecimal(new Random().nextInt(10)));
            });
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(10, TimeUnit.SECONDS));

        logger.info("After do account transfer Account info.");
        accountService.getAll().forEach(account -> System.out.println(account.toString()));
    }
}
