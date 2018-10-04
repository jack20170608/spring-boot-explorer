package com.github.fangming.springboot.aop.logging;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManager {

    String description();
}
