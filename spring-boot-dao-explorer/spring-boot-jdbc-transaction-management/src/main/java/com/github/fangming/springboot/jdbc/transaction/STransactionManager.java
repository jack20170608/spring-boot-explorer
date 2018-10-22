package com.github.fangming.springboot.jdbc.transaction;

import org.springframework.lang.Nullable;

public interface STransactionManager {


    STransactionStatus getTransaction(@Nullable STransactionDefinition definition) throws STransactionException;

    void commit(STransactionStatus status) throws STransactionException;


    void rollback(STransactionStatus status) throws STransactionException;
}
