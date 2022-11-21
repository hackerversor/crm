package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.SysIconService;
@Controller
public class SysIconController {
	
	@Autowired
	private SysIconService sysIconService;
	
	@RequestMapping(path = "/crm/sys_icon/query")
	@ResponseBody
	public String query(@RequestParam(name = "icon_title",required = false)String icon_title) {
		JSONObject retJson = sysIconService.list(icon_title);
		return retJson.toJSONString();
	}
}
