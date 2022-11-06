package com.jdc.task.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper<T> {

	T map(ResultSet rs) throws SQLException;
}