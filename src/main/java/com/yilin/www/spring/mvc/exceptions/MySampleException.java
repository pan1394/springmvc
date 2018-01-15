package com.yilin.www.spring.mvc.exceptions;

public class MySampleException extends Exception {
 
	private static final long serialVersionUID = 1L;

	public MySampleException(){
		super();
	}
	
	public MySampleException(String msg){
		super(msg);
	}
}
