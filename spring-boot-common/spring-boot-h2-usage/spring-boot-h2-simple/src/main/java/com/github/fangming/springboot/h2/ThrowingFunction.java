package com.github.fangming.springboot.h2;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T t) throws E;
}
