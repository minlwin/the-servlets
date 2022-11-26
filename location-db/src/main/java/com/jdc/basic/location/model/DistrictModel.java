package com.jdc.basic.location.model;

import java.util.List;

import com.jdc.basic.location.model.dto.District;
import com.jdc.basic.location.model.form.DistrictForm;

public interface DistrictModel {

	// Create Method
	District create(DistrictForm form);
	
	// Update Method
	District update(int id, DistrictForm form);
	
	// Find By Id
	District findById(int id);
	
	// Search Method - state id, name (like search)
	List<District> search(int stateId, String name);
}
