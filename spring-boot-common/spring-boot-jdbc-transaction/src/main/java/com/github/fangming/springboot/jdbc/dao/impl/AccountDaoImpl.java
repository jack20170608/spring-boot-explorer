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
    private static final String GET_NEXT_ID_SQL = "select account_id_sequence.nextval from dual";
    private static final String GET_ALL_SQL = "select * from t_account ";
    private static final String UPDATE_SQL = "update t_account set name = ?, balance = ?, last_update_dt = ? where id = ?";
    private static final String CREATE_SQL = "insert into t_account values (?,?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "delete from t_account where id = ?";
    private static final String DELETE_ALL_SQL = "delete from t_account ";

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDaoImpl.class);

    private static final ThrowingFunction<ResultSet, Account, SQLException> ACCOUNT_MAPPER = rs -> {
        return Account.builder()
            .setId(rs.getLong(1))
            .setName(rs.getString(2))
            .setBalance(rs.getBigDecimal(3))
            .setInsertDt(rs.getDate(4).toLocalDate())
            .setLastUpdateTs(rs.getTimestamp(5).toLocalDateTime())
            .build();
    };

    @Override
    public Long getNextId(Connection connection) {
        LOGGER.info("Get next id.");
        ResultSet rs = null;
        Statement statement = null;
        Long id = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(GET_NEXT_ID_SQL);
            while (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    @Override
    public Account getById(Connection connection, Long id) {
        LOGGER.info("Get by Id [{}]", id);
        Account account = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(GET_BY_ID_SQL);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                account = ACCOUNT_MAPPER.apply(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
            }
            if (null != statement) {
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
    public Account create(Connection connection, Account account) {
        LOGGER.info("Persist new account [{}]", account);
        Account persistedAccount;
        try (PreparedStatement statement = connection.prepareStatement(CREATE_SQL)) {
            Long newId = getNextId(connection);
            LOGGER.info("New account with id [{}].", newId);
            statement.setLong(1, newId);
            statement.setString(1, account.getName());
            statement.setBigDecimal(2, account.getBalance());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            persistedAccount = getById(connection, newId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persistedAccount;
    }

    @Override
    public Account update(Connection connection, Account account) {
        LOGGER.info("Update account [{}]", account);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, account.getName());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(4, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Update failure for [{}].", e.getMessage());
            throw new RuntimeException(e);
        }
        return account;
    }

    @Override
    public void deleteById(Connection connection, Long id) {
        LOGGER.info("delete account with id [{}]", id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Delete account failure for [{}].", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(Connection connection) {
        LOGGER.info("delete all accounts");
        try (Statement statement = connection.createStatement()) {
            statement.execute(DELETE_ALL_SQL);
        } catch (SQLException e) {
            LOGGER.error("Delete account failure for [{}].", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
