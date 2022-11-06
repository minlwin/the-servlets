package com.jdc.task.test.db;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jdc.task.model.TaskAppException;
import com.jdc.task.model.dao.ProjectDao;
import com.jdc.task.model.db.ProjectDb;
import com.jdc.task.model.dto.form.ProjectForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.DbUtils;
import com.jdc.task.test.TestDataSource;

public class ProjectDaoTest {

	private ProjectDao dao;

	@BeforeAll
	static void beforeAll() {
		DataSourceManager.init(new TestDataSource());
	}

	@BeforeEach
	void before() {
		dao = new ProjectDb();
		DbUtils.truncate("account", "project", "task");
		
		DbUtils.execute(
				"insert into account (name, email, role, password, entry_date) values ('Thidar', 'thidar@gmail.com', 'Manager', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nilar', 'nilar@gmail.com', 'Manager', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Ko Ko', 'koko@gmail.com', 'Member', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nyi Nyi', 'nyinyi@gmail.com', 'Member', 'password', '2022-11-01');"
				);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_create.tsv", delimiter = '\t')
	void test_create(int id, String name, int ownerId, String description, LocalDate startDate, boolean finished) {
		
		var form = new ProjectForm(name, ownerId, description, startDate, finished);
		
		var result = dao.create(form);
		
		assertThat(result, is(id));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_validation.tsv", delimiter = '\t')
	void test_validation(String name, int ownerId, String description, LocalDate startDate, boolean finished, int errors) {
		
		var form = new ProjectForm(name, ownerId, description, startDate, finished);
		
		var exception = assertThrows(TaskAppException.class, () -> dao.create(form));
		
		assertThat(exception, allOf(
				notNullValue(),
				hasProperty("errors", hasSize(errors))
				));
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_update.tsv", delimiter = '\t')
	void test_update(int id, String name, int ownerId, String description, LocalDate startDate, boolean finished) {
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_find_by_id.tsv", delimiter = '\t')
	void test_find_by_id(int id, String name, int ownerId, String description, LocalDate startDate, boolean finished) {
		
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
