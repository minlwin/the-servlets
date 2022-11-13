package com.jdc.basic.location.model;

import com.jdc.basic.location.model.dto.State;
import com.jdc.basic.location.model.form.StateForm;

public interface StateModel {

	State create(StateForm form);

}
