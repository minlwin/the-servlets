package com.jdc.task.test.db;

import static com.jdc.task.test.HasRecordComponent.hasRecordComponent;
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
import org.junit.jupiter.params.provider.CsvSource;
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
		
		DbUtils.execute(
				"insert into project (name, owner_id, description, start_date, finished) values ('Task Management', 1, 'Servlet Demo Project', '2022-11-01', false);",
				"insert into project (name, owner_id, description, start_date, finished) values ('POS Management', 1, 'Spring Angular Project', '2022-06-01', true);",
				"insert into project (name, owner_id, description, start_date, finished) values ('HR Management', 2, 'Mobile Project', '2022-10-01', false);"				
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
				hasProperty("messages", hasSize(errors))
				));
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_update.tsv", delimiter = '\t')
	void test_update(int id, String name, int ownerId, String ownerName, String description, LocalDate startDate, boolean finished) {
		var form = new ProjectForm(name, ownerId, description, startDate, finished);
		
		dao.update(id, form);
		
		var dto = dao.findById(id);
		
		assertThat(dto, allOf(
				hasRecordComponent("id", is(id)),
				hasRecordComponent("name", is(name)),
				hasRecordComponent("ownerId", is(ownerId)),
				hasRecordComponent("ownerName", is(ownerName)),
				hasRecordComponent("description", is(description)),
				hasRecordComponent("start", is(startDate)),
				hasRecordComponent("finished", is(finished))
		));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/project/test_find_by_id.tsv", delimiter = '\t')
	void test_find_by_id(int id, String name, int ownerId, String ownerName, String description, LocalDate startDate, boolean finished) {
		
		var dto = dao.findById(id);
		
		assertThat(dto, allOf(
				hasRecordComponent("id", is(id)),
				hasRecordComponent("name", is(name)),
				hasRecordComponent("ownerId", is(ownerId)),
				hasRecordComponent("ownerName", is(ownerName)),
				hasRecordComponent("description", is(description)),
				hasRecordComponent("start", is(startDate)),
				hasRecordComponent("finished", is(finished))
		));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 5, 6, 7})
	void test_not_found_by_id(int id) {
		assertNull(dao.findById(id));
	}
	
	@ParameterizedTest
	@CsvSource({
		",,,,3",
		",,,false,2",
		",,,true,1",
		"Thidar,,,true,1",
		"thi,,,true,1",
		"thi,,2022-06-01,true,1",
		"thi,pos,2022-06-01,true,1",
		"thi,post,,true,0"
	})
	void test_search(String ownerName, String projectName, LocalDate date, Boolean finished, int size) {
		var result = dao.search(ownerName, projectName, date, finished);		
		assertThat(result, hasSize(size));
	}

}
