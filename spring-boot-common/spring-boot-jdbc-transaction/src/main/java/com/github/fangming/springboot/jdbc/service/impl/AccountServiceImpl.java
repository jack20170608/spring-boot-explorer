package com.github.fangming.springboot.jdbc.service.impl;

import com.github.fangming.springboot.jdbc.dao.AccountDao;
import com.github.fangming.springboot.jdbc.model.Account;
import com.github.fangming.springboot.jdbc.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public Account create(Account account) {
        return accountDao.create(account);
    }

    @Override
    public void deleteAll() {
        accountDao.deleteAll();
    }

    @Override
    public void transfer(Account source, Account target, BigDecimal amount) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        Objects.requireNonNull(amount);

        logger.info("Transfer [[]] from [{}] to [{}].", amount, source, target);
        source.setBalance(source.getBalance().subtract(amount));
        target.setBalance(target.getBalance().add(amount));
        accountDao.update(source);
        accountDao.update(target);
    }
}
