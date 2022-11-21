package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface SysIconInfoDao {
	
	/**
	 * 	查询图标信息
	 * @param icon_title
	 * @return [{icon_title,icon_path,icon_desc}]
	 */
	public List<HashMap> qryShows(@Param("icon_title")String icon_title);
}
