package com.github.fangming.springboot.jdbc.common;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();

    boolean releaseConnection(Connection connection);

}
