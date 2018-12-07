package com.github.fangming.springboot.jdbc.dao.impl;

import com.github.fangming.springboot.jdbc.dao.AbstractDaoImpl;
import com.github.fangming.springboot.jdbc.dao.AccountDao;
import com.github.fangming.springboot.jdbc.model.Account;
import com.github.fangming.springboot.jdbc.pool.SimpleConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    @Override
    public Account getById(Long id) {
        LOGGER.info("Get by Id [{}]", id);
        Connection connection = getConnection();
        Account account = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_BY_ID_SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                account = new Account(rs.getLong(1));
                account.setName(rs.getString(2));
                account.setBalance(rs.getBigDecimal(3));
                account.setInsertDt(rs.getDate(4).toLocalDate());
                account.setLastUpdateTs(rs.getTimestamp(5).toLocalDateTime());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            if (null != statement){
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return account;
    }

    @Override
    public Collection<Account> getAll() {
        return null;
    }

    @Override
    public Account create(Account account) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
