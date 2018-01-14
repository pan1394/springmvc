package com.yilin.www.spring.mvc.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String get() {
		String uuid = UUID.randomUUID().toString();  
        uuid = uuid.replace("-", "");             
        return uuid;
	}
}
