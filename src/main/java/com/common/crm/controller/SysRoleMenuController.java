package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.SysRoleMenuService;


@Controller
public class SysRoleMenuController {
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	/**
	 *	添加角色
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	
	@RequestMapping(path = "/crm/role/add")
	@ResponseBody
	public String add(@RequestParam(name = "role_name",required = false)String role_name,
			@RequestParam(name = "menu_id",required = false)String[] menu_id,
			@RequestParam(name = "status",required = false)String status) {
		
		JSONObject retJson = sysRoleMenuService.add(role_name,menu_id,status);
		
		return retJson.toJSONString();
	}
	
	/**
	 *	查询
	 * @param role_id	角色编码
	 * @param role_name	角色名称
	 * @param status	状态 normal delete
	 * @return {retCode,retMsg,}
	 */
	@RequestMapping(path = "/crm/role/query")
	@ResponseBody
	public String query(@RequestParam(name = "role_id",required = false)String role_id,
			@RequestParam(name = "role_name",required = false)String role_name,
			@RequestParam(name = "status",required = false)String status) {
		JSONObject retJson = sysRoleMenuService.query(role_id, role_name, status);
		return retJson.toJSONString();
	}
	
	/**
	 *	角色修改  删除原来的，插入新的
	 * @param role_id	角色id
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/role/modify")
	@ResponseBody
	public String modify(@RequestParam(name = "role_id",required = true)String role_id,
			@RequestParam(name = "role_name",required = false)String role_name,
			@RequestParam(name = "menu_id",required = false)String[] menu_id) {
		JSONObject retJson = sysRoleMenuService.modify(role_id,role_name,menu_id);
		return retJson.toJSONString();
	}
	
	/**
	 *	删除
	 * @param role_id	角色id
	 * @param status	状态 normal delete
	 * @return {retCode,retMsg,}
	 */
	@RequestMapping(path = "/crm/role/delete")
	@ResponseBody
	public String delete(@RequestParam(name = "role_id",required = true)String role_id) {
		JSONObject retJson = sysRoleMenuService.delete(role_id);
		return retJson.toJSONString();
	}
	
}
