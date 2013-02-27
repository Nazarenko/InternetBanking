package com.dao.impl;

import com.model.Client;
import com.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 2/27/13
 * Time: 2:54 PM
 */
public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

        Client source = new Client();
        source.setFirstname(rs.getString("source_firstname"));
        source.setLastname(rs.getString("source_lastname"));
        source.setNumber(rs.getString("source_number"));

        // destination user
        Client destination = new Client();
        destination.setFirstname(rs.getString("destination_firstname"));
        destination.setLastname(rs.getString("destination_lastname"));
        destination.setNumber(rs.getString("destination_number"));

        Transaction transaction = new Transaction();
        transaction.setDate(rs.getDate("transaction_date"));
        transaction.setSum(rs.getBigDecimal("sum"));
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);

         return transaction;
    }
}
