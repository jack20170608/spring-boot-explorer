package com.github.fangming.springboot.jdbc.service;

import com.github.fangming.springboot.jdbc.model.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account create(Account account);

    void deleteAll();

    void transfer(Account source, Account target, BigDecimal amount);
}
