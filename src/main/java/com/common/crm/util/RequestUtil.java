package com.common.crm.util;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
	private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	/**
	 * 按key返回 req中的cookie,没有则返回null,如果有多个相同的key则返回第一个
	 * @param HttpServletRequest req
	 * @param String key
	 * @return String value
	 */
	public static String getCookieByKey(HttpServletRequest req,String key){
		String value = null;
		Cookie[]  cookies = req.getCookies();
		if(cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
//			if(logger.isErrorEnabled()){
//				logger.error("cookie name is " + cookies[i].getName() + " cookie value is " + cookies[i].getValue());
//			}
			String name = cookies[i].getName();
			if(key.equals(name)){
				value = cookies[i].getValue();
				break;
			}
		}
		return value;
	}
    
    /**
     * 获取所有的参数
     * @param request
     * @return
     */
    public static String getParamValue(HttpServletRequest request){
        StringBuffer  buffer = new StringBuffer("参数为===");
        Enumeration<String> enu =  request.getParameterNames();
        while(enu.hasMoreElements()){
            String paramBame = (String)enu.nextElement();
            String paramValue = request.getParameter(paramBame);
            buffer.append(paramBame + ":" + paramValue + ";");
        }
        return buffer.toString();
    }
}
