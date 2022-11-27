package com.jdc.basic.location.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jdc.basic.location.model.TownshipModel;
import com.jdc.basic.location.model.form.TownshipForm;
import com.jdc.basic.location.model.impl.TownshipModelImpl;
import com.jdc.basic.location.util.TestUtils;

public class TownshipModelTest {
	
	private TownshipModel model;

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
		
		// Prepare District Data
		TestUtils.execute("insert into district(state_id, name) values (1, 'Pathein')");
		TestUtils.execute("insert into district(state_id, name) values (1, 'Ma-ubin')");
		TestUtils.execute("insert into district(state_id, name) values (1, 'Phyapon')");
		TestUtils.execute("insert into district(state_id, name) values (2, 'Bago')");
		TestUtils.execute("insert into district(state_id, name) values (2, 'Pyay')");
		TestUtils.execute("insert into district(state_id, name) values (3, 'Falam')");

		// Prepare Township Data for update and find by id
		TestUtils.execute("insert into township (district_id, name) values (1, 'Pathein');");
		TestUtils.execute("insert into township (district_id, name) values (1, 'Kangyidaunt');");
		TestUtils.execute("insert into township (district_id, name) values (1, 'Ngapudaw');");
		TestUtils.execute("insert into township (district_id, name) values (2, 'Ma-ubin');");
		TestUtils.execute("insert into township (district_id, name) values (2, 'Danubyu');");
		TestUtils.execute("insert into township (district_id, name) values (3, 'Phyapon');");
		
		// Create Model Object
		model = new TownshipModelImpl(TestUtils.getDataSource());
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/township/test_find_by_id.txt", delimiter = '\t')
	void test_find_by_id(int id, String name, int dId, String dName, int sId, String sName, String region, String capital) {
		
		var result = model.findById(id);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var district = result.district();
		assertNotNull(district);
		assertEquals(dId, district.id());
		assertEquals(dName, district.name());
		
		var state = district.state();
		assertNotNull(state);
		assertEquals(sId, state.id());
		assertEquals(sName, state.name());
		assertEquals(region, state.region());
		assertEquals(capital, state.capital());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 7})
	void test_find_by_id_not_found(int id) {
		var result = model.findById(id);
		assertNull(result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0,,Please select district for township.",
		"1,,Please enter district name."
	})
	void test_validation(int districtId, String name, String message) {
		var form = new TownshipForm(name, districtId);
		var exception = assertThrows(IllegalArgumentException.class, () -> model.create(form));
		assertEquals(message, exception.getMessage());
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/township/test_create.txt", delimiter = '\t')
	void test_create(int id, String name, int dId, String dName, int sId, String sName, String region, String capital) {
		var form = new TownshipForm(name, dId);
		var result = model.create(form);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var district = result.district();
		assertNotNull(district);
		assertEquals(dId, district.id());
		assertEquals(dName, district.name());
		
		var state = district.state();
		assertNotNull(state);
		assertEquals(sId, state.id());
		assertEquals(sName, state.name());
		assertEquals(region, state.region());
		assertEquals(capital, state.capital());		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/township/test_update.txt", delimiter = '\t')
	void test_update(int id, String name, int dId, String dName, int sId, String sName, String region, String capital) {
		var form = new TownshipForm(name, dId);
		var result = model.update(id, form);
		
		assertNotNull(result);
		assertEquals(id, result.id());
		assertEquals(name, result.name());
		
		var district = result.district();
		assertNotNull(district);
		assertEquals(dId, district.id());
		assertEquals(dName, district.name());
		
		var state = district.state();
		assertNotNull(state);
		assertEquals(sId, state.id());
		assertEquals(sName, state.name());
		assertEquals(region, state.region());
		assertEquals(capital, state.capital());		
	}
	
	@ParameterizedTest
	@CsvSource({
		"0,,6",
		"1,,3",
		"2,,2",
		"3,,1",
		"4,,0",
		"1,pathein,1",
		"1,phya,1",
		"1,mau,1",
	})
	void test_search(int districtId, String keyword, int size) {
		var result = model.search(districtId, keyword);
		assertEquals(size, result.size());
	}
}
