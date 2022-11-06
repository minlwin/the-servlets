package com.jdc.task.test.db;

import static com.jdc.task.test.HasRecordComponent.hasRecordComponent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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
import com.jdc.task.model.dao.TaskDao;
import com.jdc.task.model.db.TaskDb;
import com.jdc.task.model.dto.Task.Status;
import com.jdc.task.model.dto.form.TaskForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.DbUtils;
import com.jdc.task.model.utils.StringUtils;
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

		DbUtils.truncate("account", "project", "task", "task_date");
		
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
		
		DbUtils.execute(
				"insert into task (name, owner_id, project_id, date_from, date_to, status, remark) values ('Database Design', 1, 1, '2022-11-01', '2022-11-05', 'Started', '');",
				"insert into task (name, owner_id, project_id, date_from, date_to, status, remark) values ('Create DDL For Database', 3, 1, '2022-11-05', '2022-11-06', 'Open', '');",
				"insert into task (name, owner_id, project_id, date_from, date_to, status, remark) values ('Service Class Design', 2, 3, '2022-11-01', '2022-11-03', 'Finished', '');",
				"insert into task (name, owner_id, project_id, date_from, date_to, status, remark) values ('Controller Class Design', 4, 3, '2022-10-25', '2022-10-26', 'Late', 'For Sickness');"
		);
		
		DbUtils.execute(
				"insert into task_date values (1, '2022-11-01');",
				"insert into task_date values (1, '2022-11-02');",
				"insert into task_date values (1, '2022-11-03');",
				"insert into task_date values (1, '2022-11-04');",
				"insert into task_date values (1, '2022-11-05');",
				"insert into task_date values (2, '2022-11-05');",
				"insert into task_date values (2, '2022-11-06');",
				"insert into task_date values (3, '2022-11-01');",
				"insert into task_date values (3, '2022-11-02');",
				"insert into task_date values (3, '2022-11-03');",
				"insert into task_date values (4, '2022-10-25');",
				"insert into task_date values (4, '2022-10-26');"
		);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_create.tsv", delimiter = '\t')
	void test_create(int id, String name, int projectId, int ownerId, LocalDate dateFrom, LocalDate dateTo, Status status, String remark) {
		
		var form = new TaskForm(name, projectId, ownerId, dateFrom, dateTo, status, remark);
		
		var result = dao.create(form);
		
		assertThat(result, is(id));
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_validation.tsv", delimiter = '\t')
	void test_validation(String name, int projectId, int ownerId, LocalDate dateFrom, LocalDate dateTo, Status status, String remark, int messages) {
		var form = new TaskForm(name, projectId, ownerId, dateFrom, dateTo, status, remark);
		
		var exception = assertThrows(TaskAppException.class, () -> dao.create(form));
		
		assertThat(exception, allOf(
				notNullValue(),
				hasProperty("messages", hasSize(messages))
				));
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_update.tsv", delimiter = '\t')
	void test_update(int id, String name, int projectId, String projectName, int projectOwnerId, String projectOwnerName, int ownerId, String ownerName, LocalDate dateFrom, LocalDate dateTo, Status status, String remark) {
		
		var form = new TaskForm(name, projectId, ownerId, dateFrom, dateTo, status, remark);
		
		dao.update(id, form);
		
		var result = dao.findById(id);
		
		assertThat(result, allOf(
				notNullValue(),
				hasRecordComponent("id", is(id)),
				hasRecordComponent("taskName", is(name)),
				hasRecordComponent("dateFrom", is(dateFrom)),
				hasRecordComponent("dateTo", is(dateTo)),
				hasRecordComponent("status", is(status)),
				hasRecordComponent("remark", StringUtils.isEmpty(remark) ? anyOf(emptyString(), nullValue()) : is(remark)),
				hasRecordComponent("taskOwnerId", is(ownerId)),
				hasRecordComponent("taskOwnerName", is(ownerName)),
				hasRecordComponent("projectId", is(projectId)),
				hasRecordComponent("projectName", is(projectName)),
				hasRecordComponent("projectOwnerId", is(projectOwnerId)),
				hasRecordComponent("projectOwnerName", is(projectOwnerName))
		));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/task/test_find_by_id.tsv", delimiter = '\t')
	void test_find_by_id(int id, String name, int projectId, String projectName, int projectOwnerId, String projectOwnerName, int ownerId, String ownerName, LocalDate dateFrom, LocalDate dateTo, Status status, String remark) {
		
		var result = dao.findById(id);
		
		assertThat(result, allOf(
				notNullValue(),
				hasRecordComponent("id", is(id)),
				hasRecordComponent("taskName", is(name)),
				hasRecordComponent("dateFrom", is(dateFrom)),
				hasRecordComponent("dateTo", is(dateTo)),
				hasRecordComponent("status", is(status)),
				hasRecordComponent("remark", StringUtils.isEmpty(remark) ? anyOf(emptyString(), nullValue()) : is(remark)),
				hasRecordComponent("taskOwnerId", is(ownerId)),
				hasRecordComponent("taskOwnerName", is(ownerName)),
				hasRecordComponent("projectId", is(projectId)),
				hasRecordComponent("projectName", is(projectName)),
				hasRecordComponent("projectOwnerId", is(projectOwnerId)),
				hasRecordComponent("projectOwnerName", is(projectOwnerName))
		));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 5, 6, 7})
	void test_not_found_by_id(int id) {
		assertNull(dao.findById(id));
	}
	
	@ParameterizedTest
	@CsvSource({
		",,,,4",
		"Started,,,,1",
		"Started,thi,,,1",
		"Started,Nilar,,,0",
		",,2022-11-01,,3",
		",,2022-11-01,2022-11-04,2",
		"Started,,2022-11-01,2022-11-04,1",
		",,,2022-10-25,1",
		",,,2022-10-24,0"
	})
	void test_search(Status status, String owner, LocalDate from, LocalDate to, int size) {
		
		var result = dao.search(status, owner, from, to);
		
		assertThat(result, allOf(
				notNullValue(),
				hasSize(size)
				));
	}

}
