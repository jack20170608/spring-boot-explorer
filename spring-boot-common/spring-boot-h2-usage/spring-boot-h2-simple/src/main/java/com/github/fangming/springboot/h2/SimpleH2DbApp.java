package com.github.fangming.springboot.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class SimpleH2DbApp implements CommandLineRunner {


    private static final Logger logger = LoggerFactory.getLogger(SimpleH2DbApp.class);

    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "org.h2.Driver";
    //File Db
//    public static final String DB_URL = "jdbc:h2:~/jack";
    //In memory DB
    public static final String DB_URL = "jdbc:h2:mem:test";

    //  Database credentials
    public static final String USER = "sa";
    public static final String PASS = "";

    public static void main(String[] args) {
        SpringApplication.run(SimpleH2DbApp.class, args);
    }


    static ThrowingConsumer<Connection, Exception> createTable = (Connection connection) -> {
        logger.info("Creating table in given database...");
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE REGISTRATION " +
                "(id INTEGER not NULL, " +
                " first VARCHAR(255), " +
                " last VARCHAR(255), " +
                " age INTEGER, " +
                " PRIMARY KEY ( id ))";
            statement.executeUpdate(sql);
        }catch (SQLException e){
            logger.error("Exception occurs");
            throw e;
        }finally {
            if (null != statement){
                statement.close();
            }
        }
        logger.info("Created table in given database...");
    };

    static ThrowingConsumer<Connection, Exception> insertData = (Connection connection) -> {
        logger.info("inserting data into table ....");
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO Registration " + "VALUES (102, 'Zaid', 'Khan', 30)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO Registration " + "VALUES(103, 'Sumit', 'Mittal', 28)";
            statement.executeUpdate(sql);
            logger.info("Inserted records into the table...");
        }catch (SQLException e){
            logger.error("Exception occurs");
            throw e;
        }finally {
            if (null != statement){
                statement.close();
            }
        }
        logger.info("inserted data to table in given database...");
    };


    static ThrowingConsumer<Connection, Exception> queryData = (Connection connection) -> {
        logger.info("querying data from table ....");
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT id, first, last, age FROM Registration";
            ResultSet rs = statement.executeQuery(sql);

            //Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
            logger.info("Queried records into the table...");
        }catch (SQLException e){
            logger.error("Exception occurs");
            throw e;
        }finally {
            if (null != statement){
                statement.close();
            }
        }
        logger.info("Queried data from table in given database...");
    };




    @Override
    public void run(String... args) throws Exception {
        Connection conn = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            logger.info("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3:  DB operation
            createTable.accept(conn);
            insertData.accept(conn);
            queryData.accept(conn);

            // STEP 4: Clean-up environment
            conn.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } //end finally try
        } //end try
        logger.info("Goodbye!");
    }
}
