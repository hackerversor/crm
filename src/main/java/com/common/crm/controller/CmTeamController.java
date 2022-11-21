package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmTeamService;


@Controller
public class CmTeamController {
	
	@Autowired
	private CmTeamService cmTeamService;
	
	/**
	    *      团队新增
	 * @param team_name 团队名称
	 * @param slogan 口号
	 * @param user_id 团队主管
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/team/add")
	@ResponseBody
	public String add(@RequestParam(name = "team_name",required = true)String team_name,
			@RequestParam(name = "slogan",required = false)String slogan,
			@RequestParam(name = "user_id",required = false)String user_id) {
		
		JSONObject retJson = cmTeamService.add(team_name,slogan,user_id);
		
		return retJson.toJSONString();
	}
	
	/**
	 *	团队删除  需要把对应的团队成员删除
	 * @param team_id 团队编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/team/del")
	@ResponseBody
	public String cmTeamDelete(@RequestParam(name = "team_id",required = true)String team_id){

		JSONObject retJson = cmTeamService.delete(team_id);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	团队修改   
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param slogan	口号
	 * @param user_id	团队主管
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/team/modify")
	@ResponseBody
	public String cmTeamModify(@RequestParam(name = "team_id",required = true)String team_id,
			@RequestParam(name = "team_name",required = false)String team_name,
			@RequestParam(name = "slogan",required = false)String slogan,
			@RequestParam(name = "user_id",required = false)String user_id){
		JSONObject retJson = cmTeamService.modify(team_id,team_name, slogan, user_id);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	团队查询
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param user_id	团队主管
	 * @return {retCode,retMsg,list:team_id,slogan,user_id,user_name,ctime}
	 */
	@RequestMapping(path = "/crm/team/query")
	@ResponseBody
	public String query(@RequestParam(name = "team_id",required = false)String team_id,
			@RequestParam(name = "team_name",required = false)String team_name,
			@RequestParam(name = "user_id",required = false)String user_id) {
		JSONObject retJson = cmTeamService.query(team_id, team_name, user_id);
		return retJson.toJSONString();
	}
	
	/**
	 *	团队成员查询
	 * @param team_id  团队编码	
	 * @return {retCode,retMsg,team_id,team_name,slogan,user_id,user_name,ctime,
	 * users:user_id,name,sex,birthday,hiredate,email,phone,leavedate,ctime,status,remark,user_no}
	 */
	@RequestMapping(path = "/crm/team/member/query")
	@ResponseBody
	public String memberQuery(@RequestParam(name = "team_id",required = true)String team_id) {
		JSONObject retJson = cmTeamService.memberQuery(team_id);
		return retJson.toJSONString();
	}
	
	/**
	 *	团队成员新增    团队成员新增  一个人只能加入一个团队  如果已经加入团队则拒绝并提示
	 * @param team_id	团队编码
	 * @param user_id	用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/team/member/add")
	@ResponseBody
	public String memberAdd(@RequestParam(name = "team_id",required = true)String team_id,
			@RequestParam(name = "user_id",required = true)String[] user_id) {
		JSONObject retJson = cmTeamService.memberAdd(team_id,user_id);
		return retJson.toJSONString();
	}
	
	/**
	 *	团队成员删除
	 * @param team_id 团队编码
	 * @param user_id 用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/team/member/del")
	@ResponseBody
	public String memberDel(@RequestParam(name = "team_id",required = true)String team_id,
			@RequestParam(name = "user_id",required = true)String[] user_id){

		JSONObject retJson = cmTeamService.memberDel(team_id,user_id);
		return retJson.toJSONString();
		
	}
	
}
