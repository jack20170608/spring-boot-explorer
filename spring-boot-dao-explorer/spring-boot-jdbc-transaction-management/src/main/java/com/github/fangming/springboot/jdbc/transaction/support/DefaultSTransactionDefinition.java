package com.github.fangming.springboot.jdbc.transaction.support;

import com.github.fangming.springboot.jdbc.transaction.STransactionDefinition;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    public void setPropagationBehavior(TransactionPropagation propagationBehavior) {
        this.propagationBehavior = propagationBehavior;
    }

    public void setIsolationLevel(TransactionIsolationLevel isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final void setTimeout(int timeout) {
        if (timeout < TIMEOUT_DEFAULT) {
            throw new IllegalArgumentException("Timeout must be a positive integer or TIMEOUT_DEFAULT");
        }
        this.timeout = timeout;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultSTransactionDefinition)) return false;
        DefaultSTransactionDefinition that = (DefaultSTransactionDefinition) o;
        return timeout == that.timeout &&
            readOnly == that.readOnly &&
            propagationBehavior == that.propagationBehavior &&
            isolationLevel == that.isolationLevel &&
            Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(propagationBehavior, isolationLevel, timeout, readOnly, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("propagationBehavior", propagationBehavior)
            .add("isolationLevel", isolationLevel)
            .add("timeout", timeout)
            .add("readOnly", readOnly)
            .add("name", name)
            .toString();
    }
}
