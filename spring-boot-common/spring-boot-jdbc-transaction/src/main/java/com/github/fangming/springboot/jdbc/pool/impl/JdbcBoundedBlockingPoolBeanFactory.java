package com.github.fangming.springboot.jdbc.pool.impl;

import com.github.fangming.springboot.jdbc.pool.Pool;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component("blockingPool")
public class JdbcBoundedBlockingPoolBeanFactory implements FactoryBean<Pool<Connection>> {

    @Value("${blockingPool.size}")
    private int poolSize;

    @Value("$(blockingPool.driverClass)")
    private String driverClass;

    @Value("${blockingPool.url}")
    private String url;

    @Value("${blockingPool.user}")
    private String userName;

    @Value("${blockingPool.password}")
    private String password;

    @Override
    public Pool<Connection> getObject() throws Exception {
        return new BoundedBlockingPool<>(
            this.poolSize ,
            new JDBCConnectionValidator(),
            new JDBCConnectionFactory(this.driverClass, this.url, userName, password)
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
