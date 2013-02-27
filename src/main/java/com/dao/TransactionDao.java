package com.dao;

import java.util.List;

import com.model.Transaction;

public interface TransactionDao {

	/**
	 * Find all user transactions with the given range
	 * 
	 * @param number
	 *            - the client number
	 * @param start
	 *            - the offset
	 * @param limit
	 *            - the number of results returned
	 * @return {@link Transaction} list
	 */
	public List<Transaction> find(String number, Integer start, Integer limit);

	/**
	 * Create new transaction
	 * 
	 * @param transaction
	 *            - {@link Transaction}
	 */
	public void createTransaction(Transaction transaction);

	/**
	 * Get client transactions count
	 * 
	 * @param number
	 *            - client number
	 */
	public Integer getTransactionsCount(String number);
}
