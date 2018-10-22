package com.github.fangming.springboot.jdbc;

import org.junit.Test;

import static com.github.fangming.springboot.jdbc.transaction.STransactionDefinition.TransactionPropagation.PROPAGATION_REQUIRED;


public class STransactionDefinitionTest {

    @Test
    public void testTransactionPropagation(){
        System.out.println(PROPAGATION_REQUIRED.ordinal());
    }
}
