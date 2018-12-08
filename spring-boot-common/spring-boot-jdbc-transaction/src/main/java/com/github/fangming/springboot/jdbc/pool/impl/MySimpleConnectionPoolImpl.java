package com.github.fangming.springboot.jdbc.pool.impl;

import com.github.fangming.springboot.jdbc.pool.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@Component("simplePool")
public class MySimpleConnectionPoolImpl implements Pool<Connection> {

    private final String jdbcDriver ;
    private final String url ;
    private final String userName ;
    private final String password ;

    private final int initCount;
    private final int maxCount;

    private AtomicInteger currentCount = new AtomicInteger(0);
    private LinkedList<Connection> connectionsPool = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(MySimpleConnectionPoolImpl.class);

    @Autowired
    public MySimpleConnectionPoolImpl(@Value("${SimpleConnectionPool.driver}") String jdbcDriver
        , @Value("${SimpleConnectionPool.url}") String url
        , @Value("${SimpleConnectionPool.user}") String userName
        , @Value("${SimpleConnectionPool.password}") String password
        , @Value("${SimpleConnectionPool.initCount}")int initCount
        , @Value("${SimpleConnectionPool.maxCount}") int maxCount) {
        this.jdbcDriver = jdbcDriver;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.initCount = initCount;
        this.maxCount = maxCount;

        try {
            Class.forName(this.jdbcDriver);
        } catch (ClassNotFoundException e) {
            logger.error("Driver class not found.");
            throw new RuntimeException(e);
        }
        for (int i = 0; i < this.initCount; i++) {
            try {
                this.connectionsPool.addLast(this.createConnection());
                this.currentCount.addAndGet(1);
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private Connection createConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(false);
        }catch (SQLException e){
            logger.error("Create db connection failure.");
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public Connection get() {
        synchronized (connectionsPool) {
            if (this.connectionsPool.size() > 0)
                return this.connectionsPool.removeFirst();

            if (this.currentCount.get() < maxCount) {
                this.currentCount.addAndGet(1);
                return this.createConnection();
            }

            throw new RuntimeException("No Connection available now.");
        }
    }

    @Override
    public void release(Connection conn) {
        this.connectionsPool.addLast(conn);
    }

    @Override
    public void shutdown() {
    }


}
	

