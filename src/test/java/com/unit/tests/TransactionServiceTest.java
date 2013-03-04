package com.unit.tests;

import com.dao.impl.ClientJDBCDaoImpl;
import com.dao.impl.TransactionJDBCDaoImpl;
import com.exceptions.NotFoundException;
import com.exceptions.DataException;
import com.model.Client;
import com.model.ClientStatus;
import com.services.AppServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/1/13
 * Time: 2:02 PM
 */
public class TransactionServiceTest {

    @InjectMocks
    AppServiceImpl appService;
    @Mock
    TransactionJDBCDaoImpl transactionDao;
    @Mock
    ClientJDBCDaoImpl clientDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void transactionCreateTest() {
        Client clientActive = new Client(1);
        clientActive.setStatus(ClientStatus.ACTIVE);
        Client clientBlocked = new Client();
        clientBlocked.setStatus(ClientStatus.BLOCKED);
        Client clientNew = new Client();
        clientNew.setStatus(ClientStatus.NEW);
        when(clientDao.findByNumber("not exist")).thenReturn(null);
        when(clientDao.findByNumber("blocked")).thenReturn(clientBlocked);
        when(clientDao.findByNumber("new")).thenReturn(clientNew);
        when(clientDao.findByNumber("active")).thenReturn(clientActive);
        when(clientDao.findClientSum(1)).thenReturn(new BigDecimal(1000));

        String errorDestinationNotFound = "Destination account is not found";
        String errorDestinationNotActive = "Destination account is not active";
        String errorSourceNotActive = "Your account is not active";
        String errorNoMoney = "You don't have enough money";

        // check destination account
        try {
            appService.createTransaction("active", "not exist", BigDecimal.ZERO);
            assertTrue("Destination account should not be found", false);
        } catch (NotFoundException e) {
            assertTrue(e.getMessage().equals(errorDestinationNotFound));
        }
        try {
            appService.createTransaction("active", "blocked", BigDecimal.ZERO);
            assertTrue("Destination account should not be active", false);
        } catch (DataException e) {
            assertTrue(e.getMessage().equals(errorDestinationNotActive));
        }
        try {
            appService.createTransaction("active", "new", BigDecimal.ZERO);
            assertTrue("Destination account should not be active", false);
        } catch (DataException e) {
            assertTrue(e.getMessage().equals(errorDestinationNotActive));
        }

        // check source account
        try {
            appService.createTransaction("new", "active", BigDecimal.ZERO);
            assertTrue("Source account should not be active", false);
        } catch (DataException e) {
            assertTrue(e.getMessage().equals(errorSourceNotActive));
        }
        try {
            appService.createTransaction("blocked", "active", BigDecimal.ZERO);
            assertTrue("Source account should not be active", false);
        } catch (DataException e) {
            assertTrue(e.getMessage().equals(errorSourceNotActive));
        }
        try {
            appService.createTransaction("active", "active", new BigDecimal(2000));
            assertTrue("Source account should not have money", false);
        } catch (DataException e) {
            assertTrue(e.getMessage().equals(errorNoMoney));
        }

        // successful result
        appService.createTransaction("active", "active", new BigDecimal(1000));
    }

}
