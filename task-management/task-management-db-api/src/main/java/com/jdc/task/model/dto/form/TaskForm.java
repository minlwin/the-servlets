package com.jdc.task.model.dto.form;

import java.time.LocalDate;

import com.jdc.task.model.dto.Task.Status;

public record TaskForm(
		String name,
		int projectId,
		int ownerId,
		LocalDate dateFrom,
		LocalDate dateTo,
		Status status,
		String remark
		) {

}
