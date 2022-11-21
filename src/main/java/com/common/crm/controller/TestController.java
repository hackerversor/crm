package com.common.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmUserInfoDao;
import com.common.crm.service.StatService;
import com.common.crm.support.ApplicationContext;
import com.common.crm.support.WebSocket;

@Controller
public class TestController {
	@Autowired
	private CmUserInfoDao cmUserInfoDao;
	
	@RequestMapping(path = "/crm/test/first")
	@ResponseBody
	public  String testFirst(@RequestParam(name = "user_id",required = true)String user_id) {
		return cmUserInfoDao.queryOne(user_id).toString();
	}
	

	@Autowired
	private WebSocket webSocket;
	@RequestMapping(path = "/crm/test/websocket")
	@ResponseBody
	public void testCodeQuery(@RequestParam(name = "event_type",required = false)String[] event_type,
			@RequestParam(name = "data",required = false)String[] data,
			@RequestParam(name = "name",required = false)String[] name,
			@RequestParam(name = "desc",required = false)String[] desc,
			@RequestParam(name = "time",required = false)Integer[] time
			) throws Exception {
//		webSocket.sendMessage(text);
		JSONObject retJson =  null;
		List list=new ArrayList();
		for(int i=0;i<event_type.length;i++) {
			retJson =  new JSONObject();
			retJson.put("event_type",event_type[i]);
			
			JSONObject datas =  new JSONObject();
			datas.put("name",name[i]);
			retJson.put("data",datas);
			
			retJson.put("time",time[i]);
			retJson.put("desc",desc[i]);
			list.add(retJson);
		}
		
		for(Object msg:list) {
			System.out.println(msg.toString());
			webSocket.sendMessage(msg.toString());
		}
		
	}

    @Autowired
	private StatService statService;
    @RequestMapping(path = "/crm/test/refreshAchievement") 
	@ResponseBody
	public  void refreshAchievement(@RequestParam(name = "user_id",required = true)String user_id,
			@RequestParam(name = "car_no",required = true)String car_no) {
    	statService.refreshAchievement(user_id, car_no);
    	
    	//sx_count_data	商险成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
		ConcurrentHashMap sx_count_data = ApplicationContext.sx_count_data;
		
		//sx_premium_data	商险保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
		ConcurrentHashMap sx_premium_data = ApplicationContext.sx_premium_data;

		//fc_count_data	非车成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
		ConcurrentHashMap fc_count_data = ApplicationContext.fc_count_data;

		//fc_premium_data	非车保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
		ConcurrentHashMap fc_premium_data = ApplicationContext.fc_premium_data;
		
    			
	}
	
    
}
