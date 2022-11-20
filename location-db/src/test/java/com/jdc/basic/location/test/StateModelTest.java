package com.jdc.basic.location.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.basic.location.model.StateModel;
import com.jdc.basic.location.model.dto.State;
import com.jdc.basic.location.model.form.StateForm;


@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class StateModelTest {
	
	private StateModel model;
	
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
