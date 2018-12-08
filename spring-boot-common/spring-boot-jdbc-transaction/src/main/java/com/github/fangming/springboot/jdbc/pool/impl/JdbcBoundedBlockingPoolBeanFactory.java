package com.github.fangming.springboot.jdbc.pool.impl;

import com.github.fangming.springboot.jdbc.pool.Pool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class JdbcBoundedBlockingPoolBeanFactory implements FactoryBean<Pool<Connection>> {


    private final int poolSize;
    private final String driverClass;
    private final String url;
    private final String userName;
    private final String password;

    public JdbcBoundedBlockingPoolBeanFactory(@Value("${blockingPool.size}") int poolSize
        ,@Value("$(blockingPool.driverClass)") String driverClass
        ,@Value("${blockingPool.url}") String url
        ,@Value("${blockingPool.user}")String userName
        ,@Value("${blockingPool.password}")String password) {
        this.poolSize = poolSize;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Pool<Connection> getObject() throws Exception {
        Pool <Connection> pool =
            new BoundedBlockingPool <Connection> (
                this.poolSize ,
                new JDBCConnectionValidator(),
                new JDBCConnectionFactory(this.driverClass, this.url, userName, password)
            );

        return pool;
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
