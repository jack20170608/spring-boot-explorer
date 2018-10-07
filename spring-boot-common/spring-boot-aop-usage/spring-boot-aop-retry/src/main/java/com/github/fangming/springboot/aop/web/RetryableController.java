package com.github.fangming.springboot.aop.web;

import com.github.fangming.springboot.aop.service.MockedFailureServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryableController {

    private static final Logger logger = LoggerFactory.getLogger(RetryableController.class);

    private final MockedFailureServiceImpl failureService;

    @Autowired
    public RetryableController(MockedFailureServiceImpl failureService) {
        this.failureService = failureService;
    }

    @GetMapping(value = "/{a}/{b}",produces = "application/json")
    public Integer calculate(@PathVariable("a") int a, @PathVariable("b") int b) {
        return failureService.calculate(a, b);
    }

}
