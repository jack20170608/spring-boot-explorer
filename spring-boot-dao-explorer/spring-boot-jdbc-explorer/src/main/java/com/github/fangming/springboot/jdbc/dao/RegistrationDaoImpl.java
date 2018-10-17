package com.github.fangming.springboot.jdbc.dao;

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
import java.util.List;

@Component
public class RegistrationDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationDaoImpl.class);

    private final ConnectionPool connectionPool;

    @Autowired
    public RegistrationDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    public void initTable(){
       Connection conn = connectionPool.getConnection();
        Statement statement = null;
       try {
           logger.info("Creating table in given database...");
           String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION " +
               "(id NUMBER not NULL, " +
               " first VARCHAR(255), " +
               " last VARCHAR(255), " +
               " age INTEGER, " +
               " PRIMARY KEY ( id ))";
           statement = conn.createStatement();
           statement.executeUpdate(sql);
           logger.info("Created table in given database...");
       } catch (Exception e) {
           logger.error("Initial Table failure....");
           e.printStackTrace();
       } finally {
           connectionPool.releaseConnection(conn);
           if (null != statement){
               try {
                   statement.close();
               } catch (SQLException e) {
               }
           }
       }
   }

   public void create(Registration registration){
       Connection conn = connectionPool.getConnection();
       PreparedStatement statement = null;
       logger.info("Begin create registration.");
       try {
           String sql = "insert into REGISTRATION (ID, FIRST, LAST, AGE) values (?, ? , ? , ?);";
           statement = conn.prepareStatement(sql);
           statement.setLong(1, registration.getId());
           statement.setString(2, registration.getFirstName());
           statement.setString(3, registration.getLastName());
           statement.setInt(4, registration.getAge());
           statement.executeUpdate();
           conn.commit();
           logger.info("committed successfully.");
       } catch (Exception e) {
           logger.error("insert into Table failure....");
           e.printStackTrace();
           try {
               conn.rollback();
           } catch (SQLException e1) {
           }
           throw new RuntimeException("DAO create operation failure.");
       } finally {
           connectionPool.releaseConnection(conn);
           if (null != statement){
               try {
                   statement.close();
               } catch (SQLException e) {
               }
           }
       }
   }

   public int update(Registration registration) {
       Connection conn = connectionPool.getConnection();
       PreparedStatement statement = null;
       int result = 0;
       try {
           String sql = "update REGISTRATION set FIRST = :first, LAST = :last, AGE = :age where id = :id ;";
           statement = conn.prepareStatement(sql);
           statement.setLong(4, registration.getId());
           statement.setString(1, registration.getFirstName());
           statement.setString(2, registration.getLastName());
           statement.setInt(3, registration.getAge());
           result = statement.executeUpdate();
           conn.commit();
       } catch (Exception e) {
           logger.error("update operation failure....");
           e.printStackTrace();
           try {
               conn.rollback();
           } catch (SQLException e1) {
           }
           throw new RuntimeException("DAO update operation failure.");
       } finally {
           connectionPool.releaseConnection(conn);
           if (null != statement){
               try {
                   statement.close();
               } catch (SQLException e) {
               }
           }
       }
       return result;
   }

   public List<Registration> getAll(){
       return null;
   }
}
