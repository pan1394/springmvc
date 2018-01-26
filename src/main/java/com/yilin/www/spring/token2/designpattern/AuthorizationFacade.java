package com.yilin.www.spring.token2.designpattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
@Component
public class AuthorizationFacade implements Authorization {

	@Override
	public String getAuthorizationToken(HttpServletRequest request) {
		AuthorizationContext context = AuthorizationContext.getContext();
		String res = context.getAuthorizationToken(new HeaderFetcher(), request);
		res  =  (res != null) ? res : context.getAuthorizationToken(new CookieFetcher(), request); 
		return res;
	}

	
}
