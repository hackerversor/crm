package com.common.crm.controller;

import com.common.crm.service.CmPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmUserService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CmPolicyController {
	
	@Autowired
	private CmPolicyService cmPolicyService;

	/**
	 * 	导入保单数据
	 * @param file	保单文件
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/policy/data_imp")
	@ResponseBody
	public String data_imp(@RequestParam(name = "file",required = true) MultipartFile file) throws Exception {
		JSONObject retJson =  cmPolicyService.data_imp(file);
		return retJson.toJSONString();
	}

}
