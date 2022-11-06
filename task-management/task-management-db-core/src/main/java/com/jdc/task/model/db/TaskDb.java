package com.jdc.task.model.db;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.TaskDao;
import com.jdc.task.model.db.mapper.TaskMapper;
import com.jdc.task.model.dto.Task;
import com.jdc.task.model.dto.Task.Status;
import com.jdc.task.model.dto.form.TaskForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.Errors;
import com.jdc.task.model.utils.StringUtils;

public class TaskDb implements TaskDao{
	
	private ResultSetMapper<Task> mapper;
	
	public TaskDb() {
		mapper = new TaskMapper();
	}

	@Override
	public int create(TaskForm form) {
		
		validate(form);
		
		var sql = """
				insert into task (name, owner_id, project_id, date_from, date_to, status, remark) 
				values (?, ?, ?, ?, ?, ?, ?)
				""";

		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, form.name());
			stmt.setInt(2, form.ownerId());
			stmt.setInt(3, form.projectId());
			stmt.setDate(4, Date.valueOf(form.dateFrom()));
			stmt.setDate(5, Date.valueOf(form.dateTo()));
			stmt.setString(6, form.status().name());
			stmt.setString(7, form.remark());
			
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
	public void update(int id, TaskForm form) {
		var sql = """
				update task set name = ?, owner_id = ?, project_id = ?, date_from = ?, date_to = ?, 
				status = ?, remark = ? where id = ?
				""";

		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.prepareStatement(sql)) {
			
		} catch (SQLException e) {
			throw new TaskAppException(List.of(e.getMessage()), e);
		}
	}

	@Override
	public Task findById(int id) {
		var sql = """
				select t.id, t.name, t.date_from, t.date_to, t.status, t.remark, 
				to.id, to.name, p.id, p.name, po.id, po.name from task t 
				join account to on to.id = t.owner_id 
				join project p on p.id = t.project_id 
				join account po on po.id = p.owner_id 
				where t.id = ?
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
	public List<Task> search(Status status, String owner, LocalDate from, LocalDate to) {
		var result = new ArrayList<Task>();
		var sql = new StringBuffer("""
				select t.id, t.name, t.date_from, t.date_to, t.status, t.remark, 
				to.id, to.name, p.id, p.name, po.id, po.name from task t 
				join account to on to.id = t.owner_id 
				join project p on p.id = t.project_id 
				join account po on po.id = p.owner_id 
				where 1 = 1""");
		var params = new ArrayList<>();
		
		if(null != status) {
			sql.append(" and t.status = ?");
			params.add(status.name());
		}
		
		if(!StringUtils.isEmpty(owner)) {
			sql.append(" and lower(to.name) like ?");
			params.add(StringUtils.lowerLike(owner));
		}
		
		if(null != from) {
			sql.append(" and t.start_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to) {
			sql.append(" and t.end_date <= ?");
			params.add(Date.valueOf(to));
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

	private void validate(TaskForm form) {

		var list = new ArrayList<String>();

		// name 
		if(StringUtils.isEmpty(form.name())) {
			list.add("Please enter task name.");
		}
		
		// owner_id
		if(form.ownerId() == 0) {
			list.add("Please set owner for the task.");
		}
		
		// project_id
		if(form.projectId() == 0) {
			list.add("Please set project for the task.");
		}

		// date_from
		if(null == form.dateFrom()) {
			list.add("Please enter Start Date.");			
		}
		// date_to
		if(null == form.dateTo()) {
			list.add("Please enter End Date.");			
		}
		
		// status	
		if(null == form.status()) {
			list.add("Please enter Status of the task.");			
		}
		
		if(!list.isEmpty()) {
			Errors.make(list);
		}
	}
}
