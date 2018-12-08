package com.github.fangming.springboot.jdbc.pool;

public interface ObjectFactory<T> {
    T createNew();
}