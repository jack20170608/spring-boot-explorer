package com.github.fangming.springboot.aop.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class AbstractRetryTemplate {

    private static final int DEFAULT_RETRY_TIME = 1;
    private static final long DEFAULT_SLEEP_TIME = 0L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractRetryTemplate.class);

    private int retryTime = DEFAULT_RETRY_TIME;
    private long sleepTime = DEFAULT_SLEEP_TIME;

    public AbstractRetryTemplate() {
    }

    public AbstractRetryTemplate(int retryTime) {
        this(retryTime, DEFAULT_RETRY_TIME);
    }

    public AbstractRetryTemplate(int retryTime, long sleepTime) {
        this.retryTime = retryTime;
        this.sleepTime = sleepTime;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public AbstractRetryTemplate setRetryTime(int retryTime) {
        if (retryTime < 0){
            throw new IllegalArgumentException("retry time should equal or bigger than 0");
        }
        this.retryTime = retryTime;
        return this;
    }

    public AbstractRetryTemplate setSleepTime(long sleepTime) {
        if (sleepTime < 0){
            throw new IllegalArgumentException("sleep time should equal or bigger than 0");
        }
        this.sleepTime = sleepTime;
        return this;
    }

    public abstract Object doBiz() throws Throwable;

    public Object execute() throws Throwable {
        for(int i = 0; i < retryTime; i++ ){
            logger.info("Retry with in [{}] time.", i);
            try {
                return doBiz();
            }catch (Throwable throwable){
                logger.error("Exception around: [{}].", throwable);
                Thread.sleep(sleepTime);
            }
        }
        throw new RuntimeException("Retry timeout....");
    }

    public Object submit(ExecutorService executorService) throws Throwable{
        return executorService.submit((Callable<Object>) () -> {
            try {
                return execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        });
    }

}
