package com.jdc.task.model.dto;

import java.time.LocalDate;

public record Account(
	int id,
	String name,
	Role role,
	String email,
	LocalDate entryDate
		) {

	public enum Role {
		Manager, Member
	}
	
	public boolean isManager() {
		return role == Role.Manager;
	}
}
