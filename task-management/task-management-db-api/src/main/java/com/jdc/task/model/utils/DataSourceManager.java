package com.jdc.task.model.utils;

import java.util.List;

import javax.sql.DataSource;

/**
 * Data Source Manager
 * 
 * Singleton Object and need to initialize with DataSource with Servlet Context Lifecycle Listeners
 * 
 * @author minlwin
 *
 */
public class DataSourceManager {

	private static DataSource dataSource;
	
	/**
	 * Initialize at ServletContext Lifecycle Listener
	 * 
	 * @param dataSource
	 */
	public static void init(DataSource dataSource) {
		DataSourceManager.dataSource = dataSource;
	}
	
	public static DataSource dataSource() {
		if(null == dataSource) {
			Errors.make(List.of("Please int DataSource first."));
		}
		return dataSource;
	}
}
