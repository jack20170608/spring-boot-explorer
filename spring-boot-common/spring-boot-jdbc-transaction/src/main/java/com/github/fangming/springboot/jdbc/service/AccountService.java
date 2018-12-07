package com.github.fangming.springboot.jdbc.service;

import com.github.fangming.springboot.jdbc.model.Account;

import java.math.BigDecimal;
import java.util.Collection;

public interface AccountService {

    Collection<Account> getAll();

    Account getById(Long id);

    Account create(Account account);

    void deleteAll();

    void transfer(Account source, Account target, BigDecimal amount);
}
