package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Client;
import com.model.ClientStatus;

class ClientRowMapper implements RowMapper<Client> {

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		Client user = new Client();
		user.setId(rs.getInt("id"));
		user.setFirstname(rs.getString("firstname"));
		user.setLastname(rs.getString("lastname"));
		user.setNumber(rs.getString("number"));
		user.setStatus(ClientStatus.valueOf(rs.getString("status")));
		return user;
	}

}
