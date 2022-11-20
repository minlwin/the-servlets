package com.jdc.basic.location.model.dto;

import com.jdc.basic.location.model.form.StateForm;

public record State(
		int id,
		String name,
		String region,
		String capital
		) {

	public static State from(int id, StateForm form) {
		return new State(id, form.name(), form.region(), form.capital());
	}
}
