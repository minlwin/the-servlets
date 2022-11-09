package com.jdc.task.model.db;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.db.mapper.AccountMapper;
import com.jdc.task.model.dto.Account;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.AccountForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.Errors;
import com.jdc.task.model.utils.StringUtils;

public class AccountDb implements AccountDao{
	
	private ResultSetMapper<Account> mapper;
	
	public AccountDb() {
		mapper = new AccountMapper();
	}

	@Override
	public int create(AccountForm form) {
		
		validate(form, true);

		var sql = "insert into account (name, email, password, role, entry_date) values (?, ?, ?, ?, ?)";
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			
			stmt.setString(1, form.name());
			stmt.setString(2, form.email());
			stmt.setString(3, form.password());
			stmt.setString(4, form.role().name());
			stmt.setDate(5, Date.valueOf(LocalDate.now()));
			
			stmt.executeUpdate();

			var rs = stmt.getGeneratedKeys();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
			
			return 0;
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
	}

	@Override
	public void update(int id, AccountForm form) {

		validate(form, false);
		
		var sql = "update account set name = ?, email = ?, role = ? where id = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, form.name());
			stmt.setString(2, form.email());
			stmt.setString(3, form.role().name());
			stmt.setInt(4, id);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		
	}

	@Override
	public Account findById(int id) {
		var sql = "select id, name, role, email, entry_date from account where id = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			var rs = stmt.executeQuery();
			while(rs.next()) {
				return mapper.map(rs);
			}
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		return null;
	}
	
	@Override
	public Account findByEmail(String email) {
		var sql = "select id, name, role, email, entry_date from account where email = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			
			var rs = stmt.executeQuery();
			while(rs.next()) {
				return mapper.map(rs);
			}
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		return null;
	}


	@Override
	public List<Account> search(Role role, String name) {
		var result = new ArrayList<Account>();
		var sql = new StringBuffer("select id, name, role, email, entry_date from account where 1 = 1");
		var params = new ArrayList<Object>();
		
		if(null != role) {
			sql.append(" and role = ?");
			params.add(role.name());
		}
		
		if(StringUtils.isNotEmpty(name)) {
			sql.append(" and (lower(name) = ? or lower(name) like ?)");
			params.add(StringUtils.lower(name));
			params.add(StringUtils.lowerLike(name));
		}

		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql.toString())) {
			
			for(var i = 0; i < params.size(); i ++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			var rs = stmt.executeQuery();
			while(rs.next()) {
				result.add(mapper.map(rs));
			}
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		return result;
	}

	private void validate(AccountForm form, boolean create) {
		var list = new ArrayList<String>();
		
		if(StringUtils.isEmpty(form.name())) {
			list.add("Please enter Account Name.");
		}
		
		if(StringUtils.isEmpty(form.email())) {
			list.add("Please enter Email Address.");
		}
		
		if(create && StringUtils.isEmpty(form.password())) {
			list.add("Please enter Password.");
		}
		
		if(null == form.role()) {
			list.add("Please select Role of Account.");
		}

		if(!list.isEmpty()) {
			Errors.make(list);
		}
	}

	@Override
	public void changePass(int memberId, String oldPass, String newPass) {

		var errors = new ArrayList<String>();
		
		if(StringUtils.isEmpty(oldPass)) {
			errors.add("Please enter Old Password.");
		}
		
		if(StringUtils.isEmpty(newPass)) {
			errors.add("Please enter New Password.");
		}
		
		if(!errors.isEmpty()) {
			Errors.make(errors);
		}
		
		if(memberId == 0) {
			Errors.make(List.of("Invalid Member Id."));
		}
				
		if(!isSameOldPassword(memberId, oldPass)) {
			Errors.make(List.of("Invalid Old Password."));
		}
		
		var sql = "update account set password = ? where id = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection(); 
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, oldPass);
			stmt.setInt(2, memberId);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}		
	}

	private boolean isSameOldPassword(int memberId, String oldPass) {
		
		var sql = "select password from account where id = ? and password = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection(); 
				var stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, memberId);
			stmt.setString(2, oldPass);
			
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		
		return false;
	}


}
