package com.jdc.task.model.dto;

import java.time.LocalDate;

public record Project(
		int id,
		String name,
		int ownerId,
		String ownerName,
		String description,
		LocalDate start,
		boolean finished
		) {

}
