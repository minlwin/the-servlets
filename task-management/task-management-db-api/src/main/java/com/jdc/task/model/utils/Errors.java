package com.jdc.task.model.utils;

import java.util.List;

import com.jdc.task.model.TaskAppException;

public abstract class Errors {

	public static void make(List<String> messages) {
		throw new TaskAppException(messages);
	}
	
}
