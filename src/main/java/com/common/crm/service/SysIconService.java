package com.common.crm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.SysIconInfoDao;

@Controller
public class SysIconService {
	@Autowired
	private SysIconInfoDao sysIconInfoDao;
	
	/**
	 * 	图标列表
	 * @param icon_title
	 * @return 
	 * {
	 * 	retCode,retMsg,list:[{icon_title,icon_path,icon_desc}]
	 * }
	 */
	public JSONObject list(String icon_title) {
		List<HashMap> list = sysIconInfoDao.qryShows(icon_title);
		JSONObject retJson = new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", list);
		return retJson;
	}
}
