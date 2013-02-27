package com.services;

import java.math.BigDecimal;
import java.util.List;

import com.model.Client;
import com.model.ClientStatus;
import com.model.Transaction;

public interface DBService {

	/**
	 * Find client by login
	 * 
	 * @param login
	 * @return {@link Client}
	 */
	public Client findClientByLogin(String login);

	/**
	 * Find client by id
	 * 
	 * @param clientId
	 * @return {@link Client}
	 */
	public Client findClientById(Integer clientId);

	/**
	 * Find client by number
	 * 
	 * @param number
	 *            - account number
	 * @return {@link Client}
	 */
	public Client findClientByNumber(String number);

	/**
	 * Find client transactions with the given range
	 * 
	 * @param number
	 *            - the client number
	 * @param start
	 *            - the offset
	 * @param limit
	 *            - the number of results returned
	 * @return {@link Transaction} list
	 */
	public List<Transaction> findClientTransactions(String number,
			Integer start, Integer limit);

	/**
	 * Get client transactions count
	 * 
	 * @param number
	 *            - client number
	 */
	public Integer getTransactionsCount(String number);

	/**
	 * Create new bank transaction
	 * 
	 * @param source - source account
     * @param destination - destination account
     * @param sum - transaction sum
	 */
	public void createTransaction(Client source, Client destination, BigDecimal sum);

	/**
	 * Find bank clients with the given range
	 * 
	 * @param start
	 *            - the offset
	 * @param limit
	 *            - the number of results returned
	 * @return {@link Client} list
	 */
	public List<Client> findClients(Integer start, Integer limit);

	/**
	 * Find client balance
	 * 
	 * @param clientId
	 *            - client id
	 * @return current balance
	 */
	public BigDecimal findClientSum(Integer clientId);

	/**
	 * Update client status
	 * 
	 * @param number
	 *            - account number
	 * @param status
	 *            - current client status {@link ClientStatus}
     * @return client status after update
	 */
	public ClientStatus updateClientStatus(String number, ClientStatus status);

	/**
	 * Get clients count
	 */
	public Integer getClientsCount();
}
