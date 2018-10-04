package com.github.fangming.springboot.service;

import com.github.fangming.springboot.aop.caching.SimpleDataCacheable;
import com.github.fangming.springboot.model.CalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ComplicatedCalculationService {

    private static final Logger logger = LoggerFactory.getLogger(ComplicatedCalculationService.class);

    @SimpleDataCacheable
    public CalculationResult calculate(int a, int b) {
        logger.info("Calculating " + a + " + " + b);
        try {
            // pretend this is an expensive and complicated operation
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error("Something went wrong...", e);
        }
        return new CalculationResult(a, b, a + b);
    }
}
