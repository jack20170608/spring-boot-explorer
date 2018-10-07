package com.github.fangming.springboot.aop.retry;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleRetryDot {

    int count() default 0;


    /**
     * retry interval time (ms)
     * @return
     */
    long sleep() default 0L;

    /**
     * support async retry style
     * @return
     */
    boolean asyn() default false;
}
