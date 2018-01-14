package com.yilin.www.spring.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeConsumerAOP {

	private static Logger logger = LoggerFactory.getLogger(TimeConsumerAOP.class);
	public TimeConsumerAOP(){
		logger.debug("AOP loaded."); 
	}
	@Around("execution(* com.yilin.www.spring.mvc.controller.*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable{
		double start = System.currentTimeMillis();
		Object result = point.proceed();
		double end = System.currentTimeMillis();
		logger.info("Method {} consumes {} seconds totally.", point.getSignature(), (end - start)/1000);
		return result;
	}
}
