package com.common.crm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.SysOutParamInfoDao;
import com.common.crm.dao.SysParamInfoDao;
import com.common.crm.support.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {
	@Autowired
	private SysOutParamInfoDao sysOutParamInfoDao;
	
	@Autowired
	private SysParamInfoDao sysParamInfoDao;
	/**
	 * 	刷新参数
	 */
	public void reflushCache() {
		//刷新外接参数
		List list = sysOutParamInfoDao.list(null, null);
		if(list != null && !list.isEmpty()) {
			JSONArray array = new JSONArray(list);
			for (int i = 0; i < array.size(); i++) {
				JSONObject paramJson  = array.getJSONObject(i);
				String key = paramJson.getString("param_code");
				ApplicationContext.setOutParam(key, paramJson);
			}
		}
		
		//刷新系统参数
		list = sysParamInfoDao.list(null, null);
		if(list != null && !list.isEmpty()) {
			JSONArray array = new JSONArray(list);
			for (int i = 0; i < array.size(); i++) {
				JSONObject paramJson  = array.getJSONObject(i);
				String key = paramJson.getString("param_code");
				ApplicationContext.setSysParam(key, paramJson);
			}
		}
	}
	
	/**
	 * 	根据 参数编码 获取参数
	 * @param key 参数编码
	 * @return {pid,param_code,param_value,param_desc}
	 */
	public JSONObject getCacheParam(String key) {
		if(ApplicationContext.outParamIsEmpty()||ApplicationContext.sysParamIsEmpty()) {
			reflushCache();
		}
		JSONObject retJson = ApplicationContext.getOutParam(key);
		if(retJson == null || retJson.isEmpty()) {
			retJson = ApplicationContext.getSysParam(key);
		}
		return retJson;
	}
	/**
	 *  根据 参数编码 查询 参数值
	 * @param key 参数编码
	 * @return 参数值
	 */
	public String getCacheParamValue(String key) {
		JSONObject retJson = getCacheParam(key);
		return retJson == null ? null : retJson.getString("param_value");
	}
	
}
