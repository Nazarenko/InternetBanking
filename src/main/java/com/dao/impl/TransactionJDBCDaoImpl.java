package com.dao.impl;

import java.math.BigDecimal;
import java.util.*;

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

    private Properties queries;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public Properties getQueries() {
        return queries;
    }

    @Autowired
    public void setQueries(Properties queries) {
        this.queries = queries;
    }

    private NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Transaction> find(String number, Integer start, Integer limit) {
		String sql = queries.getProperty("find_transactions_by_client");

		// Assign values to parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("number", number);
		parameters.put("offset", start);
		parameters.put("limit", limit);

		List<Transaction> transactions = getJdbcTemplate().query(sql,
                parameters, new TransactionRowMapper());

		return transactions;
	}

	@Override
	public void createTransaction(Transaction transaction) {
		String query = queries.getProperty("insert_transaction");
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("sourceId", transaction.getSourceAccount().getId());
		namedParameters.put("destinationId", transaction
				.getDestinationAccount().getId());
		namedParameters.put("sum", transaction.getSum());
		getJdbcTemplate().update(query, namedParameters);
	}

	@Override
	public Integer getTransactionsCount(String number) {
		String sql = queries.getProperty("find_transactions_count_by_client");
		SqlParameterSource namedParameters = new MapSqlParameterSource(
                "number", number);
		return getJdbcTemplate().queryForInt(sql, namedParameters);
	}
}