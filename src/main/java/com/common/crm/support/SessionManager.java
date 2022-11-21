package com.common.crm.support;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.util.RequestUtil;
@Component
@Scope("singleton")
public class SessionManager {
	
	@Value("${session_id_key}")
	private String session_id_key;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	//private static ConcurrentHashMap<String, JSONObject> sessionMap = new ConcurrentHashMap<String, JSONObject>();
	public JSONObject getSession() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String session_id = RequestUtil.getCookieByKey(request, session_id_key);
		String session_str = stringRedisTemplate.opsForValue().get(session_id);
		JSONObject retJson = null;
		if(session_str!=null&&!"".equals(session_str)) {
			retJson = JSONObject.parseObject(session_str);
		}
		return retJson;
	}
	
	public void setSession(String key,JSONObject obj) {
		stringRedisTemplate.opsForValue().set(key,obj.toJSONString());
		stringRedisTemplate.expire(key, 2, TimeUnit.HOURS);
	}
	
	public void delSession(String key) {
		stringRedisTemplate.delete(key);
	}
	
	public void delSession() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		String session_id = RequestUtil.getCookieByKey(request, session_id_key);
		delSession(session_id);
	}
	
	/**
	 * 验证session信息
	 * @param req
	 * @param resp
	 * @return  登录的用户 返回true  未登录的用户返回false
	 * @throws IOException
	 */
	public  boolean sessionCheck(HttpServletRequest req,HttpServletResponse resp) throws  IOException {
		String session_id = RequestUtil.getCookieByKey(req, session_id_key);
		if( session_id == null ) {
			JSONObject ret = new JSONObject();
			ret.put("retCode", "unauthorized");
			ret.put("retMsg", "验证失败,请重新登录");
			resp.setStatus(401);
			resp.getOutputStream().write(ret.toJSONString().getBytes("utf-8"));
			resp.getOutputStream().close();
			return false ;
		}
		
		JSONObject session = this.getSession();
		if( session == null ) {
			JSONObject ret = new JSONObject();
			ret.put("retCode", "unauthorized");
			ret.put("retMsg", "验证失败,请重新登录");
			resp.setStatus(403);
			resp.getOutputStream().write(ret.toJSONString().getBytes("utf-8"));
			resp.getOutputStream().close();
			return false ;
		}else {
			session.put("current_time", System.currentTimeMillis());
			this.setSession(session_id, session);
		}
		return true ;
	}
	
	/**
	 * 添加登录人员信息到session
	 * @param user_object
	 */
	public void initSession(JSONObject user_object) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		String session_id = RequestUtil.getCookieByKey(request, session_id_key);
		this.setSession(session_id, user_object);
	}
}
