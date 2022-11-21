package com.common.crm.support;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
	private static ConcurrentHashMap<String, JSONObject> outParam = new ConcurrentHashMap<String, JSONObject>();
	
	public static JSONObject getOutParam(String key){
		return outParam.get(key);
	}
	
	public static void setOutParam(String key,JSONObject param){
		outParam.put(key, param);
	}
	
	public static boolean outParamIsEmpty() {
		return outParam.isEmpty();
	}
	
	private static ConcurrentHashMap<String, JSONObject> sysParam = new ConcurrentHashMap<String, JSONObject>();
	
	public static JSONObject getSysParam(String key){
		return sysParam.get(key);
	}
	
	public static void setSysParam(String key,JSONObject param){
		sysParam.put(key, param);
	}
	
	public static boolean sysParamIsEmpty() {
		return sysParam.isEmpty();
	}
	
	//sx_count_data	商险成单数排名  {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],first:[{user_id,name,policy_count}] }
	public static ConcurrentHashMap sx_count_data = new ConcurrentHashMap();
	//sx_premium_data	商险保费排名   {first:[{user_id,name,premium}],second:[{user_id,name,premium}],first:[{user_id,name,premium}] }
	public static ConcurrentHashMap sx_premium_data = new ConcurrentHashMap();
	//fc_count_data	非车成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],first:[{user_id,name,policy_count}] }
	public static ConcurrentHashMap fc_count_data = new ConcurrentHashMap();
	//fc_premium_data	非车保费排名   {first:[{user_id,name,premium}],second:[{user_id,name,premium}],first:[{user_id,name,premium}] } 
	public static ConcurrentHashMap fc_premium_data = new ConcurrentHashMap();
	
	
	
	
	

	

	
}
