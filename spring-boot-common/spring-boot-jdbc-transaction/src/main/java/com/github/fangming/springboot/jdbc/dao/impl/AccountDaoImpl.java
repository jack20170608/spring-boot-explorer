package com.github.fangming.springboot.jdbc.dao.impl;

import com.github.fangming.springboot.ThrowingFunction;
import com.github.fangming.springboot.jdbc.dao.AbstractDaoImpl;
import com.github.fangming.springboot.jdbc.dao.AccountDao;
import com.github.fangming.springboot.jdbc.model.Account;
import com.github.fangming.springboot.jdbc.pool.SimpleConnectionPool;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class AccountDaoImpl extends AbstractDaoImpl implements AccountDao {

    @Autowired
    public AccountDaoImpl(SimpleConnectionPool simpleConnectionPool) {
        super(simpleConnectionPool);
    }

    private static final String GET_BY_ID_SQL = "select * from t_account where id = ?";
    private static final String GET_ALL_SQL = "select * from t_account ";
    private static final String UPDATE_SQL = "update t_account set name = ?, balance = ?, last_update_dt = ?";
    private static final String CREATE_SQL = "insert into t_account values (?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "delete from t_account where id = ?";
    private static final String DELETE_ALL_SQL = "delete from t_account ";

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    private static final ThrowingFunction<ResultSet, Account, SQLException> ACCOUNT_MAPPER = rs -> {
        Account account = new Account(rs.getLong(1));
        account.setName(rs.getString(2));
        account.setBalance(rs.getBigDecimal(3));
        account.setInsertDt(rs.getDate(4).toLocalDate());
        account.setLastUpdateTs(rs.getTimestamp(5).toLocalDateTime());
        return account;
    };

//    private static final ThrowingFunction<AccountResultSet, , SQLException> ACCOUNT_UN_MAPPER = rs -> {
//        Account account = new Account(rs.getLong(1));
//        account.setName(rs.getString(2));
//        account.setBalance(rs.getBigDecimal(3));
//        account.setInsertDt(rs.getDate(4).toLocalDate());
//        account.setLastUpdateTs(rs.getTimestamp(5).toLocalDateTime());
//        return account;
//    };

    @Override
    public Account getById(Connection connection,Long id) {
        LOGGER.info("Get by Id [{}]", id);
        Account account = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(GET_BY_ID_SQL);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            while (rs.next()){
                account = ACCOUNT_MAPPER.apply(rs);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if (null != rs){
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
            }
            if (null != statement){
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }
        return account;
    }


    @Override
    public Collection<Account> getAll(Connection connection) {
        LOGGER.info("Get all accounts");
        Collection<Account> accounts = Sets.newHashSet();
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(GET_ALL_SQL)) {
            while (rs.next()) {
                Account account = ACCOUNT_MAPPER.apply(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public Account create(Connection connection,Account account) {
        LOGGER.info("Persist new account [{}]", account);
        try (PreparedStatement statement = connection.prepareStatement(CREATE_SQL)) {
            statement.setLong(1, account.getId());
            statement.setString(2, account.getName());
            statement.setBigDecimal(3, account.getBalance());
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    @Override
    public Account update(Connection connection,Account account) {
        return null;
    }

    @Override
    public void deleteById(Connection connection,Long id) {

    }

    @Override
    public void deleteAll(Connection connection) {

    }
}
