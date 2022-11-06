package com.jdc.task.model.utils;

public class DbUtils {

	public static void execute(String ... sqls) {
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.createStatement()) {
			for(var sql : sqls) {
				stmt.execute(sql);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void truncate(String ... tables) {
		
		try(var conn = DataSourceManager.dataSource().getConnection();
				var stmt = conn.createStatement()) {
			stmt.execute("set foreign_key_checks = 0;");
			
			for(var table : tables) {
				stmt.execute("truncate table %s;".formatted(table));
			}			

			stmt.execute("set foreign_key_checks = 1;");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
