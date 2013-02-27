package com.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

	@Override
	public Client findByLogin(String login) {
		String sql = "SELECT c.* FROM clients c "
				+ "INNER JOIN users u ON c.id = u.id " + "WHERE u.login = :login";
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "login", login);
		try {
			Client user = (Client) getJdbcTemplate().query(sql,
                    namedParameters, new ClientRowMapper());

			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Client findById(Integer clientId) {
		String sql = "SELECT * FROM clients WHERE id = :clientId";
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "clientId", clientId);
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
		String sql = "SELECT * FROM clients WHERE number = :number";
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
		String sql = "SELECT * FROM clients ORDER BY status, lastname, firstname LIMIT ?, ?";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("start", start);
        namedParameters.put("limit", limit);

		List<Client> clients = getJdbcTemplate().query(sql,
                namedParameters, new ClientRowMapper());

		return clients;
	}

	@Override
	public void updateStatus(String number, ClientStatus status) {
		String sql = "UPDATE clients SET status=:status WHERE number=:number";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("status", status);
        namedParameters.put("number", number);
		getJdbcTemplate().update(sql,
				namedParameters);
	}

	@Override
	public BigDecimal findClientSum(Integer clientId) {
		String sql = "SELECT balance FROM client_balance WHERE client_id = :clientId ";
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "clientId", clientId);
		BigDecimal sum = (BigDecimal) getJdbcTemplate().queryForObject(sql,
				namedParameters, BigDecimal.class);

		return sum;
	}

	@Override
	public void updateBalance(Integer clientId, BigDecimal sum) {
		String query = "UPDATE client_balance SET balance = balance + :sum WHERE client_id=:clientId";
        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("sum", sum);
        namedParameters.put("clientId", clientId);
		getJdbcTemplate().update(query, namedParameters);
	}

	@Override
	public Integer getClientsCount() {
		String sql = "SELECT COUNT(*) FROM clients ";

		Integer count = (Integer) getJdbcTemplate().queryForInt(sql, new MapSqlParameterSource());

		return count;
	}

}
