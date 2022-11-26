package com.jdc.basic.location.model.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		
		validate(form);
		var sql = "insert into state(name, region, capital) values (?, ?, ?)";
		
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
				return findById(id);
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

		return null;
	}


	@Override
	public State update(int id, StateForm form) {
		
		validate(form);
		var sql = "update state set name = ?, region = ?, capital = ? where id = ?";
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, form.name());
			stmt.setString(2, form.region());
			stmt.setString(3, form.capital());
			stmt.setInt(4, id);
			
			stmt.executeUpdate();
			
			return findById(id);
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}		
	}

	@Override
	public State findById(int id) {
		var sql = "select * from state where id = ?";
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getData(rs);
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}		
		
		return null;
	}


	@Override
	public List<State> search(String region, String name) {
		var sb = new StringBuffer("select * from state where 1 = 1");
		var params = new ArrayList<Object>();
		var list = new ArrayList<State>();
		 
		if(!StringUtils.isEmpty(region)) {
			sb.append(" and region = ?");
			params.add(region);
		}
		
		if(!StringUtils.isEmpty(name)) {
			sb.append(" and lower(name) like ?");
			params.add(name.toLowerCase().concat("%"));
		}
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(sb.toString())) {
			
			for(var i = 0; i < params.size(); i ++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(getData(rs));
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}		
		return list;
	}

	@Override
	public long findCountByName(String name) {
		var sql = "select count(id) from state where name = ?";

		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getLong(1);
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}	
		
		return 0;
	}

	@Override
	public int upload(List<StateForm> forms) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private State getData(ResultSet rs) throws SQLException{
		return new State(
				rs.getInt("id"), 
				rs.getString("name"), 
				rs.getString("region"), 
				rs.getString("capital"));
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
