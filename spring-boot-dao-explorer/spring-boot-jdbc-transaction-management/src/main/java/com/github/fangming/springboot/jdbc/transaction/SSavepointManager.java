package com.github.fangming.springboot.jdbc.transaction;

public interface SSavepointManager {

    Object createSavepoint() throws STransactionException;

    void rollbackToSavepoint(Object savepoint) throws STransactionException;

    void releaseSavepoint(Object savepoint) throws STransactionException;
}
