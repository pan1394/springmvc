package com.yilin.www.spring.token2.designpattern;

import javax.servlet.http.HttpServletRequest;

import com.yilin.www.spring.mvc.utils.Constants;

public class HeaderFetcher implements Authorization {

	@Override
	public String getAuthorizationToken(HttpServletRequest request) {
		return request.getHeader(Constants.AUTHORIZATION);
	}

}
