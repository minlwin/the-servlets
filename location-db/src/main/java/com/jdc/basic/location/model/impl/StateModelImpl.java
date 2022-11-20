package com.jdc.basic.location.model.impl;

import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.jdc.basic.location.model.StateModel;
import com.jdc.basic.location.model.dto.State;
import com.jdc.basic.location.model.form.StateForm;
import com.jdc.basic.location.utils.StringUtils;

public class StateModelImpl implements StateModel {

	private DataSource dataSource;

	public StateModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public State create(StateForm form) {
		
		var sql = "insert into state(name, region, capital) values (?, ?, ?)";
		
		validate(form);
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(sql, 
						Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, form.name());
			stmt.setString(2, form.region());
			stmt.setString(3, form.capital());
			
			stmt.executeUpdate();
			
			var rs = stmt.getGeneratedKeys();
			
			while(rs.next()) {
				var id = rs.getInt(1);
				return State.from(id, form);
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

		return null;
	}


	@Override
	public State findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State update(int id, StateForm form) {
		
		validate(form);
		
		return null;
	}

	private void validate(StateForm form) {
		
		if(null == form) {
			throw new IllegalArgumentException("Form must not be null.");
		}
		
		if(StringUtils.isEmpty(form.name())) {
			throw new IllegalArgumentException("Please enter name of state.");
		}
		
		if(StringUtils.isEmpty(form.region())) {
			throw new IllegalArgumentException("Please enter region of state.");
		}
		
		if(StringUtils.isEmpty(form.capital())) {
			throw new IllegalArgumentException("Please enter capital of state.");
		}		
	}
}
