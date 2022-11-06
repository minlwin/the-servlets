package com.jdc.task.model.dto.form;

import java.time.LocalDate;

public record ProjectForm(
		String name,
		int ownerId,
		String description,
		LocalDate startDate,
		boolean finished
		) {

}
