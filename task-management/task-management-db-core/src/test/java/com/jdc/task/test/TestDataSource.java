package com.jdc.task.test;

import org.apache.commons.dbcp.BasicDataSource;

public class TestDataSource extends BasicDataSource{

	public TestDataSource() {
		setUrl("jdbc:mysql://localhost:3306/task_db");
		setUsername("taskusr");
		setPassword("taskpwd");
	}
}
