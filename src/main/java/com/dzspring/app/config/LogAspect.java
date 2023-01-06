package com.dzspring.app.config;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("execution(public * com.dzspring.app..*.*(..))")
	public void publicTarget() { }
	
	@Around("publicTarget()")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug(joinPoint.getSignature().toLongString());
		Arrays.asList(joinPoint.getArgs()).forEach(arg -> logger.debug("[Arg]: " + arg));
		Object result = joinPoint.proceed();
		logger.debug("[return] " + result);
		return result;
	}
}
