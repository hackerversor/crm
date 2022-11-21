package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmUserRoleInfoDao {
	/**
	 * 	添加或者替换
	 * @param user_id 用户编码
	 * @param role_id 角色编码
	 */
	public void replace(@Param("user_id")String user_id,@Param("role_id")String role_id);
	
	/**
	 * 	删除用户角色
	 * @param user_id 用户编码 必输
 	 * @param role_id 角色编码 可以为空
	 */
	public void delete(@Param("user_id")String user_id,@Param("role_id")String role_id);
	
	/**
	 * 	查询用户角色记录
	 * @param user_id 必输
	 * @param role_id 必输
	 * @return {user_id,role_id,role_name}
	 */
	public HashMap queryOne(@Param("user_id")String user_id,@Param("role_id")String role_id);
	
	/**
	 * 	查询用户角色记录
	 * @param user_id 可为空
	 * @param role_id 可为空
	 * @return [{user_id,role_id,role_name}]
	 */
	public List<HashMap> queryShow(@Param("user_id")String user_id,@Param("role_id")String role_id);
	
	
}
