package com.github.fangming.springboot.jdbc.transaction.support;

public class SimpleSTransactionStatus extends AbstractSTransactionStatus {

	private final boolean newTransaction;


	/**
     * Creates a new instance of the {@link SimpleSTransactionStatus} class,
	 * indicating a new transaction.
	 */
	public SimpleTransactionStatus() {
		this(true);
	}

	/**
	 * Creates a new instance of the {@link SimpleSTransactionStatus} class.
	 * @param newTransaction whether to indicate a new transaction
	 */
	public SimpleSTransactionStatus(boolean newTransaction) {
		this.newTransaction = newTransaction;
	}


	public boolean isNewTransaction() {
		return newTransaction;
	}

}