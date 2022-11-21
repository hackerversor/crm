package com.common.crm.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.common.crm.util.RequestUtil;
@Aspect
@Component
public class AopInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void annotationPointCut(){};
	
	@Around("annotationPointCut()")
	public Object interceptor(ProceedingJoinPoint pjp) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		String params = RequestUtil.getParamValue(request);
		String url = request.getRequestURI();
		logger.info("req url is == [ " + url +" ]");
		logger.info("params  is == [ " + params +" ]");
		Object obj = pjp.proceed();
		if( obj != null && obj instanceof Map) {
			Map map = (Map)obj;
			HashMap logMap = new HashMap();
			 Iterator it = map.entrySet().iterator(); 
			 while (it.hasNext()) {  
				 Map.Entry entry = (Map.Entry) it.next();  
				 Object key = entry.getKey();  
				 String value = entry.getValue()+"";  
				 if(value.length() >128) {
					 logMap.put(key, value.substring(0, 128));
				 }else {
					 logMap.put(key, value);
				 }
			 }
			 logger.info("返回的数据 == [ "+logMap + " ] ");
		}else if(obj != null ){
			logger.info("返回的数据 == [ "+obj.toString()+ " ] ");
		}
		return obj;
	}
}
