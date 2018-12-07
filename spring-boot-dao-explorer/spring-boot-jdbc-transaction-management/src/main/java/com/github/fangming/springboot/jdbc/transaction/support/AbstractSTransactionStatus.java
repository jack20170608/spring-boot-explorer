package com.github.fangming.springboot.jdbc.transaction.support;

import com.github.fangming.springboot.jdbc.transaction.SSavepointManager;
import com.github.fangming.springboot.jdbc.transaction.STransactionException;
import com.github.fangming.springboot.jdbc.transaction.STransactionStatus;

public class AbstractSTransactionStatus implements STransactionStatus{

    private boolean rollbackOnly = false;

    private boolean completed = false;

    private Object savepoint;



    @Override
    public boolean isNewTransaction() {
        return false;
    }

    @Override
    public boolean hasSavepoint() {
        return (this.savepoint != null);
    }

    @Override
    public void setRollbackOnly() {
        this.rollbackOnly = true;
    }

    public boolean isLocalRollbackOnly() {
        return this.rollbackOnly;
    }

    public boolean isGlobalRollbackOnly() {
        return false;
    }

    @Override
    public boolean isRollbackOnly() {
        return (isLocalRollbackOnly() || isGlobalRollbackOnly());
    }

    public void setCompleted() {
        this.completed = true;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    protected void setSavepoint(Object savepoint) {
        this.savepoint = savepoint;
    }

    public Object getSavepoint() {
        return savepoint;
    }

    public void createAndHoldSavepoint() throws STransactionException {
        setSavepoint(getSavepointManager().createSavepoint());
    }

    public void rollbackToHeldSavepoint() throws STransactionException {
        if (!hasSavepoint()) {
            throw new STransactionException("No savepoint associated with current transaction");
        }
        getSavepointManager().rollbackToSavepoint(getSavepoint());
        setSavepoint(null);
    }

    public void releaseHeldSavepoint() throws STransactionException {
        if (!hasSavepoint()) {
            throw new STransactionException("No savepoint associated with current transaction");
        }
        getSavepointManager().releaseSavepoint(getSavepoint());
        setSavepoint(null);
    }

    public Object createSavepoint() throws STransactionException {
        return getSavepointManager().createSavepoint();
    }

    public void rollbackToSavepoint(Object savepoint) throws STransactionException {
        getSavepointManager().rollbackToSavepoint(savepoint);
    }

    public void releaseSavepoint(Object savepoint) throws STransactionException {
        getSavepointManager().releaseSavepoint(savepoint);
    }

    protected SSavepointManager getSavepointManager() {
        throw new RuntimeException("This transaction does not support savepoints");
    }

}
