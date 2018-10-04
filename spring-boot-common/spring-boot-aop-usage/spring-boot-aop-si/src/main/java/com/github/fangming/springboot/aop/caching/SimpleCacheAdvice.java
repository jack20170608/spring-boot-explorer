package com.github.fangming.springboot.aop.caching;

import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class SimpleCacheAdvice {

    private Logger logger = LoggerFactory.getLogger(SimpleCacheAdvice.class);
    private Map<String, Object> cache = Maps.newConcurrentMap();

    @Around(value = "within(com.github.fangming.springboot..*) && @annotation(SimpleDataCacheable)")
    public Object aroundCachedMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.debug("Execution of Cacheable method catched");

        // generate the key under which cached value is stored
        // will look like caching.aspectj.Calculator.sum(Integer=1;Integer=2;)
        StringBuilder keyBuff = new StringBuilder();

        // append name of the class
        keyBuff.append(proceedingJoinPoint.getTarget().getClass().getName());

        // append name of the method
        keyBuff.append(".").append(proceedingJoinPoint.getSignature().getName());

        keyBuff.append("(");
        // loop through cacheable method arguments
        for (final Object arg : proceedingJoinPoint.getArgs()) {
            // append argument type and value
            keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
        }
        keyBuff.append(")");
        String key = keyBuff.toString();

        logger.debug("Key = " + key);
        Object result = cache.get(key);
        if (result == null) {
            logger.debug("Result not yet cached. Must be calculated...");
            result = proceedingJoinPoint.proceed();
            logger.info("Storing calculated value '" + result + "' to cache");
            cache.put(key, result);
        } else {
            logger.debug("Result '" + result + "' was found in cache");
        }

        return result;
    }

}
