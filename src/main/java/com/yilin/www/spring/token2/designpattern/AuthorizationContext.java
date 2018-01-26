package com.yilin.www.spring.token2.designpattern;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationContext {

	private static AuthorizationContext instance= new AuthorizationContext();
	   
	private AuthorizationContext(){
		
	}
	
	public static AuthorizationContext getContext(){ 
		return instance;
	} 
	
	public String getAuthorizationToken(Authorization authorization, HttpServletRequest request) { 
		return authorization.getAuthorizationToken(request);
	}

	
}
