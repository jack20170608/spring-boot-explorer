package com.github.fangming.springboot.aop.service;

import com.github.fangming.springboot.aop.retry.SimpleRetryDot;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockedFailureServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MockedFailureServiceImpl.class);

    @SimpleRetryDot(count = 10, sleep = 1000L, asyn = true)
    public int calculate(int a, int b) {
        int randomInt = RandomUtils.nextInt();
        if (randomInt % 3 != 0) {
            logger.info("Random integer is [{}].", randomInt);
            throw new UnsupportedOperationException("internal exception occures");
        }
        return a + b;
    }
}
