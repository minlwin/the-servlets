package com.jdc.task.model.db;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.ProjectDao;
import com.jdc.task.model.db.mapper.ProjectMapper;
import com.jdc.task.model.dto.Project;
import com.jdc.task.model.dto.form.ProjectForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.Errors;
import com.jdc.task.model.utils.StringUtils;

public class ProjectDb implements ProjectDao{
	
	private ResultSetMapper<Project> mapper;
	
	public ProjectDb() {
		mapper = new ProjectMapper();
	}

	@Override
	public int create(ProjectForm form) {
		
		validate(form);
		
		var sql = "insert into project (name, owner_id, start_date, description, finished) values (?, ?, ?, ?, ?)";
		
		try(var conn = DataSourceManager.dataSource().getConnection(); 
				var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, form.name());
			stmt.setInt(2, form.ownerId());
			stmt.setDate(3, Date.valueOf(form.startDate()));
			stmt.setString(4, form.description());
			stmt.setBoolean(5, form.finished());
			
			stmt.executeUpdate();
			
			var rs = stmt.getGeneratedKeys();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
		
		return 0;
	}

	@Override
	public void update(int id, ProjectForm form) {
		
		validate(form);

		var sql = "update project set name = ?, owner_id = ?, start_date = ?, description = ?, finished = ? where id = ?";
		
		try(var conn = DataSourceManager.dataSource().getConnection(); 
				var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, form.name());
			stmt.setInt(2, form.ownerId());
			stmt.setDate(3, Date.valueOf(form.startDate()));
			stmt.setString(4, form.description());
			stmt.setBoolean(5, form.finished());
			stmt.setInt(6, id);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
	}

	@Override
	public Project findById(int id) {
		var sql = """
				select p.id, p.name, a.id, a.name, p.description, p.start_date, p.finished from project p 
				join account a on a.id = p.owner_id where p.id = ?
				""";
		
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
	public List<Project> search(String ownerName, String projectName, LocalDate date, Boolean finished) {
		var result = new ArrayList<Project>();
		var sql = new StringBuffer("""
				select p.id, p.name, a.id, a.name, p.description, p.start_date, p.finished from project p 
				join account a on a.id = p.owner_id where 1 = 1""");
		var params = new ArrayList<>();
		
		if(!StringUtils.isEmpty(ownerName)) {
			sql.append(" and (lower(a.name) = ? or lower(a.name) like ?)");
			params.add(StringUtils.lower(ownerName));
			params.add(StringUtils.lowerLike(ownerName));
		}
		
		if(!StringUtils.isEmpty(projectName)) {
			sql.append(" and lower(p.name) like ?");
			params.add(StringUtils.lowerLike(projectName));
		}
		
		if(null != date) {
			sql.append(" and p.start_date >= ?");
			params.add(Date.valueOf(date));
		}
		
		if(null != finished) {
			sql.append(" and p.finished = ?");
			params.add(finished);
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

	private void validate(ProjectForm form) {

		var list = new ArrayList<String>();

		if(StringUtils.isEmpty(form.name())) {
			list.add("Please enter project name.");
		}
		
		if(form.ownerId() == 0) {
			list.add("Please set owner for the project.");
		}
		
		if(null == form.startDate()) {
			list.add("Please set start date for the project.");
		}

		if(!list.isEmpty()) {
			Errors.make(list);
		}
	}

}
