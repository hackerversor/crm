package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmSevAcctService;

@Controller
public class CmSevAcctController {
	@Autowired
	private CmSevAcctService cmSevAcctService;
	
	
	/**
	 * 	添加账号信息
	 * @param login_name	登录名
	 * @param login_pwd	
	 * @param login_url
	 * @param acct_desc
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id
	 * @param phone
	 * @param email
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/sev_acct/add")
	@ResponseBody
	public String add_sev_acct(@RequestParam(name = "login_name",required = true)String login_name,
			@RequestParam(name = "login_pwd",required = true)String login_pwd,
			@RequestParam(name = "login_url",required = true)String login_url,
			@RequestParam(name = "acct_desc",required = true)String acct_desc,
			@RequestParam(name = "server_sup",required = true)String server_sup,
			@RequestParam(name = "auth_type",required = false)String auth_type,
			@RequestParam(name = "auth_set",required = false)String auth_set,
			@RequestParam(name = "user_id",required = true)String user_id,
			@RequestParam(name = "phone",required = false)String phone,
			@RequestParam(name = "email",required = false)String email,
			@RequestParam(name = "remark",required = false)String remark) {
		
		JSONObject retJson = cmSevAcctService.add_sev_acct(login_name, login_pwd, login_url, acct_desc, server_sup,
				auth_type, auth_set, user_id, phone, email, remark);
		return retJson.toJSONString();
	}
	
	/**
	 * 	查账号信息
	 * @param acct_id	
	 * @param login_name
	 * @param server_sup
	 * @param user_id
	 * @return {retCode,retMsg,list[{acct_id,login_name,login_pwd,login_url,acct_desc,server_sup,auth_type,auth_set,user_id,name,phone,email,status,remark}]}
	 */
	@RequestMapping(path = "/crm/sev_acct/query")
	@ResponseBody
	public String query_sev_acct(@RequestParam(name = "acct_id",required = false)String acct_id,
			@RequestParam(name = "login_name",required = false)String login_name,
			@RequestParam(name = "server_sup",required = false)String server_sup,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "status",required = false)String status) {
		JSONObject retJson = cmSevAcctService.query_sev_acct(acct_id, login_name, server_sup, user_id,status);
		return retJson.toJSONString();
	}
	
	/**
	 * 	注销
	 * @param acct_id
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/sev_acct/cancel")
	@ResponseBody
	public String del_sev_acct(@RequestParam(name = "acct_id",required = true)String acct_id) {
		JSONObject retJson = cmSevAcctService.cancel(acct_id);
		return retJson.toJSONString();
	}
	
	/**
	 * 	更新
	 * @param acct_id
	 * @param login_name	登录名
	 * @param login_pwd	
	 * @param login_url
	 * @param acct_desc
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id
	 * @param phone
	 * @param email
	 * @param status 状态  normal正常 cancel注销
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/sev_acct/update")
	@ResponseBody
	public String update_sev_acct(@RequestParam(name = "acct_id",required = true)String acct_id,
			@RequestParam(name = "login_name",required = false)String login_name,
			@RequestParam(name = "login_pwd",required = false)String login_pwd,
			@RequestParam(name = "login_url",required = false)String login_url,
			@RequestParam(name = "acct_desc",required = false)String acct_desc,
			@RequestParam(name = "server_sup",required = false)String server_sup,
			@RequestParam(name = "auth_type",required = false)String auth_type,
			@RequestParam(name = "auth_set",required = false)String auth_set,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "phone",required = false)String phone,
			@RequestParam(name = "email",required = false)String email,
			@RequestParam(name = "status",required = false)String status,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = cmSevAcctService.update(acct_id, login_name, login_pwd, login_url, acct_desc, server_sup, auth_type, auth_set, user_id, phone, email, status, remark);
		return retJson.toJSONString();
	}
}
