package com.yilin.www.spring.mvc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;
import com.yilin.www.spring.mvc.utils.CookieUtils;
import com.yilin.www.spring.token.TokenManager;

@RestController
@RequestMapping("/security")
public class LoginController {

	 Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	@Autowired(required=false) 
	private HttpServletResponse res;
	
	@Autowired(required=false) 
	private HttpServletRequest req;
	
	@Autowired 
	private TokenManager tokenManager;
	
	public void setRes(HttpServletResponse res) {
		this.res = res;
	}


	@GetMapping("/login/{name}/{password}")
	@SystemControllerLog(description="test_for_logsystem")
	public ResponseEntity<String> login(@PathVariable String name, @PathVariable String password){
		 int i = (int) (Math.random() * 100);
		 i =1;
		 String s = null;
		 if(i==1){
			 logger.info("{} login success.", name);
			 String token = tokenManager.generateToken();
			 tokenManager.save(token);
			 Cookie cookie = new Cookie("psessionId", token);  
			 cookie.setPath("/");
			 cookie.setMaxAge(-1);
		     res.addCookie(cookie);
		     s = String.format("%s login success", name);
		 }else{
			 logger.info("{} login failed.", name);
			 s = String.format("%s login failed", name);
		 } 
		 
		 return new ResponseEntity<String>(s, HttpStatus.OK);  
	}
	@GetMapping(value="/auth") 
	@SystemControllerLog(description="test_for_logsystem")
	public ResponseEntity<String> auth(){
		 Cookie[] cks = req.getCookies();
		 String s = null;
		 String token = CookieUtils.getCookieByName("psessionId",cks).getValue();
		 if(tokenManager.auth(token)){
			 s = "you are authorized.";
		 }else{
			 s = "you have no rights.";
		 } 
		 return new ResponseEntity<String>(s, HttpStatus.OK);  
	}
	 @GetMapping(value="/logout")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<String> logout() {  
		Cookie[] cks = req.getCookies();
		Cookie cookie = CookieUtils.getCookieByName("psessionId",cks);
		cookie.setMaxAge(-1);
		cookie.setValue(null);
		String token = cookie.getValue();
		tokenManager.delete(token);
		res.addCookie(cookie);
		//logger.info("Display all students: {}", show.toString());
        return new ResponseEntity<String>("you loged out.", HttpStatus.OK);  
	  }  
  
	 
}
