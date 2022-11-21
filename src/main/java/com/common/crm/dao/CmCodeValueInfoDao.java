package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmCodeValueInfoDao {

	/**
	 *	码值新增
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	            编码描述
	 */
	public void add(@Param("param_class")String param_class,
			@Param("class_desc")String class_desc,
			@Param("param_code")String param_code,
			@Param("code_desc")String code_desc);
	
	
	/**
	 * 	码值查询
	 * @param param_class	类别
	 * @param class_desc	类别描述
	 * @param param_code	编码
	 * @param code_desc	           编码描述
	 */
	public List<HashMap> query(@Param("param_class")String param_class,
			@Param("param_code")String param_code);
	
	
	/**
	 *	删除
	 * @param param_class	类别
	 * @param param_code	编码
	 */
	public void delete(@Param("param_class")String param_class,
			@Param("param_code")String param_code);
}
