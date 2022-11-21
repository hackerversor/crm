package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmTeamRoleService;

@Controller
public class CmTeamRoleController {
	@Autowired
	private CmTeamRoleService cmTeamRoleService;
	
	/**
	 * 	团队添加角色
	 * @param team_id
	 * @param role_ids
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_team_role/add")
	@ResponseBody
	public String teamRoleAdd(@RequestParam(name="team_id",required = true)String team_id,
			@RequestParam(name="role_ids",required = true)String[] role_ids) {
		JSONObject retJson =  cmTeamRoleService.teamRoleAdd(team_id, role_ids);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除角色
	 * @param team_id
	 * @param role_id
	 * @return  {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_team_role/delete")
	@ResponseBody
	public String delete(@RequestParam(name="team_id",required = true)String team_id,
			@RequestParam(name="role_id",required = true)String role_id) {
		JSONObject retJson =  cmTeamRoleService.delete(team_id, role_id);
		return retJson.toJSONString();
	}
	
	/**
	 * 	根据团队编码查询的角色权限
	 * @param team_id 团队编码
	 * @return {retCode,retMsg,roles:[{team_id,role_id,role_name,menus:[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark,sub_menu:[....]]}]}
	 */
	@RequestMapping(path = "/crm/cm_team_role/queryShowByTeam")
	@ResponseBody
	public String queryShowByTeam(@RequestParam(name="team_id",required = true)String team_id) {
		JSONObject retJson =  cmTeamRoleService.queryShowByTeam(team_id);
		return retJson.toJSONString();
	}
	
}
