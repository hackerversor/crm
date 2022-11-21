package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmUserService;

@Controller
public class CmUserController {
	
	@Autowired
	private CmUserService cmUserService;
	
	
	/**
	 * 	登录
	 * @param login_name	登录名称
	 * @param login_pwd	登录密码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user/login")
	@ResponseBody
	public String login(@RequestParam(name = "login_name",required = true)String login_name,
			@RequestParam(name = "login_pwd",required = true)String login_pwd) {
		JSONObject retJson = cmUserService.cm_user_login(login_name, login_pwd);
		return retJson.toJSONString();
	}
	
	/**
	 * 	登出
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user/logout")
	@ResponseBody
	public String logout() {
		JSONObject retJson = cmUserService.logout();
		return retJson.toJSONString();
	}
	
	
	/**
	 *  	修改密码
	 * @param login_name 登陆名称
	 * @param old_login_pwd 旧密码
	 * @param new_login_pwd  新密码
	 * @return {retCode,retMsg}
	 */
	
	@RequestMapping(path = "/crm/cm_user/pwd_modify")
	@ResponseBody
	public String pwd_modify(@RequestParam(name = "login_name",required = true)String login_name,
			@RequestParam(name = "old_login_pwd",required = true)String old_login_pwd,
			@RequestParam(name = "new_login_pwd",required = true)String new_login_pwd) {
		JSONObject retJson = cmUserService.pwd_modify(login_name, old_login_pwd,new_login_pwd);
		return retJson.toJSONString();
	}
	
	/**
	 * 	创建用户
	 * @param name	姓名
	 * @param user_no 用户编号
	 * @param sex	性别 M男 W女
	 * @param id_no	证件号
	 * @param email	邮件
	 * @param phone	手机号
	 * @param remark	备注
	 * @return {retCode,retMsg,login_name,login_pwd}
	 */
	@RequestMapping(path = "/crm/cm_user/create")
	@ResponseBody
	public String create(@RequestParam(name="name",required = true)String name,
			@RequestParam(name="user_no",required = false)String user_no,
			@RequestParam(name = "sex",required = true)String sex,
			@RequestParam(name = "id_no" ,required = true)String id_no,
			@RequestParam(name = "email" ,required = false)String email,
			@RequestParam(name = "phone" ,required = true)String phone,
			@RequestParam(name = "remark" ,required = false)String remark) {
		JSONObject retJson = cmUserService.create(name, user_no, sex, id_no, email, phone, remark);
		return retJson.toJSONString();
	}
	
	/**
	 * 	查询用户列表
	 * @param user_id
	 * @param name
	 * @param status
	 * @param have_team 是否已经加入team true是  false否
	 * @return {retCode,retMsg,list:[{user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}]}
	 */
	@RequestMapping(path = "/crm/cm_user/query")
	@ResponseBody
	public String query(@RequestParam(name="user_id",required = false)String user_id,
			@RequestParam(name="name",required = false)String name,
			@RequestParam(name="status",required = false)String status,
			@RequestParam(name="have_team",required = false)String have_team ) {
		JSONObject retJson = cmUserService.listUsers(user_id, name, status,have_team);
		return retJson.toJSONString();
	}
	
	/**
	 * 	离职/注销用户
	 * @param user_id 用户编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user/leave")
	@ResponseBody
	public String leave(@RequestParam(name="user_id",required = true)String user_id) {
		JSONObject retJson = cmUserService.leave(user_id);
		return retJson.toJSONString();
	}
	/**
	 * 	更新用户信息
	 * @param user_id
	 * @param name
	 * @param sex
	 * @param id_no
	 * @param birthday
	 * @param email
	 * @param phone
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user/update")
	@ResponseBody
	public String update(@RequestParam(name="user_id",required = true)String user_id,
			@RequestParam(name="name",required = false)String name,
			@RequestParam(name = "user_no" ,required = false)String user_no,
			@RequestParam(name = "sex",required = false)String sex,
			@RequestParam(name = "id_no" ,required = false)String id_no,
			@RequestParam(name = "birthday" ,required = false)String birthday,
			@RequestParam(name = "email" ,required = false)String email,
			@RequestParam(name = "phone" ,required = false)String phone,
			@RequestParam(name = "remark" ,required = false)String remark) {
		JSONObject retJson = cmUserService.update(user_id, name,user_no, sex, id_no, birthday, email, phone, remark);
		return retJson.toJSONString();
	}
	
	@RequestMapping(path = "/crm/cm_user/login_status")
	@ResponseBody
	public void login_status() {
		
	}
	
	
	
	/**
	 * 	查询工作业务参数
	 * @return {retCode,retMsg,user_id,user_name,auth_user_id,auth_user_name,issueList:[{company,company_name,issue_acct,user_id}]}
	 */
	@RequestMapping(path = "/crm/cm_user/getUserWorkParams")
	@ResponseBody
	public String getUserWorkParams() {
		JSONObject retJson = cmUserService.getUserWorkParams();
		return retJson.toJSONString();
	}
	
	/**
	 *  	重置密码
	 * @param login_name 登陆名称
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user/pwd_reset")
	@ResponseBody
	public String pwd_reset(@RequestParam(name = "login_name",required = true)String login_name) {
		JSONObject retJson = cmUserService.pwd_reset(login_name);
		return retJson.toJSONString();
	}

}
