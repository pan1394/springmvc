package com.yilin.www.spring.mvc.utils;

public class StringUtils {

	public static boolean isBlank(String str) { 
		return (str == null);
	}

	public static boolean endsWithIgnoreCase(String key, String string) {
		return string.toLowerCase().endsWith(key.toLowerCase());
	}

	public static Object abbr(Object object, int i) {
		return null;
	}

}
