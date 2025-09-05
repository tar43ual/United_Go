package com.ual.aop;

import java.text.DateFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class LoggingAspect {
	
	@AfterReturning(pointcut = "execution(* com.ual.controller.BookingLogicController.*(..))")
	public void logAfterReturningDetails(JoinPoint joinPoint) {
		log.info("In AfterReturning Advice with return value, Joinpoint signature :{}", joinPoint.getSignature());
//		System.out.println(result);
		long time = System.currentTimeMillis();
		String date = DateFormat.getDateTimeInstance().format(time);
		log.info("Report generated at time:{}", date);
	} 
	
	@AfterReturning(pointcut = "execution(* com.ual.controller.FlightLogicController.*(..))")
	public void logAfterReturningDetails1(JoinPoint joinPoint) {
		log.info("In AfterReturning Advice with return value, Joinpoint signature :{}", joinPoint.getSignature());
//		System.out.println(result);
		long time = System.currentTimeMillis();
		String date = DateFormat.getDateTimeInstance().format(time);
		log.info("Report generated at time:{}", date);
	}
	
	@AfterThrowing(pointcut = "execution(* com.ual.*.*(..))", throwing = "exception")
	public void EmployeeLogger(JoinPoint joinPoint, Exception exception) {
		log.info("In After throwing Advice, Joinpoint signature :{}", joinPoint.getSignature());
		long time = System.currentTimeMillis();
		String date = DateFormat.getDateTimeInstance().format(time);
		log.info("Report generated at time:{}", date);
		log.error(exception.getMessage(), exception);
	}
}
