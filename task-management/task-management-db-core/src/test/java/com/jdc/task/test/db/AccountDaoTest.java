package com.jdc.task.test.db;

import static com.jdc.task.test.HasRecordComponent.hasRecordComponent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.jdc.task.model.dao.AccountDao;
import com.jdc.task.model.db.AccountDb;
import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.form.AccountForm;
import com.jdc.task.model.utils.DataSourceManager;
import com.jdc.task.model.utils.DbUtils;
import com.jdc.task.test.TestDataSource;

public class AccountDaoTest {

	private AccountDao dao;

	@BeforeAll
	static void beforeAll() {
		DataSourceManager.init(new TestDataSource());
	}

	@BeforeEach
	void before() {
		dao = new AccountDb();
		DbUtils.truncate("account", "project", "task", "task_date");
		
		DbUtils.execute(
				"insert into account (name, email, role, password, entry_date) values ('Thidar', 'thidar@gmail.com', 'Manager', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nilar', 'nilar@gmail.com', 'Member', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Ko Ko', 'koko@gmail.com', 'Member', 'password', '2022-11-01');",
				"insert into account (name, email, role, password, entry_date) values ('Nyi Nyi', 'nyinyi@gmail.com', 'Member', 'password', '2022-11-01');"
				);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/account/test_create_success.tsv", delimiter = '\t')
	void test_create_success(int id, String name, Role role, String email, String password) {
		
		// Prepare Input
		var form = new AccountForm(name, role, email, password);
		
		// Test Execution
		var result = dao.create(form);
		
		// Check Result
		assertEquals(id, result);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/account/test_create_error.tsv", delimiter = '\t')
	void test_create_error(String name, Role role, String email, String password, int messages) {
		// Prepare Input
		var form = new AccountForm(name, role, email, password);
		
		// Test Execution
		var exception = assertThrows(TaskAppException.class, () -> dao.create(form));
		
		// Check Result
		assertThat(exception.getMessages(), hasSize(messages));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/account/test_update.tsv", delimiter = '\t')
	void test_update(int id, String name, Role role, String email) {
		// Prepare Input
		var form = AccountForm.forUpdate(name, role, email);
		
		// Test Execution
		dao.update(id, form);
		
		// Check Result
		var result = dao.findById(id);
		
		assertThat(result, allOf(
				hasRecordComponent("id", is(id)),
				hasRecordComponent("name", is(name)),
				hasRecordComponent("role", is(role)),
				hasRecordComponent("email", is(email))
				));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/account/test_find_by_id.tsv", delimiter = '\t')
	void test_find_by_id(int id, String name, Role role, String email, LocalDate entryDate) {
		
		var result = dao.findById(id);
		
		assertThat(result, allOf(
				hasRecordComponent("id", is(id)),
				hasRecordComponent("name", is(name)),
				hasRecordComponent("role", is(role)),
				hasRecordComponent("email", is(email)),
				hasRecordComponent("entryDate", is(entryDate))
				));
		
	}

	@ParameterizedTest
	@ValueSource(ints = {
			5, 6, 7, 0
	})
	void test_find_by_id_not_found(int id) {
		var result = dao.findById(id);
		assertNull(result);
	}

	@ParameterizedTest
	@CsvSource({
		",,4",
		"Member,,3",
		"Manager,,1",
		"Member,thi,0",
		"Manager,thi,1"
	})
	void test_search(Role role, String name, int expected) {
		
		var result = dao.search(role, name);
		assertThat(result, hasSize(expected));
	}
}
