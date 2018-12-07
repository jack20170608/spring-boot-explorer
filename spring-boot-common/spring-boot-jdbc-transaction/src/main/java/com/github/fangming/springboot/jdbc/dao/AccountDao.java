package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.model.Account;

import java.sql.Connection;
import java.util.Collection;

public interface AccountDao {

    Account getById(Connection connection, Long id);

    Collection<Account> getAll(Connection connection);

    Account create(Connection connection,Account account);

    Account update(Connection connection,Account account);

    void deleteById(Connection connection,Long id);

    void deleteAll(Connection connection);
}
