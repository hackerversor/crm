package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface SysRoleMenuDao {

	/**
	 *	添加角色
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	public int addRole(@Param("role_id")String role_id,
			@Param("role_name")String role_name,
			@Param("ctime")String ctime,
			@Param("user_id")String user_id,
			@Param("status")String status);
	
	/**
	 *	添加角色菜单
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 */
	public int addRoleMenu(@Param("role_id")String role_id,
			@Param("menu_id")String menu_id);
	
	/**
	 * 	查询角色列表
	 * @param role_id	角色编码
	 * @param role_name	角色名称
	 * @param status  delete normal
	 * return [{ role_id,role_name,ctime,user_id,user_name,status}]
	 */
	public List<HashMap> query(@Param("role_id")String role_id,
			@Param("role_name")String role_name,
			@Param("status")String status);
	
	/**
	*	角色修改  删除原来的，插入新的
	 * @param role_id	角色id
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	public void modify(@Param("role_id")String role_id,
			@Param("role_name")String role_name,
			@Param("menu_id")String[] menu_id);
	
	/**
	*	角色修改  删除原来的，插入新的
	 * @param role_id	角色id
	 * @param role_name	角色名称
	 * @param menu_id	菜单编码数组
	 * @return {retCode,retMsg}
	 */
	public int deleteByRoleid(@Param("role_id")String role_id);
	
	/**
	 * 	角色修改
	  * @param role_id	角色id
	 * @param status	状态 normal delete
	 */
	public List<HashMap> queryByRoleid(@Param("role_id")String role_id);
	
	/**
	 *	删除
	 * @param role_id	角色id
	 */
	public int delete(@Param("role_id")String role_id);
	
	/**
	 *	删除状态
	 * @param role_id	角色id
	 */
	public int update(@Param("role_id")String role_id);
}
