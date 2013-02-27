package com.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ClientDao;
import com.dao.TransactionDao;
import com.model.Client;
import com.model.ClientStatus;
import com.model.Transaction;

@Service
@Transactional
public class DBServiceImpl implements DBService {
	private ClientDao clientDao;
	private TransactionDao transactionDao;

	public ClientDao getClientDao() {
		return clientDao;
	}

	@Autowired
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	@Autowired
	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Override
	public Client findClientByLogin(String login) {
		return clientDao.findByLogin(login);
	}

	@Override
	public Client findClientById(Integer clientId) {
		return clientDao.findById(clientId);
	}

	@Override
	public Client findClientByNumber(String number) {
		return clientDao.findByNumber(number);
	}

	@Override
	public List<Transaction> findClientTransactions(String number,
			Integer start, Integer limit) {
		return transactionDao.find(number, start, limit);
	}

	@Override
	public List<Client> findClients(Integer start, Integer limit) {
		return clientDao.findClients(start, limit);
	}

	@Override
	public BigDecimal findClientSum(Integer clientId) {
		return clientDao.findClientSum(clientId);
	}

	@Override
	public void createTransaction(Client source, Client destination, BigDecimal sum) {
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);
        transaction.setSum(sum);
		transactionDao.createTransaction(transaction);

		clientDao.updateBalance(source.getId(),
				transaction.getSum().negate());
		clientDao.updateBalance(destination.getId(),
				transaction.getSum());
	}

	@Override
	public ClientStatus updateClientStatus(String number, ClientStatus status) {
        ClientStatus nextStatus = status.nextStatus();
		clientDao.updateStatus(number, nextStatus);
        return nextStatus;
	}

	@Override
	public Integer getTransactionsCount(String number) {
		return transactionDao.getTransactionsCount(number);
	}

	@Override
	public Integer getClientsCount() {
		return clientDao.getClientsCount();
	}

}
