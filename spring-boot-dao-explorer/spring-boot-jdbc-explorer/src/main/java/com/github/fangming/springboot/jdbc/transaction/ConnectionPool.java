package com.github.fangming.springboot.jdbc.transaction;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();

    boolean releaseConnection(Connection connection);

}
