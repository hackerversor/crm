package com.common.crm.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.common.crm.util.RequestUtil;

@Component
public class SessionInterceptor implements HandlerInterceptor{
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private SessionManager sessionManager;
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	/*	ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)arg1);
		byte[] content = wrapperResponse.getContent();
		String str = new String(content, "UTF-8"); 
		logger.info("ret data is " +  str );*/
	}
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		resp.setContentType("text/html;charset=UTF-8");
//		String url = req.getRequestURL().toString();
//		String params = RequestUtil.getParamValue(req);
		
        return sessionManager.sessionCheck(req, resp);
	}

}
