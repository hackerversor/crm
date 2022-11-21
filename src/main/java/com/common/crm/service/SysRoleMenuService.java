package com.common.crm.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.SysMenuInfoDao;
import com.common.crm.dao.SysRoleMenuDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;

@Service
public class SysRoleMenuService {

	
	@Autowired
	private SysRoleMenuDao  sysRoleMenuDao;
	@Autowired
	private SysMenuInfoService sysMenuInfoService;
	@Autowired
	private SysMenuInfoDao sysMenuInfoDao;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private SequenceService sequenceService;
	/**
	 *	角色新增
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject add (String role_name,String[] menu_id,String status) {
		JSONObject retJson =  new JSONObject();
		String role_id = sequenceService.getNewSequence("role_id");
		String user_id=(String) sessionManager.getSession().get("user_id");
		Calendar c = Calendar.getInstance();
		String menuid=null;
		String ctime = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd kk:mm:ss");
		int roleNum=sysRoleMenuDao.addRole(role_id,role_name,ctime,user_id,"normal");
		for(int i=0;i<menu_id.length;i++){
			menuid=menu_id[i];
			if(roleNum > 0) {
				int roleMenuNum=sysRoleMenuDao.addRoleMenu(role_id, menuid);
				if(roleMenuNum > 0) {
					retJson.put("retMsg", "成功");
					retJson.put("retCode", "success");
				}
				else {
					retJson.put("retCode", "error");
					retJson.put("retMsg", "失败");
				}
			}else {
				retJson.put("retCode", "error");
				retJson.put("retMsg", "失败");
			}
			
		}
		
		return retJson;
	}
	
	/**
	 * 	角色查询
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @param status	状态 normal delete
	 * @param role_id	角色编码
	 * @param role_name	角色名称
	 * @param user_id	用户编码
	 * @param user_name	用户名称
	 * @param status	状态
	 * @param menus	菜单列表
		 * @param menu_id	菜单编码
		 * @param menu_name	菜单名称
		 * @param up_menu	上级菜单
		 * @param sub_menu	下级菜单array
	 * @return {retCode,retMsg,list:role_id,role_name,user_name,status,menus[menu_id,menu_name,up_menu,sub_menu[]] }
	 */
	
	public JSONObject query(String role_id,String role_name,String status) {
		JSONObject retJson =  new JSONObject();
		//查询角色列表
		List<HashMap> sysRoleMenuList=sysRoleMenuDao.query(role_id,role_name,status);
		
		List list = new ArrayList();
		for(HashMap roleMap:sysRoleMenuList) {
			String db_role_id =roleMap.get("role_id").toString();
			//根据用户查询有权限的菜单列表
			List<HashMap> sysMenuList = sysMenuInfoDao.getMenusByRole(db_role_id,status,"true");
			//结构化tree型菜单
			List<HashMap> menuTreeList  = sysMenuInfoService.getTreeMenu(sysMenuList);
			//为菜单添加menus数组结构
			roleMap.put("menus",menuTreeList);
			list.add(roleMap);
		}
		retJson.put("list", list);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
	
	/**
	*	角色修改  删除原来的，插入新的
	 * @param role_id	角色id
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject modify(String role_id,String role_name, String[]menu_id) {
		JSONObject retJson =  new JSONObject();
		String user_id=(String) sessionManager.getSession().get("user_id");
		Calendar c = Calendar.getInstance();
		String ctime = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd kk:mm:ss");
		List<HashMap>listStatus=sysRoleMenuDao.query(role_id,null,null);
		for(HashMap statusMap:listStatus) {
			String status=statusMap.get("status").toString();
			if(status.equals("delete")) {
				retJson.put("retCode", "error");
				retJson.put("retMsg", "失败，已删除状态数据不允许修改！");
				return retJson;
			}
		}
		sysRoleMenuDao.modify(role_id,null,null);
		int dbrNum = sysRoleMenuDao.deleteByRoleid(role_id);
		if(dbrNum > 0) {
			sysRoleMenuDao.addRole(role_id, role_name, ctime, user_id, "normal");
			if(menu_id!=null) {
				for(int i=0;i<menu_id.length;i++){
					String menuid=menu_id[i];
					sysRoleMenuDao.addRoleMenu(role_id, menuid);
				}
			}
			
			retJson.put("retCode", "secsses");
			retJson.put("retMsg", "成功");
		}else {
			retJson.put("retCode", "error");
			retJson.put("retMsg", "失败");
		}
		
		return retJson;
	}
	
	/**
	 * 	删除角色关系表数据
	 * @param role_id	角色id
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject delete(String role_id) {
		JSONObject retJson =  new JSONObject();
		List<HashMap> sysRoleQueryList =sysRoleMenuDao.queryByRoleid(role_id);
		if(sysRoleQueryList.size() > 0) {
			int delNum=sysRoleMenuDao.delete(role_id);
			if(delNum > 0) {
				sysRoleMenuDao.update(role_id);
				retJson.put("retCode", "success");
				retJson.put("retMsg", "成功");
			}else {
				retJson.put("retCode", "error");
				retJson.put("retMsg", "失败");
			}
		}else {
				retJson.put("retCode", "error");
				retJson.put("retMsg", "失败，系统中存在正在使用的角色信息");
		}
		return retJson;
		
	}
	
}

