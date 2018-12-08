package com.github.fangming.springboot.jdbc.service.impl;

import com.github.fangming.springboot.jdbc.dao.AccountDao;
import com.github.fangming.springboot.jdbc.model.Account;
import com.github.fangming.springboot.jdbc.pool.Pool;
import com.github.fangming.springboot.jdbc.pool.SimpleConnectionPool;
import com.github.fangming.springboot.jdbc.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountDao accountDao;
    private final Pool<Connection> connectionPool;

    @Autowired
    public AccountServiceImpl(final AccountDao accountDao, final @Qualifier("blockingPool") Pool<Connection> connectionPool) {
        this.accountDao = accountDao;
        this.connectionPool = connectionPool;
    }


    @Override
    public Collection<Account> getAll() {
        Connection conn = null;
        Collection<Account> accounts;
        try {
            conn = connectionPool.get();
            accounts = accountDao.getAll(conn);
        }catch (Throwable throwable){
            throw new RuntimeException(throwable);
        }finally {
            if (null != conn) {
                connectionPool.release(conn);
            }
        }
        return accounts;
    }

    @Override
    public Account getById(Long id) {
        Connection conn = null;
        Account account;
        try {
            conn = connectionPool.get();
            account = accountDao.getById(conn, id);
        }catch (Throwable throwable){
            throw new RuntimeException(throwable);
        }finally {
            if (null != conn) {
                connectionPool.release(conn);
            }
        }
        return account;
    }

    @Override
    public Account create(Account account) {
        Connection conn = null;
        Account persistedAccount = null;
        try {
            conn = connectionPool.get();
            persistedAccount = accountDao.create(conn, account);
            conn.commit();
        }catch (Throwable throwable){
            if (null != conn){
                try {
                    conn.rollback();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }finally {
            if (null != conn) {
                connectionPool.release(conn);
            }
        }
        return persistedAccount;
    }

    @Override
    public void deleteAll() {
        Connection conn = null;
        try {
            conn = connectionPool.get();
            accountDao.deleteAll(conn);
            conn.commit();
        }catch (Throwable throwable){
            if (null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }finally {
            if (null != conn) {
                connectionPool.release(conn);
            }
        }
    }

    @Override
    public void transfer(Account source, Account target, BigDecimal amount) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        Objects.requireNonNull(amount);

        logger.info("Transfer [{}] from [{}] to [{}].", amount, source, target);
        Connection conn = null;
        try {
            conn = connectionPool.get();
            accountDao.update(conn, Account.builder(source).setBalance(source.getBalance().subtract(amount)).build());
            accountDao.update(conn, Account.builder(target).setBalance(target.getBalance().add(amount)).build());
            conn.commit();
        }catch (Throwable throwable){
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(throwable);
        }finally {
            if (null != conn) {
                connectionPool.release(conn);
            }
        }
    }
}
