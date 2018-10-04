package com.github.fangming.springboot.aop.caching;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleDataCacheable {
}
