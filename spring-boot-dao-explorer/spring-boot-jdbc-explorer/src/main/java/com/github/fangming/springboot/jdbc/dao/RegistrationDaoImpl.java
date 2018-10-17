package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.ThrowingConsumer;
import com.github.fangming.springboot.jdbc.common.ConnectionPool;
import com.github.fangming.springboot.jdbc.model.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class RegistrationDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationDaoImpl.class);

    private final ConnectionPool connectionPool;

    @Autowired
    public RegistrationDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static ThrowingConsumer<Statement, Exception> createTable = (Statement statement) -> {
        logger.info("Creating table in given database...");
        String sql = "CREATE TABLE REGISTRATION " +
            "(id INTEGER not NULL, " +
            " first VARCHAR(255), " +
            " last VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
        logger.info("Created table in given database...");
    };

    private static ThrowingConsumer<Statement, Exception> insertData = (Statement statement) -> {
        logger.info("inserting data into table ....");
        String sql = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)";
        statement.executeUpdate(sql);
        logger.info("Inserted records into the table...");
    };

    private static ThrowingConsumer<Connection, Exception> jdbcOperationWrapper(ThrowingConsumer<Statement, Exception> originOperation) {
        return (Connection connection) -> {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                originOperation.accept(statement);
            } catch (SQLException e) {
                logger.error("Exception occurs");
                throw e;
            } finally {
                if (null != statement) {
                    statement.close();
                }
            }
        };
    }

    private static ThrowingConsumer<Connection, Exception> preparedJdbcOperationWrapper(ThrowingConsumer<PreparedStatement, Exception> originOperation, String sql) {
        return (Connection connection) -> {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                originOperation.accept(statement);
            } catch (SQLException e) {
                logger.error("Exception occurs");
                throw e;
            } finally {
                if (null != statement) {
                    statement.close();
                }
            }
        };
    }

    public void initTable(){
       Connection conn = connectionPool.getConnection();
       try {
           jdbcOperationWrapper(createTable).accept(conn);
       } catch (Exception e) {
           logger.error("Initial Table failure....");
           e.printStackTrace();
       } finally {
           connectionPool.releaseConnection(conn);
       }
   }

   public Registration insert(Registration registration){
       Connection conn = connectionPool.getConnection();
       try {
           preparedJdbcOperationWrapper(insertData).accept(conn);
       } catch (Exception e) {
           logger.error("Initial Table failure....");
           e.printStackTrace();
       } finally {
           connectionPool.releaseConnection(conn);
       }
   }
}
