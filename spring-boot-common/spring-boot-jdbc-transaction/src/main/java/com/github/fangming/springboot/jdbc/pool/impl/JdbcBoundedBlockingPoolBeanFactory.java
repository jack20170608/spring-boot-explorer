package com.github.fangming.springboot.jdbc.pool.impl;

import com.github.fangming.springboot.jdbc.pool.Pool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component("blockingPool")
public class JdbcBoundedBlockingPoolBeanFactory implements FactoryBean<Pool<Connection>> {

    @Value("${blocking-pool.size}")
    private int poolSize;

    @Autowired
    private JDBCConnectionFactory jdbcConnectionFactory;

    @Override
    public Pool<Connection> getObject() throws Exception {
        return new BoundedBlockingPool<>(
            this.poolSize ,
            new JDBCConnectionValidator(),
            jdbcConnectionFactory
        );
    }

    @Override
    public Class<Pool> getObjectType() {
        return Pool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
