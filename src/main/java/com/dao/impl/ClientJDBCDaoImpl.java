package com.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.dao.ClientDao;
import com.model.Client;
import com.model.ClientStatus;

@Repository
public class ClientJDBCDaoImpl implements ClientDao {

    private Properties queries;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public Properties getQueries() {
        return queries;
    }

    @Autowired
    public void setQueries(Properties queries) {
        this.queries = queries;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

	@Override
	public Client findByLogin(String login) {
		String sql = queries.getProperty("find_client_by_login");
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "login", login);
		try {
			Client user = (Client) getJdbcTemplate().queryForObject(sql,
                    namedParameters, new ClientRowMapper());

			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Client findById(Integer clientId) {
		String sql = queries.getProperty("find_client_by_id");
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "id", clientId);
		try {
			Client user = (Client) getJdbcTemplate().queryForObject(sql,
					namedParameters, new ClientRowMapper());
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Client findByNumber(String number) {
		String sql = queries.getProperty("find_client_by_number");
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "number", number);
		try {
			Client user = (Client) getJdbcTemplate().queryForObject(sql,
					namedParameters, new ClientRowMapper());
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Client> findClients(Integer start, Integer limit) {
		String sql = queries.getProperty("find_clients_with_limit");
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("start", start);
        namedParameters.put("limit", limit);

		List<Client> clients = getJdbcTemplate().query(sql,
                namedParameters, new ClientRowMapper());

		return clients;
	}

	@Override
	public void updateStatus(String number, ClientStatus status) {
		String sql = queries.getProperty("update_client_status");
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("status", status.toString());
        namedParameters.put("number", number);
		getJdbcTemplate().update(sql,
				namedParameters);
	}

	@Override
	public BigDecimal findClientSum(Integer clientId) {
		String sql = queries.getProperty("find_client_balance");
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "clientId", clientId);
		BigDecimal sum = (BigDecimal) getJdbcTemplate().queryForObject(sql,
				namedParameters, BigDecimal.class);

		return sum;
	}

	@Override
	public void updateBalance(Integer clientId, BigDecimal sum) {
		String query = queries.getProperty("update_client_balance");
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("sum", sum);
        namedParameters.put("clientId", clientId);
		getJdbcTemplate().update(query, namedParameters);
	}

	@Override
	public Integer getClientsCount() {
		String sql = queries.getProperty("find_clients_count");
		Integer count = (Integer) getJdbcTemplate().queryForInt(sql, new MapSqlParameterSource());
		return count;
	}

}
