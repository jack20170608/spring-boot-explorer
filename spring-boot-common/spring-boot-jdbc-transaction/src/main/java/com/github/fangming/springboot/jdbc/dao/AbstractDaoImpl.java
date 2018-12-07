package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.pool.SimpleConnectionPool;


public abstract class AbstractDaoImpl {

    private final SimpleConnectionPool simpleConnectionPool;

    public SimpleConnectionPool getSimpleConnectionPool() {
        return simpleConnectionPool;
    }

    public AbstractDaoImpl(SimpleConnectionPool simpleConnectionPool) {
        this.simpleConnectionPool = simpleConnectionPool;
    }


}
