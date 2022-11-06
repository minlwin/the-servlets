package com.jdc.task.model;

import java.util.List;

public class TaskAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<String> messages;

	public TaskAppException(List<String> messages) {
		super();
		this.messages = messages;
	}

	public TaskAppException(List<String> messages, Throwable cause) {
		super(cause);
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
}
