package com.jdc.task.model.dto.form;

import com.jdc.task.model.dto.Account.Role;

public record AccountForm(
		String name,
		Role role,
		String email,
		String password
		) {

	public static AccountForm forUpdate(String name, Role role, String email) {
		return new AccountForm(name, role, email, null);
	}
}
