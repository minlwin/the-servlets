package com.jdc.basic.location.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.basic.location.model.StateModel;
import com.jdc.basic.location.model.dto.State;
import com.jdc.basic.location.model.form.StateForm;
import com.jdc.basic.location.model.impl.StateModelImpl;
import com.jdc.basic.location.util.TestUtils;


@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class StateModelTest {
	
	private StateModel model;
		
	// Before each 
	@BeforeEach
	void prepareTest() {
		
		// Truncate table
		TestUtils.truncate("state");
		
		// Prepare Data for Update Method and Find By Id Method
		TestUtils.execute("insert into state(name, region, capital) values ('Ayeyarwady Region', 'Lower', 'Pathein');");
		TestUtils.execute("insert into state(name, region, capital) values ('Bago Region', 'Lower', 'Bago');");
		TestUtils.execute("insert into state(name, region, capital) values ('Chin State', 'West', 'Hakha');");
		TestUtils.execute("insert into state(name, region, capital) values ('Kachin State', 'North', 'Myitkyina');");
		
		// Create Model Object
		model = new StateModelImpl(TestUtils.getDataSource());
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/state/test_create_success.txt", delimiter = '\t')
	@Order(1)
	void test_create_success(int id, String name, String region, String capital) {
		// Prepare Inputs (Parameters)
		StateForm form = new StateForm(name, region, capital);
		
		// Execute target method
		State state = model.create(form);
		
		// Check Result
		assertNotNull(state);
		assertEquals(name, state.name());
		assertEquals(region, state.region());
		assertEquals(capital, state.capital());
		assertEquals(id, state.id());
	}
	
	@Test
	@Order(2)
	@CsvSource(value = {
			",region,capital,Please enter name of state.",
			"name,,capital,Please enter region of state.",
			"name,region,,Please enter capital of state.",
	})
	void test_create_error(String name, String region, String capital, String message) {
		
		var form = new StateForm(name, region, capital);
		
		var exception = assertThrows(IllegalArgumentException.class, () -> model.create(form));
		
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	@Order(3)
	void test_update_success() {
		
	}
	
	@Test
	@Order(4)
	void test_find_by_id_found() {
		
	}
	
	@Test
	@Order(5)
	void test_find_by_id_not_found() {
		
	}
	
	@Test
	@Order(6)
	void test_search() {
		
	}
	
}
