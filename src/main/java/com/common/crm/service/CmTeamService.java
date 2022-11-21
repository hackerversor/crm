package com.common.crm.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmTeamUserDao;

@Service
public class CmTeamService {

	@Autowired
	private CmTeamUserDao cmTeamUserDao;
	
	@Autowired
	private SequenceService sequenceService;
	
	/**
	    *      团队新增
	 * @param team_name 团队名称
	 * @param slogan 口号
	 * @param user_id 团队主管
	 * @return {retCode,retMsg}
	 */
	public JSONObject add(String team_name, String slogan, String user_id) {
		
		Timestamp ctime = new Timestamp(System.currentTimeMillis());
		String team_id=sequenceService.getNewSequence("team_id");
		JSONObject retJson =  new JSONObject();
		List<HashMap> teamList=cmTeamUserDao.query(null,team_name,null);
		if(teamList.size() > 0) {
			retJson.put("retCode", "add_error");
			retJson.put("retMsg", "添加失败，团队名不允许重复！");
		}else {
			cmTeamUserDao.add(team_id,team_name,slogan,user_id,ctime);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		
		return retJson;
	}

	/**
	 *	团队删除  需要把对应的团队成员删除
	 * @param team_id 团队编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject delete(String team_id) {
		JSONObject retJson =  new JSONObject();
		cmTeamUserDao.delete(team_id);
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}

	/**
	 *	团队修改   
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param slogan	口号
	 * @param user_id	团队主管
	 * @return {retCode,retMsg}
	 */
	public JSONObject modify(String team_id, String team_name, String slogan, String user_id) {
		JSONObject retJson =  new JSONObject();
		cmTeamUserDao.modify(team_id,team_name,slogan,user_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 *	团队查询
	 * @param team_id	团队编码
	 * @param team_name	团队名称
	 * @param user_id	团队主管
	 * @return {retCode,retMsg,list:team_id,slogan,user_id,user_name,ctime}
	 */
	public JSONObject query(String team_id, String team_name, String user_id) {
		JSONObject retJson =  new JSONObject();
		
		List<HashMap> teamList=cmTeamUserDao.query(team_id,team_name,user_id);

		retJson.put("list", teamList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 *	团队成员查询
	 * @param team_id  团队编码	
	 * @return {retCode,retMsg,team_id,team_name,slogan,user_id,user_name,ctime,
	 * users:user_id,name,sex,birthday,hiredate,email,phone,leavedate,ctime,status,remark,user_no}
	 */
	public JSONObject memberQuery(String team_id) {
		JSONObject retJson =  new JSONObject();
		
		String teamid=null;
		String team_name=null;
		String slogan=null;
		String user_id=null;
		String user_name=null;
		String ctime=null;
		List<HashMap>teamList=cmTeamUserDao.queryTeam(team_id);
		for(HashMap map:teamList) {
			teamid=map.get("team_id").toString();
			team_name=map.get("team_name").toString();
			slogan=map.get("slogan").toString();
			user_id=map.get("user_id").toString();
			user_name=map.get("user_name").toString();
			ctime=map.get("ctime").toString();

		}
		List<HashMap>userList=cmTeamUserDao.queryUser(team_id);
		
		retJson.put("team_id", teamid);
		retJson.put("team_name", team_name);
		retJson.put("slogan", slogan);
		retJson.put("user_id", user_id);
		retJson.put("user_name", user_name);
		retJson.put("user_name", user_name);
		retJson.put("ctime", ctime);
		retJson.put("users", userList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}

	/**
	 *	团队成员新增    团队成员新增  一个人只能加入一个团队  如果已经加入团队则拒绝并提示
	 * @param team_id	团队编码
	 * @param user_id	用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	public JSONObject memberAdd(String team_id, String[] user_id) {
		JSONObject retJson =  new JSONObject();
		
		for(int i=0;i<user_id.length;i++) {
			List<HashMap> teamList=cmTeamUserDao.query(team_id,null,user_id[i]);
			if(teamList.size() > 0) {
				retJson.put("retCode", "add_error");
				retJson.put("retMsg", "添加失败，一个人只能加入一个团队！");
			}else {
				cmTeamUserDao.memberAdd(team_id,user_id[i]);
//				cmTeamRoleInfoDao.replace(team_id, role_id);
				retJson.put("retCode", "success");
				retJson.put("retMsg", "成功");
			}
		}
		
		return retJson;
	}
	
	/**
	 *	团队成员删除
	 * @param team_id 团队编码
	 * @param user_id 用户编码  json数组 [user_id1,user_id2,...]
	 * @return {retCode,retMsg}
	 */
	public JSONObject memberDel(String team_id, String[] user_id) {
		JSONObject retJson =  new JSONObject();
		
		for(String userid:user_id) {
			cmTeamUserDao.memberDel(team_id,userid);
		}
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}

}
