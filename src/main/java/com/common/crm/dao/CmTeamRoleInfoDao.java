package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmTeamRoleInfoDao {
	/**
	 * 	添加或者替换
	 * @param team_id 团队编码
	 * @param role_id 角色编码
	 */
	public void replace(@Param("team_id")String team_id,@Param("role_id")String role_id);
	
	/**
	 * 	删除团队角色
	 * @param team_id 团队编码 必输
 	 * @param role_id 角色编码 可以为空
	 */
	public void delete(@Param("team_id")String team_id,@Param("role_id")String role_id);
	
	/**
	 * 	查询团队角色记录
	 * @param team_id 必输
	 * @param role_id 必输
	 * @return {team_id,role_id,role_name}
	 */
	public HashMap queryOne(@Param("team_id")String team_id,@Param("role_id")String role_id);
	
	/**
	 * 	查询业务员所属的团队的信息(团队,角色,角色名称role_id==null)
	    *      查询业务员所属的团队的信息(名称,编码,角色名称role_id!=null)
	 * @param team_id 
	 * @param role_id 可为空
	 * @return [{team_id,role_id,role_name}]
	 */
	public List<HashMap> queryTeamAndRole(@Param("team_id")String team_id,@Param("role_id")String role_id);

		
}
