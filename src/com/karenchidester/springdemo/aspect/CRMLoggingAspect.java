package com.karenchidester.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.karenchidester.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.karenchidester.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.karenchidester.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint jp) {
		//display method call
		String theMethod = jp.getSignature().toShortString();
		myLogger.info("====>> in @Before: calling method: " + theMethod);
		
		//display method arguments
		Object[] args = jp.getArgs();
		for (Object arg : args) {
			myLogger.info("====>>> argument: " + arg);
		}
	}
	
	//add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult")
	public void afterReturning(JoinPoint jp, Object theResult){
		//display method and data returned
		String theMethod = jp.getSignature().toShortString();
		myLogger.info("====>> in @AfterReturning: calling method: " + theMethod);
		
		myLogger.info("====>> result: " + theResult);
	}
	
}
