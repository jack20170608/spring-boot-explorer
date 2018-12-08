package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.pool.Pool;

import java.sql.Connection;


public abstract class AbstractDaoImpl {

    private final Pool<Connection> pool;

    public Pool<Connection> getPool() {
        return pool;
    }

    public AbstractDaoImpl(Pool<Connection> pool) {
        this.pool = pool;
    }
}
