package com.yilin.www.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
class IndexPageController {

	@Autowired
	private HttpServletResponse resp;
	
	@GetMapping("/index")
	public void index() throws IOException {
		resp.sendRedirect("swagger/index.html"); 
	}
}
