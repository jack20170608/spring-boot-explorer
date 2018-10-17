package com.github.fangming.springboot.jdbc.web;

import com.github.fangming.springboot.jdbc.common.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
@RequestMapping("jdbc-meta")
public class JdbcMetaDataController {

    private final ConnectionPool connectionPool;

    @Autowired
    public JdbcMetaDataController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @GetMapping
    public String showDBMetaData(){
        try {
            Connection connection = connectionPool.getConnection();
        }catch(Exception e){
            
        }
    }
}
