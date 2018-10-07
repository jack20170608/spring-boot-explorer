package com.github.fangming.springboot.aop.service;

import com.github.fangming.springboot.aop.retry.SimpleRetryDot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockedFailureServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MockedFailureServiceImpl.class);

    @SimpleRetryDot
    public int calculate(int a, int b){
        for(int i = 0; i < Math.abs(a); i++){
            throw new UnsupportedOperationException("internal exception occures");
        }
        return a + b;
    }
}
