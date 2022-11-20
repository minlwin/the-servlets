package com.jdc.basic.location.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class TestUtils {

	public static DataSource getDataSource() {
		var ds = new BasicDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/location_db");
		ds.setUsername("location");
		ds.setPassword("location");
		return ds;
	}

	public static void truncate(String ... tables) {
		
		try (var conn = getDataSource().getConnection();
				var stmt = conn.createStatement()){
			
			stmt.execute("set foreign_key_checks = 0");
			
			for(var tbl : tables) {
				stmt.execute("truncate table %s".formatted(tbl));
			}
			
			stmt.execute("set foreign_key_checks = 1");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void execute(String sql) {
		try (var conn = getDataSource().getConnection();
				var stmt = conn.createStatement()){
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
