package com.github.fangming.springboot.jdbc.pool.impl;

import com.github.fangming.springboot.jdbc.pool.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class JDBCConnectionFactory implements ObjectFactory<Connection> {

    private final String connectionURL;

    private final String userName;

    private final String password;

    public JDBCConnectionFactory(@Value("${blocking-pool.url}") String connectionURL
        , @Value("${blocking-pool.driver}") String driver
        , @Value("${blocking-pool.user}") String userName
        , @Value("${blocking-pool.password}") String password) {
        super();
        this.connectionURL = connectionURL;
        this.userName = userName;
        this.password = password;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ce) {
            throw new IllegalArgumentException("Unable to find driver in classpath", ce);
        }
    }

    public Connection createNew() {
        try {
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException se) {
            throw new IllegalArgumentException("Unable to create new connection", se);
        }
    }
}