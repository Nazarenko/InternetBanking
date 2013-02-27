package com.dao;

import java.math.BigDecimal;
import java.util.List;

import com.model.Client;
import com.model.ClientStatus;

public interface ClientDao {

	/**
	 * Find client by login
	 * 
	 * @param login
	 * @return {@link Client}
	 */
	public Client findByLogin(String login);

	/**
	 * Find client by id
	 * 
	 * @param clientId
	 * @return {@link Client}
	 */
	public Client findById(Integer clientId);

	/**
	 * Find client by account number
	 * 
	 * @param number
	 * @return {@link Client}
	 */
	public Client findByNumber(String number);

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
	 * Update client status
	 * 
	 * @param number
	 *            - account number
	 * @param status
	 *            - new client {@link ClientStatus}
	 */
	public void updateStatus(String number, ClientStatus status);

	/**
	 * Update client balance with sum
	 * 
	 * @param clientId
	 *            - client id
	 * @param sum
	 *            - transaction sum for addition or subtraction
	 */
	public void updateBalance(Integer clientId, BigDecimal sum);

	/**
	 * Find client balance
	 * 
	 * @param clientId
	 *            - client id
	 * @return current balance
	 */
	public BigDecimal findClientSum(Integer clientId);
	
	/**
	 * Get clients count
	 */
	public Integer getClientsCount();
}
