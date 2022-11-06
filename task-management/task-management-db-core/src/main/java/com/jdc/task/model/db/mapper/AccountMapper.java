package com.jdc.task.model.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.dto.Account;
import com.jdc.task.model.dto.Account.Role;

public class AccountMapper implements ResultSetMapper<Account>{

	@Override
	public Account map(ResultSet rs) throws SQLException {
		return new Account(rs.getInt(1), 
				rs.getString(2), 
				Role.valueOf(rs.getString(3)), 
				rs.getString(4), 
				rs.getDate(5).toLocalDate());
	}

}
