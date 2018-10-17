package com.github.fangming.springboot.jdbc.web;

import com.github.fangming.springboot.jdbc.common.ConnectionPool;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Map;

import static java.sql.Connection.*;

@RestController
@RequestMapping("jdbc-meta")
public class JdbcMetaDataController {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMetaDataController.class);

    private final ConnectionPool connectionPool;

    public enum TransactionIsolationLevel {
        TRANSACTION_NONE(0),
        TRANSACTION_READ_UNCOMMITTED(1),
        TRANSACTION_READ_COMMITTED(2),
        TRANSACTION_REPEATABLE_READ(4),
        TRANSACTION_SERIALIZABLE(8);

        private final int value;

        static Map<Integer, TransactionIsolationLevel> dataMap = Maps.newHashMap();

        TransactionIsolationLevel(int value){
            this.value = value;
        }

        public static TransactionIsolationLevel getByLevel(int value){
            return dataMap.get(value);
        }
    }

    @Autowired
    public JdbcMetaDataController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @GetMapping
    public String showDBMetaData(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = connectionPool.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            stringBuilder.append("DB product name: ")
                .append(metaData.getDatabaseProductName()).append("<br/>")
                .append("DB product version:").append(metaData.getDatabaseProductVersion()).append("<br/>")
                .append("----------------------transaction isolation level----------------------------------").append("<br/>")
                .append("DB's default transaction isolation level is :").append(metaData.getDefaultTransactionIsolation()).append("<br/>")
                .append("TRANSACTION_NONE supported: ").append(metaData.supportsTransactionIsolationLevel(TRANSACTION_NONE)).append("<br/>")
                .append("TRANSACTION_READ_UNCOMMITTED supported: ").append(metaData.supportsTransactionIsolationLevel(TRANSACTION_READ_UNCOMMITTED)).append("<br/>")
                .append("TRANSACTION_READ_COMMITTED supported: ").append(metaData.supportsTransactionIsolationLevel(TRANSACTION_READ_COMMITTED)).append("<br/>")
                .append("TRANSACTION_REPEATABLE_READ supported: ").append(metaData.supportsTransactionIsolationLevel(TRANSACTION_REPEATABLE_READ)).append("<br/>")
                .append("TRANSACTION_SERIALIZABLE supported: ").append(metaData.supportsTransactionIsolationLevel(TRANSACTION_SERIALIZABLE)).append("<br/>")
                .append("----------------------Others ----------------------------------").append("<br/>")
                .append("Max Table Name: ").append(metaData.getMaxTableNameLength()).append("<br/>")
                .append("Max Connections: ").append(metaData.getMaxConnections()).append("<br/>");

        }catch(Exception e){
            logger.error("Connection geting failure.....");
        }
        return stringBuilder.toString();
    }
}
