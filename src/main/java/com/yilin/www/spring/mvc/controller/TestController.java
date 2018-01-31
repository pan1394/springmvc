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
import com.yilin.www.spring.mvc.exceptions.MySampleRuntimeException;
import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;
import com.yilin.www.spring.mvc.model.ResultModel;
import com.yilin.www.spring.mvc.utils.UUIDUtils;
import com.yilin.www.spring.token2.CurrentUser;
import com.yilin.www.spring.vo.Student;
 

@RestController
public class TestController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	@Autowired 
	private HttpServletResponse res;
  
	@GetMapping("/hello/{name}")
	@SystemControllerLog(description="test_for_logsystem")
	public ResponseEntity<ResultModel> helloWorld(@PathVariable String name){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		ResultModel body = ResultModel.ok(new StringBuilder("Hello, Welcome my friend ").append(name).toString()); 
		return new ResponseEntity<ResultModel>(body, HttpStatus.OK);
	}
	
	
	 @PostMapping(value="/response/headers")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<String> responseEntityCustomHeaders() {  
        HttpHeaders headers = new HttpHeaders();   
        
        Cookie cookie = new Cookie("psessionId", UUIDUtils.get()); 
        headers.setContentType(MediaType.TEXT_PLAIN);  
        headers.add("soeid", "yp15911");
        res.addCookie(cookie);
        return new ResponseEntity<String>("The String ResponseBody with custom header Content-Type=text/plain", headers, HttpStatus.OK);  
	  }  
	 
	 /**
	  * MySampleRuntimeException is captured by handler in {@link WebExceptionHandler#handler}
	  * @return
	  * @throws MySampleRuntimeException
	  */
	 @SuppressWarnings("unused")
	 @GetMapping(value="/throwable")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<ResultModel> methodThrowsException() throws MySampleRuntimeException{
		if(true){
			throw new MySampleRuntimeException("my custom run time exception");
		}
		String returnValue = "I cannot be reached"; 
        return new ResponseEntity<ResultModel>(ResultModel.ok(returnValue), HttpStatus.OK);  
	 } 
	 
	 
	 @GetMapping(value="/currentUser")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<ResultModel> customArg(@CurrentUser Student u) throws MySampleRuntimeException{
		// Log.info("current user, id {}, name {}", u.getId(), u.getName());
        return new ResponseEntity<ResultModel>(ResultModel.ok(u), HttpStatus.OK);  
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
