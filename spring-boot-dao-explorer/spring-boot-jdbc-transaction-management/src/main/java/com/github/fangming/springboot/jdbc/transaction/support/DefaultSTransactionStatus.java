package com.github.fangming.springboot.jdbc.transaction.support;

import com.github.fangming.springboot.jdbc.transaction.SSavepointManager;
import com.github.fangming.springboot.jdbc.transaction.STransactionException;

public class DefaultSTransactionStatus extends AbstractSTransactionStatus{

    private final Object transaction;

    private final boolean newTransaction;

    private final boolean newSynchronization;

    private final boolean readOnly;

    private final boolean debug;

    private final Object suspendedResources;

    public DefaultSTransactionStatus(Object transaction, boolean newTransaction, boolean newSynchronization, boolean readOnly, boolean debug, Object suspendedResources) {
        this.transaction = transaction;
        this.newTransaction = newTransaction;
        this.newSynchronization = newSynchronization;
        this.readOnly = readOnly;
        this.debug = debug;
        this.suspendedResources = suspendedResources;
    }

    public Object getTransaction() {
        return this.transaction;
    }

    public boolean hasTransaction() {
        return (this.transaction != null);
    }

    public boolean isNewTransaction() {
        return (hasTransaction() && this.newTransaction);
    }

    public boolean isNewSynchronization() {
        return this.newSynchronization;
    }

    /**
     * Return if this transaction is defined as read-only transaction.
     */
    public boolean isReadOnly() {
        return this.readOnly;
    }

    /**
     * Return whether the progress of this transaction is debugged. This is used
     * by AbstractPlatformTransactionManager as an optimization, to prevent repeated
     * calls to logger.isDebug(). Not really intended for client code.
     */
    public boolean isDebug() {
        return this.debug;
    }

    /**
     * Return the holder for resources that have been suspended for this transaction,
     * if any.
     */
    public Object getSuspendedResources() {
        return this.suspendedResources;
    }


    /**
     * Determine the rollback-only flag via checking both the transaction object,
     * provided that the latter implements the SmartTransactionObject interface.
     * <p>Will return "true" if the transaction itself has been marked rollback-only
     * by the transaction coordinator, for example in case of a timeout.
     */
    public boolean isGlobalRollbackOnly() {
        return ((this.transaction instanceof SmartSTransactionObject) &&
            ((SmartSTransactionObject) this.transaction).isRollbackOnly());
    }

    /**
     * This implementation exposes the SavepointManager interface
     * of the underlying transaction object, if any.
     */
    protected SSavepointManager getSavepointManager() {
        if (!isTransactionSavepointManager()) {
            throw new STransactionException(
                "Transaction object [" + getTransaction() + "] does not support savepoints");
        }
        return (SSavepointManager) getTransaction();
    }

    /**
     * Return whether the underlying transaction implements the
     * SavepointManager interface.
     * @see #getTransaction
     */
    public boolean isTransactionSavepointManager() {
        return (getTransaction() instanceof SSavepointManager);
    }
}
