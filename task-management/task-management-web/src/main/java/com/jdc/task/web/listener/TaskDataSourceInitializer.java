package com.jdc.task.web.listener;

import javax.sql.DataSource;

import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.AccountForm;
import com.jdc.task.model.utils.DataSourceManager;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class TaskDataSourceInitializer implements ServletContextListener{
	
	@Resource(name = "jdbc/TaskDB")
	private DataSource dataSource;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DataSourceManager.init(dataSource);
		
		var dao = new AccountDb();
		
		if(dao.search(Role.Manager, null).isEmpty()) {
			var form = new AccountForm("Admin User", Role.Manager, "admin@gmail.com", "admin");
			dao.create(form);
		}
	}
	
}
