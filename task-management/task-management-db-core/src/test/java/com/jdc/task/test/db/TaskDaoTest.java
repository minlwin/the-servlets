package com.jdc.task.test.db;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jdc.task.model.dao.TaskDao;
import com.jdc.task.model.db.TaskDb;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.DbUtils;
import com.jdc.task.test.TestDataSource;

public class TaskDaoTest {
	
	private TaskDao dao;

	@BeforeAll
	static void beforeAll() {
		DataSourceManager.init(new TestDataSource());
	}

	@BeforeEach
	void before() {
		dao = new TaskDb();
		DbUtils.truncate("account", "project", "task");
		
		DbUtils.execute(
				"insert into account (name, email, role, password, entry_date) values ('Thidar', 'thidar@gmail.com', 'Manager', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nilar', 'nilar@gmail.com', 'Manager', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Ko Ko', 'koko@gmail.com', 'Member', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nyi Nyi', 'nyinyi@gmail.com', 'Member', 'password', '2022-11-01');"
				);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_create.tsv", delimiter = '\t')
	void test_create() {
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_validation.tsv", delimiter = '\t')
	void test_validation() {
		
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_update.tsv", delimiter = '\t')
	void test_update() {
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_find_by_id.tsv", delimiter = '\t')
	void test_find_by_id() {
		
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 5, 6, 7})
	void test_not_found_by_id(int id) {
		assertNull(dao.findById(id));
	}
	
	@ParameterizedTest
	void test_search() {
		
	}

}
