package com.github.fangming.springboot.jdbc.pool;

public interface Pool<T> {

    T get();

    void release(T t);

    void shutdown();

    public static interface Validator <T> {

        boolean isValid(T t);

        void invalidate(T t);
    }
}
