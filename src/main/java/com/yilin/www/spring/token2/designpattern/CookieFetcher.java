package com.yilin.www.spring.token2.designpattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.yilin.www.spring.mvc.utils.Constants;
import com.yilin.www.spring.mvc.utils.CookieUtils;

public class CookieFetcher implements Authorization {

	@Override
	public String getAuthorizationToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
    	Cookie cookie = CookieUtils.getCookieByName(Constants.AUTHORIZATION, cookies);
    	String authorization = cookie==null? null : cookie.getValue();
		return authorization;
	}

}
