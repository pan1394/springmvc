package com.yilin.www.spring.mvc.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@ExceptionHandler({MySampleRuntimeException.class})
	public ResponseEntity handler(HttpServletRequest request, HttpServletResponse response, MySampleRuntimeException e)
			throws IOException {
		logger.error("HttpRequest error:{}", request.getRequestURL());
        logger.error("HttpRequest error stack :" ,e );
		return new ResponseEntity(e.getLocalizedMessage(),HttpStatus.SERVICE_UNAVAILABLE);
	}
}
