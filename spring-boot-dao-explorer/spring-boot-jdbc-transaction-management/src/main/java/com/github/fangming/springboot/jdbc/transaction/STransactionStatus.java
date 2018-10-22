package com.github.fangming.springboot.jdbc.transaction;

public interface STransactionStatus {

    boolean isNewTransaction();

    boolean hasSavepoint();

    void setRollbackOnly();

    boolean isRollbackOnly();

    boolean isCompleted();

}
