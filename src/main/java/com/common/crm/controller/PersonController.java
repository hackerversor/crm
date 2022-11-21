package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.PersonService;

@Controller
public class PersonController {
	@Autowired
	private PersonService personService;
	
	@RequestMapping(path = "/crm/cm_user/person/query")
	@ResponseBody
	public String userBaseInfo() {
		JSONObject retJson = personService.userBaseInfo();
		return retJson.toJSONString();
	}
	
	@RequestMapping(path = "/crm/cm_user/person/issue_acct_query")
	@ResponseBody
	public String issue_acct_query(@RequestParam(name = "company",required = false)String company) {
		JSONObject retJson = personService.issue_acct_query(company);
		return retJson.toJSONString();
	}
	
	
	@RequestMapping(path = "/crm/cm_user/person/issue_acct_delete")
	@ResponseBody
	public String issue_acct_delete(@RequestParam(name = "issue_acct",required = true)String issue_acct) {
		JSONObject retJson = personService.issue_acct_delete(issue_acct);
		return retJson.toJSONString();
	}
	
	
	@RequestMapping(path = "/crm/cm_user/person/issue_acct_add")
	@ResponseBody
	public String issue_acct_add(@RequestParam(name = "company",required = true)String company,
			@RequestParam(name = "company_name",required = true)String company_name,
			@RequestParam(name = "issue_acct",required = true)String issue_acct) {
		JSONObject retJson = personService.issue_acct_add(company, company_name,issue_acct);
		return retJson.toJSONString();
	}
	
}
