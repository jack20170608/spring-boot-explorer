package com.github.fangming.springboot.jdbc.transaction.support;

public interface SmartSTransactionObject {

	/**
     * Return whether the transaction is internally marked as rollback-only.
	 * Can, for example, check the JTA UserTransaction.
	 */
	boolean isRollbackOnly();

}