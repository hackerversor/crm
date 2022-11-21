package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmUserRoleService;

@Controller
public class CmUserRoleController {
	@Autowired
	private CmUserRoleService cmUserRoleService;
	
	/**
	 * 	用户添加角色
	 * @param user_id
	 * @param role_ids
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user_role/add")
	@ResponseBody
	public String userRoleAdd(@RequestParam(name="user_id",required = true)String user_id,
			@RequestParam(name="role_ids",required = true)String[] role_ids) {
		JSONObject retJson =  cmUserRoleService.userRoleAdd(user_id, role_ids);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除角色
	 * @param user_id
	 * @param role_id
	 * @return  {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_user_role/delete")
	@ResponseBody
	public String delete(@RequestParam(name="user_id",required = true)String user_id,
			@RequestParam(name="role_id",required = true)String role_id) {
		JSONObject retJson =  cmUserRoleService.delete(user_id, role_id);
		return retJson.toJSONString();
	}
	
	/**
	 * 	根据用户编码查询用户的角色权限
	 * @param user_id 用户编码
	 * @return {retCode,retMsg,roles:[{user_id,role_id,role_name,menus:[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark,sub_menu:[....]]}]}
	 */
	@RequestMapping(path = "/crm/cm_user_role/queryShowByUser")
	@ResponseBody
	public String queryShowByUser(@RequestParam(name="user_id",required = true)String user_id) {
		JSONObject retJson =  cmUserRoleService.queryShowByUser(user_id);
		return retJson.toJSONString();
	}
	
}
