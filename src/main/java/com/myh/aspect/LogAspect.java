package com.myh.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect { 
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
    @Pointcut("execution(* com.myh.controller.*.*(..))")
	public void log() {}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getRequestURL().toString();
		String ip = request.getRemoteAddr();
		//获取类名以及方法名  =全路径
	    String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
		//请求参数
	    Object[] args = joinPoint.getArgs();
	    //构造对象
	    RequestLog requestLog=new RequestLog(url, ip, classMethod, args);
		logger.info("Request:{}",requestLog);
	}
	
	@After("log()")
	public void doAfter() {
		logger.info("-------------doAfter------");
	}
	
	@AfterReturning(returning = "result",pointcut = "log()")
	public void doAfterReturning(Object result) {
		logger.info("result:{}---",result);
	}
	  /**
     * 内部类:用于存放请求信息,用日志的方式将其记录下来
     */
    private class RequestLog{
         private String url;
         private String ip; 
         private String classMethod;
         private Object[] args;

         public RequestLog(String url, String ip, String classMethod, Object[] args) {
              this.url = url;
              this.ip = ip;
              this.classMethod = classMethod;
              this.args = args;
         }

         @Override
         public String toString() {
              return "RequestLog{" +
                      "url='" + url + '\'' +
                      ", ip='" + ip + '\'' +
                      ", classMethod='" + classMethod + '\'' +
                      ", args=" + Arrays.toString(args) +
                      '}';
         }
    }
	
}
