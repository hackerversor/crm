package com.common.crm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SysOutParamInfoDao {
	
	/**
	 * 	查询参数列表
	 * @param pid 系统编码
	 * @param param_code 参数编码
	 * @return [{pid,param_code,param_value,param_desc}]
	 */
	public List<HashMap> list(@Param("pid")Integer pid,
			@Param("param_code")String param_code);
	/**
	 * 	根据pid查询
	 * @param pid
	 * @return {pid,param_code,param_value,param_desc}
	 */
	public HashMap getByPid(@Param("pid")Integer pid);
	
	/**
	 * 	根据code查询
	 * @param param_code
	 * @return {pid,param_code,param_value,param_desc}
	 */
	public HashMap getByCode(@Param("param_code")String param_code);
}
