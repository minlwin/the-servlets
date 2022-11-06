package com.jdc.task.model.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jdc.task.model.dto.Account.Role;
import com.jdc.task.model.dto.Task.Status;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}
	
	public static boolean isNotEmpty(String str) {
		return null != str && !str.isEmpty();
	}

	public static Object lower(String str) {
		return str.toLowerCase();
	}

	public static String lowerLike(String str) {
		return str.toLowerCase().concat("%");
	}
	
	public static int parseInt(String str) {
		return isNotEmpty(str) ? Integer.parseInt(str) : 0;
	}
	
	public static LocalDate parseDate(String str) {
		return isNotEmpty(str) ? LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
	}
	
	public static Role parseRole(String str) {
		return isNotEmpty(str) ? Role.valueOf(str) : null;
	}
	
	public static Status parseStatus(String str) {
		return isNotEmpty(str) ? Status.valueOf(str) : null;
	}
	
	public static Boolean parseBoolean(String str) {
		return isNotEmpty(str) ? Boolean.parseBoolean(str) : null;
	}

}
