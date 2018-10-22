package com.github.fangming.springboot.jdbc.transaction.support;

import com.github.fangming.springboot.jdbc.transaction.STransactionDefinition;
import org.springframework.lang.Nullable;

import java.io.Serializable;

import static com.github.fangming.springboot.jdbc.transaction.STransactionDefinition.TransactionPropagation.PROPAGATION_REQUIRED;

public class DefaultSTransactionDefinition implements STransactionDefinition, Serializable{

    private TransactionPropagation propagationBehavior = PROPAGATION_REQUIRED;

    private TransactionIsolationLevel isolationLevel = TransactionIsolationLevel.ISOLATION_DEFAULT;

    private int timeout = TIMEOUT_DEFAULT;

    private boolean readOnly = false;

    private String name;

    public DefaultSTransactionDefinition(TransactionPropagation propagationBehavior, TransactionIsolationLevel isolationLevel, int timeout, boolean readOnly, String name) {
        this.propagationBehavior = propagationBehavior;
        this.isolationLevel = isolationLevel;
        this.timeout = timeout;
        this.readOnly = readOnly;
        this.name = name;
    }


    @Override
    public TransactionPropagation getPropagationBehavior() {
        return this.propagationBehavior;
    }

    @Override
    public TransactionIsolationLevel getIsolationLevel() {
        return this.isolationLevel;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }
}
