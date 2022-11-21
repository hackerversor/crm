package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.SysMenuInfoService;

@Controller
public class SysMenuController {
	
	@Autowired
	private SysMenuInfoService sysMenuInfoService;
	
	
	/**
	 * 	根据用户权限查询出客户相对应的菜单信息
	 * @return  {retCode,retMsg,list:[menus1,sub_menu:[menus]]}
	 */
	@RequestMapping(path = "/crm/sys_menu/query_for_user")
	@ResponseBody
	public String query_for_user() {
		JSONObject retJson = sysMenuInfoService.getMenuForTreeByUser();
		return retJson.toJSONString();
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
	 * @return {retCode,retMsg,list:[menus1,sub_menu:[menus]]}
	 */
	@RequestMapping(path = "/crm/sys_menu/query")
	@ResponseBody
	public String query(@RequestParam(name = "menu_id",required = false)String menu_id,
			@RequestParam(name = "menu_name",required = false)String menu_name,
			@RequestParam(name = "up_menu",required = false)String up_menu,
			@RequestParam(name = "level",required = false)Integer level,
			@RequestParam(name = "is_show",required = false)String is_show,
			@RequestParam(name = "has_more",required = false)String has_more,
			@RequestParam(name = "status",required = false)String status) {
		JSONObject retJson = sysMenuInfoService.query(menu_id, menu_name, up_menu, level, is_show, has_more, status);
		return retJson.toJSONString();
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
	@RequestMapping(path = "/crm/sys_menu/create_menu")
	@ResponseBody
	public String create_menu(@RequestParam(name = "menu_name",required = true)String menu_name,
			@RequestParam(name = "up_menu",required = false)String up_menu,
			@RequestParam(name = "link_url",required = false)String link_url,
			@RequestParam(name = "icon",required = true)String icon,
			@RequestParam(name = "is_show",required = true)String is_show,
			@RequestParam(name = "has_more",required = true)String has_more,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = sysMenuInfoService.create_menu(menu_name, up_menu, link_url, icon, is_show, has_more, remark);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除菜单
	 * @param menu_id 菜单编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/sys_menu/delete")
	@ResponseBody
	public String delete(@RequestParam(name = "menu_id",required = true)String menu_id) {
		JSONObject retJson = sysMenuInfoService.delete(menu_id);
		return retJson.toJSONString();
	}
	
	/**
	 * 	修改菜单
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
	@RequestMapping(path = "/crm/sys_menu/modify_menu")
	@ResponseBody
	public String modify(@RequestParam(name = "menu_id",required = true)String menu_id,
			@RequestParam(name = "menu_name",required = false)String menu_name,
			@RequestParam(name = "level",required = false)Integer level,
			@RequestParam(name = "up_menu",required = false)String up_menu,
			@RequestParam(name = "link_url",required = false)String link_url,
			@RequestParam(name = "icon",required = false)String icon,
			@RequestParam(name = "seq_index",required = false)Integer seq_index,
			@RequestParam(name = "is_show",required = false)String is_show,
			@RequestParam(name = "has_more",required = false)String has_more,
			@RequestParam(name = "status",required = false)String status,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = sysMenuInfoService.modify(menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark);
		return retJson.toJSONString();
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
	 * @return {retCode,retMsg,list:[menus1,sub_menu:[menus]]}
	 */
	@RequestMapping(path = "/crm/sys_menu/list")
	@ResponseBody
	public String list(@RequestParam(name = "menu_id",required = false)String menu_id,
			@RequestParam(name = "menu_name",required = false)String menu_name,
			@RequestParam(name = "up_menu",required = false)String up_menu,
			@RequestParam(name = "level",required = false)Integer level,
			@RequestParam(name = "is_show",required = false)String is_show,
			@RequestParam(name = "has_more",required = false)String has_more,
			@RequestParam(name = "status",required = false)String status) {
		JSONObject retJson = sysMenuInfoService.list(menu_id, menu_name, up_menu, level, is_show, has_more, status);
		return retJson.toJSONString();
	}
}
