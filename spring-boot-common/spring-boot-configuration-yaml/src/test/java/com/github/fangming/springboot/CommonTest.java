package com.github.fangming.springboot;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class CommonTest {


    @Test
    public void testList2String(){
        List<String> stringList = Lists.newArrayList("jack", "lucy", "jemmi");
        System.out.println(stringList.toString());
    }
}
