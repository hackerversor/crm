package com.common.crm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmUserRoleInfoDao;
import com.common.crm.dao.SysMenuInfoDao;

@Service
public class CmUserRoleService {
	
	@Autowired
	private CmUserRoleInfoDao cmUserRoleInfoDao;
	
	@Autowired
	private SysMenuInfoDao sysMenuInfoDao;
	
	@Autowired
	private SysMenuInfoService sysMenuInfoService;
	
	/**
	 * 	用户添加角色
	 * @param user_id
	 * @param role_ids
	 * @return {retCode,retMsg}
	 */
	public JSONObject  userRoleAdd(String user_id,String[] role_ids) {
		JSONObject retJson = new JSONObject();
		for (String role_id : role_ids) {
			cmUserRoleInfoDao.replace(user_id, role_id);
		}
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	删除角色
	 * @param user_id
	 * @param role_id
	 * @return  {retCode,retMsg}
	 */
	public JSONObject delete(String user_id,String  role_id) {
		JSONObject retJson = new JSONObject();
		cmUserRoleInfoDao.delete(user_id, role_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	根据用户编码查询用户的角色权限
	 * @param user_id 用户编码
	 * @return {retCode,retMsg,roles:[{user_id,role_id,role_name,menus:[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark,sub_menu:[....]]}]}
	 */
	public JSONObject queryShowByUser(String user_id) {
		JSONObject retJson = new JSONObject();
		List<HashMap> roles =  cmUserRoleInfoDao.queryShow(user_id, null);
		if(roles != null) {
			for (HashMap roleMap : roles) {
				String role_id = roleMap.get("role_id").toString();
				List<HashMap> menus = sysMenuInfoDao.getMenusByRole(role_id, "normal", null);
				menus = sysMenuInfoService.getTreeMenu(menus);
				roleMap.put("menus", menus);
			}
		}
		retJson.put("roles", roles);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
}
