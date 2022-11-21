package com.common.crm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmCodeValueInfoDao;

@Service
public class CmCodeValueService {

	
	@Autowired
	private CmCodeValueInfoDao cmCodeValueInfoDao;
	
	/**
	 *	码值新增
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	            编码描述
	 * @return {retCode,retMsg}
	 */
	public JSONObject add (String param_class,String class_desc,String param_code,String code_desc) {
		JSONObject retJson =  new JSONObject();
		
		cmCodeValueInfoDao.add(param_class,class_desc,param_code,code_desc);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	码值查询
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	            编码描述
	 * @return {retCode,retMsg,list:param_class,class_desc,param_code,code_desc}
	 */
	public JSONObject query(String param_class,String param_code) {
		List<HashMap> codeValueList=cmCodeValueInfoDao.query(param_class,param_code);
		JSONObject retJson =  new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", codeValueList);
		return retJson;
	}
	
	/**
	 * 	删除码值
	 * @param param_class	类别
	 * @param param_code	编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject delete(String param_class,String param_code) {
		JSONObject retJson =  new JSONObject();
		cmCodeValueInfoDao.delete(param_class,param_code);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
}

