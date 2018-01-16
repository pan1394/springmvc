package com.yilin.www.spring.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yilin.www.spring.mvc.utils.CommonUtils;

@Aspect
@Component
public class TimeConsumerAOP {

	private static Logger logger = LoggerFactory.getLogger(TimeConsumerAOP.class);
	public TimeConsumerAOP(){
		logger.debug("AOP loaded."); 
	}
	@Around("execution(* com.yilin.www.spring.mvc.controller.*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable{
		long start = System.currentTimeMillis();
		Object result = point.proceed();
		long end = System.currentTimeMillis();
		logger.info("Method {} consumes {}s totally.", point.getSignature(), CommonUtils.timePeriod(end-start));
		return result;
	}
}
