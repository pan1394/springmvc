package com.yilin.www.spring.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeConsumerAOP {

	public TimeConsumerAOP(){
		System.out.println("AOP loaded.");
	}
	@Around("execution(* com.yilin.www.spring.mvc.controller.*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable{
		double start = System.currentTimeMillis();
		Object result = point.proceed();
		double end = System.currentTimeMillis();
		String str = String.format("Method %s consumes %.2f seconds totally.", point.getSignature(), (end - start)/1000);
		System.out.println(str);
		return result;
	}
}
