package com.jdc.basic.location.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
	
	@ParameterizedTest
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
	
	@ParameterizedTest
	@Order(3)
	@CsvFileSource(resources = "/state/test_update_success.txt", delimiter = '\t')
	void test_update_success(int id, String name, String region, String capital) {
		
		var form = new StateForm(name, region, capital);
		
		var state = model.update(id, form);
		
		assertNotNull(state);
		assertEquals(name, state.name());
		assertEquals(region, state.region());
		assertEquals(capital, state.capital());
		assertEquals(id, state.id());
	}
	
	@ParameterizedTest
	@Order(4)
	@CsvFileSource(resources = "/state/test_find_by_id_found.txt", delimiter = '\t')
	void test_find_by_id_found(int id, String name, String region, String capital) {
		
		var result = model.findById(id);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		assertEquals(region, result.region());
		assertEquals(capital, result.capital());
	}
	
	@ParameterizedTest
	@Order(5)
	@ValueSource(ints = {0, 5, 6})
	void test_find_by_id_not_found(int id) {
		var result = model.findById(id);
		assertNull(result);
	}
	
	@ParameterizedTest
	@Order(6)
	@CsvSource({
		",,4",
		"Lower,,2",
		"Lower,Bago,1",
		"Lower,Kachin,0",
	})
	void test_search(String region, String name, int count) {
		var result = model.search(region, name);
		assertEquals(count, result.size());
	}
	
	@ParameterizedTest
	@Order(7)
	@CsvSource({
		"Ayeyarwady Region,1",
		"Bago Region,1",
		"Chin State,1",
		"Kachin State,1",
		"Kayah State,0"		
	})
	void test_find_count_by_name(String name, long count) {
		var result = model.findCountByName(name);
		assertEquals(count, result);
	}
	
	@Disabled
	@ParameterizedTest
	@Order(8) 
	@MethodSource("generateUploadArguments")
	void test_upload(List<StateForm> states) {
		var count = model.upload(states);
		assertEquals(states.size(), count);
	}
	
	static Stream<Arguments> generateUploadArguments() {
		return null;
	}
	
}
