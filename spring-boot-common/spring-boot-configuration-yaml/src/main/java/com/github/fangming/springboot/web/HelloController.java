package com.github.fangming.springboot.web;

import com.github.fangming.springboot.service.DBConnectionFactory;
import com.github.fangming.springboot.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final HelloWorldService helloWorldService;
    private final DBConnectionFactory dbConnectionFactory;

    @Autowired
    public HelloController(HelloWorldService helloWorldService, DBConnectionFactory dbConnectionFactory) {
        this.helloWorldService = helloWorldService;
        this.dbConnectionFactory = dbConnectionFactory;
    }

    @GetMapping("/")
    public String index(){
        String returnMsg = helloWorldService.sayHello();
        logger.info(returnMsg);
        return returnMsg;
    }

    @GetMapping("/db")
    public String dbInfo(){
        return dbConnectionFactory.toString();
    }
}
