package com.jdc.task.model.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.dto.Task;
import com.jdc.task.model.dto.Task.Status;

public class TaskMapper implements ResultSetMapper<Task>{

	@Override
	public Task map(ResultSet rs) throws SQLException {
		return new Task(rs.getInt(1), 
				rs.getString(2), 
				rs.getDate(3).toLocalDate(), 
				rs.getDate(4).toLocalDate(), 
				Status.valueOf(rs.getString(5)), 
				rs.getString(6), 
				rs.getInt(7), 
				rs.getString(8), 
				rs.getInt(9), 
				rs.getString(10), 
				rs.getString(11), 
				rs.getInt(12), 
				rs.getString(13));
	}

}
