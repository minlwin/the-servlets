package com.jdc.basic.location.model;

import java.util.List;

import com.jdc.basic.location.model.dto.State;
import com.jdc.basic.location.model.form.StateForm;

public interface StateModel {

	State create(StateForm form);

	State findById(int id);

	State update(int id, StateForm form);

	List<State> search(String region, String name);

	long findCountByName(String name);
	
	int upload(List<StateForm> forms);

}
