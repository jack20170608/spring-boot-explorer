package com.github.fangming.springboot.jdbc.pool;

import java.sql.Connection;

public interface SimpleConnectionPool {

    Connection getConnection();
    void releaseConnection(Connection conn);
}
