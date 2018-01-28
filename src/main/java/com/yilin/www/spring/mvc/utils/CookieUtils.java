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
		 cookie.setMaxAge(30*60);
	     res.addCookie(cookie);
	 }
	 
	 /**
	  * maxage set 0.
	  * @param cookie - the one need to be deleted
	  * @param res
	  */
	 public static void delete(Cookie cookie, HttpServletResponse res) { 
		//if(cookie == null) throw new RuntimeException("Not found the cookie.");
		cookie.setMaxAge(0); 
		cookie.setValue(null); 
		res.addCookie(cookie);
	 }
}
