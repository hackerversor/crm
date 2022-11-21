package com.common.crm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmTeamRoleInfoDao;
import com.common.crm.dao.SysMenuInfoDao;

@Service
public class CmTeamRoleService {
	
	@Autowired
	private CmTeamRoleInfoDao cmTeamRoleInfoDao;
	
	@Autowired
	private SysMenuInfoDao sysMenuInfoDao;
	
	@Autowired
	private SysMenuInfoService sysMenuInfoService;
	
	/**
	 * 	团队添加角色
	 * @param team_id
	 * @param role_ids
	 * @return {retCode,retMsg}
	 */
	public JSONObject  teamRoleAdd(String team_id,String[] role_ids) {
		JSONObject retJson = new JSONObject();
		for (String role_id : role_ids) {
			cmTeamRoleInfoDao.replace(team_id, role_id);
		}
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	删除角色
	 * @param team_id
	 * @param role_id
	 * @return  {retCode,retMsg}
	 */
	public JSONObject delete(String team_id,String  role_id) {
		JSONObject retJson = new JSONObject();
		cmTeamRoleInfoDao.delete(team_id, role_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	根据团队编码查询团队的角色权限
	 * @param team_id 团队编码
	 * @return {retCode,retMsg,roles:[{team_id,role_id,role_name,menus:[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark,sub_menu:[....]]}]}
	 */
	public JSONObject queryShowByTeam(String team_id) {
		JSONObject retJson = new JSONObject();
		// 查询业务员所属的团队的信息(团队,角色,角色名称)
		List<HashMap> teamAndRole =  cmTeamRoleInfoDao.queryTeamAndRole(team_id, null);
		HashMap map=new HashMap();	
		List list =new ArrayList();
		if(teamAndRole != null) {
			for (HashMap roleMap : teamAndRole) {
				String role_id = roleMap.get("role_id").toString();
				// 查询业务员所属的团队的角色拥有的权限(菜单)信息
				List<HashMap> menus = sysMenuInfoDao.getMenusByRole(role_id, "normal", null);
				menus = sysMenuInfoService.getTreeMenu(menus);
				// 查询业务员所属的团队的信息(团队,角色,角色名称)
				List<HashMap> teamRole = cmTeamRoleInfoDao.queryTeamAndRole(team_id, role_id);
				
				for(HashMap mapRole: teamRole) {
					mapRole.put("menus", menus);
					map=mapRole;
					
				}
//				Collections.sort(list);
				list.add(map);
			}
		}
		retJson.put("roles", list);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		
		return retJson;
		
		}
	
}
