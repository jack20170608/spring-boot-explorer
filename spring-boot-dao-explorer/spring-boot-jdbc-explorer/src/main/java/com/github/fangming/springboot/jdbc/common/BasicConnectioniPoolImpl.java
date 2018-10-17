package com.github.fangming.springboot.jdbc.common;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class BasicConnectioniPoolImpl implements ConnectionPool{

    private static final Logger logger = LoggerFactory.getLogger(BasicConnectioniPoolImpl.class);
    private static final int INITIAL_POOL_SIZE = 5;

    private final int poolSize;
    private final String url;
    private final String driverClass;
    private final String user;
    private final String password;
    private final List<Connection> connectionList;
    private final List<Connection> usedConnectionList;

    @Autowired
    public BasicConnectioniPoolImpl(@Value("${basic.pool.url}") String url, @Value("${basic.pool.driver:org.h2.Driver}") String driverClass,
                                    @Value("${basic.pool.user:jack}") String user, @Value("${basic.pool.password:}") String password,
                                    @Value("${basic.pool.poolSize:5}") int poolSize) {
        this.url = url;
        this.driverClass = driverClass;
        this.user = user;
        this.password = password;
        this.poolSize = poolSize <= 0 ? INITIAL_POOL_SIZE : poolSize;
        this.connectionList = Lists.newArrayList();
        this.usedConnectionList = Lists.newArrayList();

        try {
            Class.forName(driverClass);
            for(int i =0 ; i < this.poolSize ; i++){
                Connection conn = createConnection(this.url, this.user, this.password);
                conn.setAutoCommit(false);
                connectionList.add(conn);
            }
        }catch (SQLException | ClassNotFoundException e){
            logger.error("DB connection pool initial failure.");
            e.printStackTrace();
        }
    }

    private Connection createConnection(String url,String user, String password) throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public synchronized Connection getConnection() {
        Connection connection = connectionList.remove(connectionList.size() - 1);
        usedConnectionList.add(connection);
        return connection;
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        connectionList.add(connection);
        return usedConnectionList.remove(connection);
    }
}
