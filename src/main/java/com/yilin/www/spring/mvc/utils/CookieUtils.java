package com.yilin.www.spring.mvc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static Cookie getCookieByName(String name, Cookie[] cookies){ 
		 if(name == null) return null;
		 if(cookies == null) return null;
		 Cookie cookie = null;
		 for(Cookie ck : cookies){
			 if(name.equals(ck.getName())){
				 cookie = ck;
				 break;
			 }
		 }
		 return cookie;
	 }
	 
	/**
	 * default path - /
	 * default maxage '-1' 
	 * @param name - cookie name
	 * @param value - cookie value
	 */
	 public static void saveAsSimpleCookie(String name,String value, HttpServletResponse res){
	    	Cookie cookie = new Cookie(name, value);  
			 cookie.setPath("/");
			 cookie.setMaxAge(-1);
		     res.addCookie(cookie);
	 }
}
