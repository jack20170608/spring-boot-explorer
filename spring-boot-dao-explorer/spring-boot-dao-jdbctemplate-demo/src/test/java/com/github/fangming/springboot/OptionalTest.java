package com.github.fangming.springboot;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void testOptional(){
        System.out.println(Optional.empty().orElse(0));
        System.out.println(Optional.of("hi").orElse("hello"));

    }
}
