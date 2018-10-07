package com.github.fangming.springboot.aop.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class SimpleRetryAdvice {

    private ExecutorService executorService = new ThreadPoolExecutor(3, 5, 1,
        TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

    private Logger logger = LoggerFactory.getLogger(SimpleRetryAdvice.class);

    @Around(value = "within(com.github.fangming.springboot..*) && @annotation(simpleJackRetryDot)")
    public Object executeRetry(ProceedingJoinPoint proceedingJoinPoint, SimpleRetryDot simpleRetryDot) throws Throwable {
        logger.info("Execute retry method with times [{}], sleep time [{}], async [{}].", simpleRetryDot.count(), simpleRetryDot.sleep(), simpleRetryDot.asyn());
        AbstractRetryTemplate retryTemplate = new AbstractRetryTemplate() {
            @Override
            public Object doBiz() throws Throwable {
                return proceedingJoinPoint.proceed();
            }
        }.setRetryTime(simpleRetryDot.count()).setSleepTime(simpleRetryDot.sleep());
        if (simpleRetryDot.asyn()){
            return retryTemplate.submit(executorService);
        }else {
            return retryTemplate.execute();
        }
    }

}
