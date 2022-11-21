package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmCodeValueService;
@Controller
public class CmCodeValueController {
	
	@Autowired
	private CmCodeValueService cmCodeValueService;
	/**
	 *	码值新增
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	            编码描述
	*/
	@RequestMapping(path = "/crm/code_value/add")
	@ResponseBody
	public String add(@RequestParam(name = "param_class",required = true)String param_class,
			@RequestParam(name = "class_desc",required = true)String class_desc,
			@RequestParam(name = "param_code",required = true)String param_code,
			@RequestParam(name = "code_desc",required = true)String code_desc) {
		JSONObject retJson = cmCodeValueService.add(param_class, class_desc,param_code,code_desc);
		return retJson.toJSONString();
	}
	
	/**
	 *	码值查询
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	            编码描述
	 */
	@RequestMapping(path = "/crm/code_value/query")
	@ResponseBody
	public String query(@RequestParam(name = "param_class",required = false)String param_class,
			@RequestParam(name = "param_code",required = false)String param_code) {
		JSONObject retJson = cmCodeValueService.query(param_class, param_code);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除费率
	 * @param pid 编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/code_value/delete")
	@ResponseBody
	public String delete(@RequestParam(name = "param_class",required = true)String param_class,
			@RequestParam(name = "param_code",required = true)String param_code) {
		JSONObject retJson = cmCodeValueService.delete(param_class,param_code);
		return retJson.toJSONString();
	}
}
