package com.github.fangming.springboot.aop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RetryableController {

    private static final Logger logger = LoggerFactory.getLogger(RetryableController.class);

    @GetMapping


}
