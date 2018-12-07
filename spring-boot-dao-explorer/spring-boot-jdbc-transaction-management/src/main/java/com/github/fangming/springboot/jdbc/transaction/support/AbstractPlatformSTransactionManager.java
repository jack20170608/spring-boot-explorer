package com.github.fangming.springboot.jdbc.transaction.support;

import com.github.fangming.springboot.jdbc.transaction.STransactionDefinition;
import com.github.fangming.springboot.jdbc.transaction.STransactionException;
import com.github.fangming.springboot.jdbc.transaction.STransactionManager;
import com.github.fangming.springboot.jdbc.transaction.STransactionStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class AbstractPlatformSTransactionManager implements STransactionManager, Serializable{

    public static enum SynchronizationLevel {

    }

    @Override
    public STransactionStatus getTransaction(@Nullable STransactionDefinition definition) throws STransactionException {
        return null;
    }

    @Override
    public void commit(STransactionStatus status) throws STransactionException {

    }

    @Override
    public void rollback(STransactionStatus status) throws STransactionException {

    }
}
