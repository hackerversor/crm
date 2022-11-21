package com.common.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmUserInfoDao;
import com.common.crm.dao.SysMenuInfoDao;
import com.common.crm.support.SessionManager;

@Service
public class SysMenuInfoService {
	@Autowired
	private SysMenuInfoDao sysMenuInfoDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private CmUserInfoDao cmUserInfoDao;
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 根据用户权限查询出客户相对应的菜单信息
	 * @return  {retCode,retMsg,listL[menus1,sub_menu:[menus]]}
	 */
	public JSONObject getMenuForTreeByUser() {
		JSONObject retJson =  new JSONObject();
		//全部菜单
		List<HashMap> allMenuList = null;
		String login_name = sessionManager.getSession().getString("login_name");
		if(login_name.equals("admin")) {//如果是超级管理员 则查询所有可显示的菜单
			allMenuList = sysMenuInfoDao.qryShows(null, null, null, null, "true", null, "normal");
		}else {//根据用户角色查询菜单列表
			String user_id = sessionManager.getSession().getString("user_id");
			allMenuList = sysMenuInfoDao.getMenusByUser(user_id, "normal","true");
		}
		List menuTreeList =getTreeMenu(allMenuList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", menuTreeList);
		return retJson;
	}
	
	
	
	/**
	 * 	结构化tree型菜单
	 * @param allMenuList
	 * @return
	 */
	public List<HashMap> getTreeMenu(List<HashMap> allMenuList){
		List<HashMap> supMenuList = new ArrayList<HashMap>();
		for (HashMap hashMap : allMenuList) {
			String level = hashMap.get("level").toString();
			if(level.equals("1")) {
				supMenuList.add(hashMap);
			}
		}
		List menuTreeList = new ArrayList();
		for (HashMap menuMap : supMenuList) {
			List<HashMap> menu_list = findSubMenu(menuMap,allMenuList);
			menuTreeList.addAll(menu_list);
		}
		return menuTreeList;
	}
	
	/**
	 * 	结构化菜单结构。
	 * @param menuMap
	 * @param allMenuList
	 * @return  [listL[menus1,sub_menu:[menus]]]
	 */
	private List findSubMenu(HashMap menuMap ,List<HashMap> allMenuList){
		String menu_id = menuMap.get("menu_id").toString();
		Boolean has_more = Boolean.valueOf(menuMap.get("has_more").toString());
		List  menuTreeList = new ArrayList<HashMap>();
		List  subTreeList = new ArrayList<HashMap>();
		menuTreeList.add(menuMap);
		if(has_more) {
			for (HashMap subMap : allMenuList) {
				String sub_menu_id = subMap.get("menu_id").toString();
				if(sub_menu_id.equals(menu_id)) {
					continue;
				}
				
				String sub_up_menu = subMap.get("up_menu") == null ? "" : subMap.get("up_menu").toString();
				if(sub_up_menu.equals(menu_id)) {
					subTreeList.addAll(findSubMenu(subMap,allMenuList));
					menuMap.put("sub_menu",subTreeList);
				}
			}
		}
		return menuTreeList;
	}
	
	
	
	
	/**
	 * 	菜单查询
	 * @param menu_id	菜单编码
	 * @param menu_name	菜单名称
	 * @param up_menu	上级菜单
	 * @param level 	级别 1最大
	 * @param is_show	是否显示 true/false 显示/不显示
	 * @param has_more	是否有下级true/false	是/否
	 * @param status	状态 normal/delete 正常/删除
	 * @return {retCode,retMsg,list[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark ]}
	 */
	public JSONObject query(String menu_id,String menu_name,String up_menu,Integer level,String is_show,String has_more,String status) {
		List<HashMap> allMenuList = sysMenuInfoDao.qryShows(menu_id, menu_name, up_menu, level, is_show, has_more, status);
		allMenuList = getTreeMenu(allMenuList);
		JSONObject retJson =  new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", allMenuList);
		return retJson;
	}
	
	/**
	 * 	创建菜单
	 * @param menu_name
	 * @param up_menu
	 * @param link_url
	 * @param icon
	 * @param is_show	是否显示 true/false 显示/不显示
	 * @param has_more	是否有下级true/false	是/否
	 * @param remark
	 * @return  {retCode,retMsg}
	 */
	public JSONObject create_menu (String menu_name,String up_menu,String link_url,String icon,String is_show,String has_more,String remark) {
		JSONObject retJson =  new JSONObject();
		String menu_id = sequenceService.getNewSequence("sys_menu_id");
		Integer level = 0;
		if(up_menu != null && up_menu.length() > 0) {
			List<HashMap> menuList = sysMenuInfoDao.qryShows(up_menu, null, null, null, null, null, null);
			if(menuList == null || menuList.size() != 1) {
				retJson.put("retCode", "error");
				retJson.put("retMsg", "上级菜单异常");
				return retJson;
			}
			Integer up_level = (Integer)(menuList.get(0).get("level"));
			level = up_level + 1;
		}else {
			level = 1;
		}
		
		sysMenuInfoDao.add(menu_id, menu_name, level, up_menu, link_url, icon, 0, is_show, has_more, "normal", remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	删除菜单
	 * @param menu_id 菜单编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject delete(String menu_id) {
		JSONObject retJson =  new JSONObject();
		sysMenuInfoDao.update(menu_id, null, null, null, null, null, null, null, null, "delete", null);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
	
	/**
	   *  修改菜单
	 * @param menu_id	菜单编码
	 * @param menu_name	菜单名称
	 * @param seq_index 菜单序列
	 * @param icon      菜单图标
	 * @param link_url  菜单链接地址
	 * @param up_menu	上级菜单
	 * @param level 	级别 1最大
	 * @param is_show	是否显示 true/false 显示/不显示
	 * @param has_more	是否有下级true/false	是/否
	 * @param status	状态 normal/delete 正常/删除
	 * @return {retCode,retMsg}
	 */
	public JSONObject modify(String menu_id,String menu_name,Integer level,String up_menu,String link_url,String icon,Integer seq_index,String is_show,
			String has_more,String status,String remark) {
		JSONObject retJson =  new JSONObject();
		sysMenuInfoDao.update(menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
	
	/**
	 * 	菜单查询
	 * @param menu_id	菜单编码
	 * @param menu_name	菜单名称
	 * @param up_menu	上级菜单
	 * @param level 	级别 1最大
	 * @param is_show	是否显示 true/false 显示/不显示
	 * @param has_more	是否有下级true/false	是/否
	 * @param status	状态 normal/delete 正常/删除
	 * @return {retCode,retMsg,list[menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark ]}
	 */
	public JSONObject list(String menu_id,String menu_name,String up_menu,Integer level,String is_show,String has_more,String status) {
		List<HashMap> allMenuList = sysMenuInfoDao.qryShows(menu_id, menu_name, up_menu, level, is_show, has_more, status);
		JSONObject retJson =  new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", allMenuList);
		return retJson;
	}
}
