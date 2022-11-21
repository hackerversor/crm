package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CacheService;


@Controller
public class CacheController {
	@Autowired
	private CacheService cacheService;
	
	@RequestMapping(path = "/crm/test/reflushCache")
	@ResponseBody
	public void reflushCache() {
		cacheService.reflushCache();
	}
	
	
	@RequestMapping(path = "/crm/reflushCache")
	@ResponseBody
	public JSONObject getCacheParam(@RequestParam(name = "key")String key) {
		JSONObject json = cacheService.getCacheParam(key);
		return json;
	}
}
