package com.yilin.www.spring.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;

@RestController
public class TestController {

	@GetMapping("/hello/{name}")
	@SystemControllerLog(description="test_for_logsystem")
	public String helloWorld(@PathVariable String name){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new StringBuffer("Hello, Welcome my friend ").append(name).toString();
	}
}
