package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.model.Account;

import java.util.Collection;

public interface AccountDao {

    Account getById(Long id);

    Collection<Account> getAll();

    Account update(Account account);

    void deleteById(Long id);

    void deleteAll();
}
