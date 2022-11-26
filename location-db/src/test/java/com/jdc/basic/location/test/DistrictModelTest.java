package com.jdc.basic.location.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;

import com.jdc.basic.location.model.DistrictModel;
import com.jdc.basic.location.model.form.DistrictForm;
import com.jdc.basic.location.model.impl.DistrictModelImpl;
import com.jdc.basic.location.util.TestUtils;

public class DistrictModelTest {
	
	private DistrictModel model;

	// Before each 
	@BeforeEach
	void prepareTest() {
		
		// Truncate table
		TestUtils.truncate("state", "district");
		
		// Prepare State Data for References
		TestUtils.execute("insert into state(name, region, capital) values ('Ayeyarwady Region', 'Lower', 'Pathein');");
		TestUtils.execute("insert into state(name, region, capital) values ('Bago Region', 'Lower', 'Bago');");
		TestUtils.execute("insert into state(name, region, capital) values ('Chin State', 'West', 'Hakha');");
		TestUtils.execute("insert into state(name, region, capital) values ('Kachin State', 'North', 'Myitkyina');");
		
		// Prepare District Data for update and find by id
		TestUtils.execute("insert into district(state_id, name) values (1, 'Pathein')");
		TestUtils.execute("insert into district(state_id, name) values (1, 'Ma-ubin')");
		TestUtils.execute("insert into district(state_id, name) values (1, 'Phyapon')");
		TestUtils.execute("insert into district(state_id, name) values (2, 'Bago')");
		TestUtils.execute("insert into district(state_id, name) values (2, 'Pyay')");
		TestUtils.execute("insert into district(state_id, name) values (3, 'Falam')");
		
		// Create Model Object
		model = new DistrictModelImpl(TestUtils.getDataSource());
	}
	
	@ParameterizedTest
	void test_find_by_id(int id, String name, int stateId, String stateName, String region) {

		var result = model.findById(id);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var state = result.state();
		assertNotNull(state);
		assertEquals(stateId, state.id());
		assertEquals(stateName, state.name());
		assertEquals(region, state.region());
	}
	
	@ParameterizedTest
	void test_find_by_id_not_found(int id) {
		var result = model.findById(id);
		assertNull(result);
	}
	
	@ParameterizedTest
	void test_validation(String name, Integer stateId, String message) {
		var form = new DistrictForm(name, stateId);
		var exception = assertThrows(IllegalArgumentException.class, () -> model.create(form));
		assertEquals(message, exception.getMessage());
	}

	@ParameterizedTest
	void test_create(int id, String name, int stateId, String stateName, String region) {
		var form = new DistrictForm(name, stateId);
		
		var result = model.create(form);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var state = result.state();
		assertNotNull(state);
		assertEquals(stateId, state.id());
		assertEquals(stateName, state.name());
		assertEquals(region, state.region());
	}
	
	@ParameterizedTest
	void test_update(int id, String name, int stateId, String stateName, String region) {
		var form = new DistrictForm(name, stateId);
		
		var result = model.update(id, form);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var state = result.state();
		assertNotNull(state);
		assertEquals(stateId, state.id());
		assertEquals(stateName, state.name());
		assertEquals(region, state.region());
	}
	
	@ParameterizedTest
	void test_search(Integer stateId, String name, int size) {
		var list = model.search(stateId, name);
		assertEquals(size, list.size());
	}
}
