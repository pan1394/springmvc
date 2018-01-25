package com.yilin.www.spring.mvc.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.mvc.derby.DerbyDB;
import com.yilin.www.spring.mvc.exceptions.MySampleException;
import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;
import com.yilin.www.spring.mvc.utils.UUIDUtils;

@RestController
public class TestController {

	 Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	@Autowired(required=false) 
	private HttpServletResponse res;
	
	public void setRes(HttpServletResponse res) {
		this.res = res;
	}


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
	
	
	 @PostMapping(value="/response/headers")  
	 public ResponseEntity<String> responseEntityCustomHeaders() {  
        HttpHeaders headers = new HttpHeaders();   
        
        Cookie cookie = new Cookie("psessionId", UUIDUtils.get()); 
        headers.setContentType(MediaType.TEXT_PLAIN);  
        headers.add("soeid", "yp15911");
        res.addCookie(cookie);
        return new ResponseEntity<String>("The String ResponseBody with custom header Content-Type=text/plain", headers, HttpStatus.OK);  
	  }  
	 
	 @SuppressWarnings("unused")
	 @GetMapping(value="/throwable")  
	 public ResponseEntity<String> throwException() throws MySampleException {
		if(true){
			throw new MySampleException("my exception");
		}
        return new ResponseEntity<String>("I cannot be reached", HttpStatus.OK);  
	  } 
	 
	 	@Autowired DerbyDB db;
	 	@GetMapping("/initDB")
		@SystemControllerLog(description="test_for_logsystem")
		public String initDB() throws IOException{
	 		Resource resource = new ClassPathResource("sample.sql");
	 		File f = resource.getFile(); 
	 		String table = "token_t";
	 		logger.info("check..."); 
	 		db.init(f,  table);  
			return new StringBuffer("done ").toString();
		}
}
