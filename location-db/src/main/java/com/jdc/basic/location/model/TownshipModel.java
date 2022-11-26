package com.jdc.basic.location.model;

import java.util.List;

import com.jdc.basic.location.model.dto.Township;
import com.jdc.basic.location.model.form.TownshipForm;

public interface TownshipModel {

	Township create(TownshipForm form);
	
	Township update(int id, TownshipForm form);
	
	Township findById(int id);
	
	List<Township> search(int districtId, String name);
}
