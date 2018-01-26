package com.yilin.www.spring.token2.designpattern;

import javax.servlet.http.HttpServletRequest;

public interface Authorization {

	public String getAuthorizationToken(HttpServletRequest request);
}
