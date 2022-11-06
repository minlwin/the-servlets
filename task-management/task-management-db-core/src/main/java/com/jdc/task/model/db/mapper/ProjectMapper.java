package com.jdc.task.model.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.task.model.ResultSetMapper;
import com.jdc.task.model.dto.Project;

public class ProjectMapper implements ResultSetMapper<Project> {

	@Override
	public Project map(ResultSet rs) throws SQLException {
		return new Project(rs.getInt(1), 
				rs.getString(2), 
				rs.getInt(3), 
				rs.getString(4), 
				rs.getString(5), 
				rs.getDate(6).toLocalDate(),
				rs.getBoolean(7));
	}

}
