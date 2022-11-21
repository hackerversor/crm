package com.common.crm.support;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/crm/websocket/notice")
@Component
public class WebSocket {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Session session;
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		//session.getBasicRemote().
		logger.info("open socket 。。。。");
	}
	
	@OnClose
	public void onClose() {
		logger.info("close socket 。。。。");
	}
	
	/**
	 *     出现错误
	* @param session
	* @param error
	*/
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("发生错误：{}，浏览器页面被关闭！",error.getMessage(),session.getId());
	//	error.printStackTrace();
	}
	
	@OnMessage
	public void onMessage(String message,Session session) throws Exception {
		logger.info("socket msg:" + message);
		//session.getBasicRemote().sendText(message);
	}
	
	public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
	
	/**
	 *	 事件通知
	 * @param event_type
	  		order_change	排名变动事件 调用员工保费汇总(/crm/stand_book/stat/query_by_user)接口 刷新排名
			complete_order	成单事件
			challenge_count_premium	完成任务事件(单数和保费金额同时完成) 
			challenge_count	完成任务事件(单数完成)
			challenge_premium 完成任务事件(保费金额完成)
	 * @param name 姓名 	 challenge_count_premium  challenge_count  challenge_premium 事件时有值
	 * @param desc 事件说明
	 * @param time 停留时间 单位秒
	 * @throws IOException
	 */
	public void sendEnent(String event_type,String name,String desc,Integer time )  {
		JSONObject retJson =  new JSONObject();
		retJson.put("event_type",event_type);
		if(name != null){
			JSONObject data =  new JSONObject();
			data.put("name",name);
			retJson.put("data",data);
		}
		retJson.put("time",time);
		retJson.put("desc",desc);
        try {
			session.getBasicRemote().sendText(retJson.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
