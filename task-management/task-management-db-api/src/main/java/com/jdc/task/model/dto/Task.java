package com.jdc.task.model.dto;

import java.time.LocalDate;

public record Task(
		int id,
		String taskName,
		LocalDate dateFrom,
		LocalDate dateTo,
		Status status,
		String remark,
		int taskOwnerId,
		String taskOwnerName,
		int projectId,
		String projectName,
		String description,
		int projectOwnerId,
		String projectOwnerName
		) {
	
	public enum Status {
		Open, Started, Late, Finished
	}

}
