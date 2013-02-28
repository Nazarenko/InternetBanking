package com.services;

import java.math.BigDecimal;
import java.util.List;

import com.exceptions.ServiceException;
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
public class AppServiceImpl implements AppService {

    private static int ACCOUNTS_PER_PAGE = 10;
    private static int TRANSACTIONS_PER_PAGE = 10;

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
    public List<Transaction> findClientLastTransactions(String number,
                                                    Integer count) {
        return transactionDao.find(number, 0, count);
    }

	@Override
	public List<Transaction> findClientTransactions(String number,
			Integer page) {
		return transactionDao.find(number, (page - 1)
                * TRANSACTIONS_PER_PAGE, TRANSACTIONS_PER_PAGE);
	}

	@Override
	public List<Client> findClients(Integer page) {
		return clientDao.findClients((page - 1) * ACCOUNTS_PER_PAGE,
                ACCOUNTS_PER_PAGE);
	}

	@Override
	public BigDecimal findClientSum(Integer clientId) {
		return clientDao.findClientSum(clientId);
	}

	@Override
	public void createTransaction(String source, String destination, BigDecimal sum) {
        // destination account check
        Client clientDestination = clientDao.findByNumber(destination);
        if (clientDestination == null) {
            throw new ServiceException("Destination account is not found");
        }
        if (!clientDestination.getStatus().equals(ClientStatus.ACTIVE)) {
            throw new ServiceException("Destination account is not active");
        }

        // source account and balance check
        Client clientSource = clientDao.findByNumber(source);
        if (!clientSource.getStatus().equals(ClientStatus.ACTIVE)) {
            throw new ServiceException("Your account is not active");
        }
        BigDecimal currentSum = clientDao.findClientSum(clientSource.getId());
        if (currentSum.compareTo(sum) == -1) {
            throw new ServiceException("You don't have enough money");
        }


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(clientSource);
        transaction.setDestinationAccount(clientDestination);
        transaction.setSum(sum);
		transactionDao.createTransaction(transaction);

		clientDao.updateBalance(clientSource.getId(),
				transaction.getSum().negate());
		clientDao.updateBalance(clientDestination.getId(),
				transaction.getSum());
	}

	@Override
	public ClientStatus updateClientStatus(String number, ClientStatus status) {
        ClientStatus nextStatus = status.nextStatus();
		clientDao.updateStatus(number, nextStatus);
        return nextStatus;
	}

	@Override
	public Integer getTransactionsPages(String number) {
		Integer count = transactionDao.getTransactionsCount(number);
        Integer pages = count / TRANSACTIONS_PER_PAGE;
        if (count % TRANSACTIONS_PER_PAGE > 0) {
            pages++;
        }
        return  pages;
	}

	@Override
	public Integer getAccountsPages() {
        Integer count = clientDao.getClientsCount();
        Integer pages = count / ACCOUNTS_PER_PAGE;
        if (count % ACCOUNTS_PER_PAGE > 0) {
            pages++;
        }
		return pages;
    }

}
