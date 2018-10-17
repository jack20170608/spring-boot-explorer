package com.github.fangming.springboot.jdbc.dao;

import com.github.fangming.springboot.jdbc.model.Registration;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class RegistrationDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationDaoImpl.class);

    public void initTable(final Connection conn) {
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
            throw new RuntimeException("Initial table failure....", e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void create(Connection conn, Registration registration) {
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
        } catch (Exception e) {
            logger.error("insert into Table failure....");
            throw new RuntimeException("DAO create operation failure.", e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public Registration getById(Connection conn, Long id){
        PreparedStatement statement = null;
        Registration result = null;
        try {
            String sql = "select id, first, last, age from REGISTRATION  where id = ? ;";
            statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                // Retrieve by column name
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                result = new Registration(id, first, last, age);
            }
            rs.close();
        } catch (Exception e) {
            logger.error("update operation failure....");
            throw new RuntimeException("DAO update operation failure.", e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }
        return result;
    }

    public void update(Connection conn, Registration registration) {
        PreparedStatement statement = null;
        try {
            String sql = "update REGISTRATION set FIRST = ?, LAST = ?, AGE = ? where id = ?;";
            statement = conn.prepareStatement(sql);
            statement.setLong(4, registration.getId());
            statement.setString(1, registration.getFirstName());
            statement.setString(2, registration.getLastName());
            statement.setInt(3, registration.getAge());
            statement.executeUpdate();
        } catch (Exception e) {
            logger.error("update operation failure....");
            throw new RuntimeException("DAO update operation failure.", e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public List<Registration> getAll(Connection conn) {
        Statement stmt = null;
        List<Registration> result = Lists.newArrayList();
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id, first, last, age FROM Registration";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Retrieve by column name
                Long id = rs.getLong("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                result.add(new Registration(id, first, last, age));
            }
            rs.close();
        } catch (SQLException e) {
            logger.error("query operation failure....");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }


}
