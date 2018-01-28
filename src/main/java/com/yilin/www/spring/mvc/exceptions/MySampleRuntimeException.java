package com.yilin.www.spring.mvc.exceptions;

public class MySampleRuntimeException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;

	public MySampleRuntimeException(){
		super();
	}
	
	public MySampleRuntimeException(String msg){
		super(msg);
	}
}
