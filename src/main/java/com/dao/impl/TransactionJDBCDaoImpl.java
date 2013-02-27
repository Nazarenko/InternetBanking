package com.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.dao.TransactionDao;
import com.model.Client;
import com.model.Transaction;

@Repository
public class TransactionJDBCDaoImpl implements TransactionDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	private NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public List<Transaction> find(String number, Integer start, Integer limit) {
		String sql = "SELECT t.*, s.firstname as source_firstname, "
				+ "s.lastname as source_lastname, s.number as source_number, "
				+ "d.firstname as destination_firstname, d.lastname as destination_lastname, "
				+ "d.number as destination_number "
				+ "FROM transactions t "
				+ "INNER JOIN clients s ON s.id = t.source_id "
				+ "INNER JOIN clients d ON d.id = t.destination_id "
				+ "WHERE s.number = ':number' OR d.number = ':number' "
				+ "ORDER BY t.transaction_date DESC "
				+ "LIMIT :offset,:limit";

		// Assign values to parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("number", number);
		parameters.put("offset", start);
		parameters.put("limit", limit);

		List<Transaction> transactions = new ArrayList<Transaction>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql,
				parameters);
		for (Map<String, Object> row : rows) {
			// source user
			Client source = new Client((Integer) row.get("source_id"));
			source.setFirstname((String) row.get("source_firstname"));
			source.setLastname((String) row.get("source_lastname"));
			source.setNumber((String) row.get("source_number"));

			// destination user
			Client destination = new Client((Integer) row.get("destination_id"));
			destination.setFirstname((String) row.get("destination_firstname"));
			destination.setLastname((String) row.get("destination_lastname"));
			destination.setNumber((String) row.get("destination_number"));

			// transaction
			Transaction transaction = new Transaction();
			transaction.setDate((Date) row.get("transaction_date"));
			transaction.setSum((BigDecimal) row.get("sum"));
			transaction.setSourceAccount(source);
			transaction.setDestinationAccount(destination);

			transactions.add(transaction);
		}

		return transactions;
	}

	@Override
	public void createTransaction(Transaction transaction) {
		String query = "INSERT INTO transactions (source_id, destination_id, sum) " +
				"VALUES (:sourceId,:destinationId,:sum)";
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("sourceId", transaction.getSourceAccount().getId());
		namedParameters.put("destinationId", transaction
				.getDestinationAccount().getId());
		namedParameters.put("sum", transaction.getSum());
		getJdbcTemplate().update(query, namedParameters);
	}

	@Override
	public Integer getTransactionsCount(String number) {
		String sql = "SELECT COUNT(*) FROM transactions INNER JOIN clients c ON c.number = ':number'" +
                " WHERE source_id =c.id OR destination_id =c.id";
		SqlParameterSource namedParameters = new MapSqlParameterSource(
                "number", number);
		return getJdbcTemplate().queryForInt(sql, namedParameters);
	}
}