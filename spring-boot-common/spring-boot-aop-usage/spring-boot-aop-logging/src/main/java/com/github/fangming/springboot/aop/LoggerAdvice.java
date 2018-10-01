package com.github.fangming.springboot.aop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAdvice {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //Should use the target class instead of the aspect class
    private Logger getAcrualLogger(Class targetClass){
        return LoggerFactory.getLogger(targetClass);
    }

    @Before("within(com.github.fangming.springboot..*) && @annotation(loggerManager)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManager loggerManager){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);

        logger.info("execute [{}] start", loggerManager.description());
        logger.info("Method signature [{}].", joinPoint.getSignature().toLongString());
        logger.info("Input parameters [{}].", parseParames(joinPoint.getArgs()));
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning("within(com.github.fangming.springboot..*) && @annotation(loggerManager1)")
    public void addAfterRetruningLogger(JoinPoint joinPoint, LoggerManager loggerManager1){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        logger.info("execute [{}] end.", loggerManager1.description());
    }

    //Take care about the @annotation parameter should the same to the method parameter
    @AfterThrowing(value = "within(com.github.fangming.springboot..*) && @annotation(loggerManager2)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManager loggerManager2, Exception ex){
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        logger.info("execute [{}] throw [{}] exception.", loggerManager2.description(), ex);
    }

    @Around(value = "within(com.github.fangming.springboot..*) && @annotation(LoggerManager)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Class className = joinPoint.getTarget().getClass();
        Logger logger = getAcrualLogger(className);
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime.get();
        logger.info("[{}] executed in [{}] ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    private String parseParames(Object[] parames){
        if (null == parames || parames.length <= 0){
            return "";
        }
        StringBuffer param = new StringBuffer();
        Arrays.stream(parames).forEach((Object obj) -> param.append(ToStringBuilder.reflectionToString(obj)));
        return param.toString();
    }
}
