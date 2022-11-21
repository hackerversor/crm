package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface SysMenuInfoDao {

	/**
	 * 	查询菜单信息
	 * @param menu_id	菜单编码
	 * @param menu_name	菜单名称
	 * @param up_menu	上级菜单
	 * @param level 	级别 1最大
	 * @param is_show	是否显示 true/false 显示/不显示
	 * @param has_more	是否有下级true/false	是/否
	 * @param status	状态 normal/delete 正常/删除
	 * @return [{menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark }]
	 */
	public List<HashMap> qryShows(@Param("menu_id")String menu_id,
			@Param("menu_name")String menu_name,
			@Param("up_menu")String up_menu,
			@Param("level")Integer level,
			@Param("is_show")String is_show,
			@Param("has_more")String has_more,
			@Param("status")String status);
	
	
	/**
	 *	添加菜单
	 * @param menu_id 菜单编码
	 * @param menu_name 名称
	 * @param level 级别 1最大
	 * @param up_menu 上级菜单
	 * @param link_url 链接地址
	 * @param icon 图标
	 * @param seq_index 顺序
	 * @param is_show 是否显示 true/false 显示/不显示
	 * @param has_more 是否有下级true/false	是/否
	 * @param status 状态 normal/delete 正常/删除
	 * @param remark 备注
	 */
	public void add(@Param("menu_id")String menu_id,
			@Param("menu_name")String menu_name,
			@Param("level")int level,
			@Param("up_menu")String up_menu,
			@Param("link_url")String link_url,
			@Param("icon")String icon,
			@Param("seq_index")Integer seq_index,
			@Param("is_show")String is_show,
			@Param("has_more")String has_more,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 *	更新菜单
	 * @param menu_id 菜单编码
	 * @param menu_name 名称
	 * @param level 级别 1最大
	 * @param up_menu 上级菜单
	 * @param link_url 链接地址
	 * @param icon 图标
	 * @param seq_index 顺序
	 * @param is_show 是否显示 true/false 显示/不显示
	 * @param has_more 是否有下级true/false	是/否
	 * @param status 状态 normal/delete 正常/删除
	 * @param remark 备注
	 */
	public void update(@Param("menu_id")String menu_id,
			@Param("menu_name")String menu_name,
			@Param("level")Integer level,
			@Param("up_menu")String up_menu,
			@Param("link_url")String link_url,
			@Param("icon")String icon,
			@Param("seq_index")Integer seq_index,
			@Param("is_show")String is_show,
			@Param("has_more")String has_more,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	根据用户查询有权限的菜单列表
	 * @param user_id 用户编码
	 * @param status 状态 normal/delete 正常/删除
	 * @param is_show 是否显示 true/false 显示/不显示 
	 * @return [{menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark }]
	 */
	public List<HashMap> getMenusByUser(@Param("user_id")String user_id,@Param("status")String status,@Param("is_show")String is_show);
	
	/**
	 * 	根据用户查询有权限的菜单列表
	 * @param role_id 角色编码
	 * @param status 状态 normal/delete 正常/删除
	 * @param is_show 是否显示 true/false 显示/不显示 
	 * @return [{menu_id,menu_name,level,up_menu,link_url,icon,seq_index,is_show,has_more,status,remark }]
	 */
	public List<HashMap> getMenusByRole(@Param("role_id")String role_id,@Param("status")String status,@Param("is_show")String is_show);
	
	
}
