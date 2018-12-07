package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.pool.SimpleConnectionPool;

import java.sql.Connection;

public abstract class AbstractDaoImpl {

    private final SimpleConnectionPool simpleConnectionPool;

    public AbstractDaoImpl(SimpleConnectionPool simpleConnectionPool) {
        this.simpleConnectionPool = simpleConnectionPool;
    }

    protected Connection getConnection(){
        return simpleConnectionPool.getConnection();
    }
}
