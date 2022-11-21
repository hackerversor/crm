package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuDriverLicenseFenDao {
	
	/**
	 * 	添加
	 * @param licenseid 驾照编码
	 * @param cusid 客户号
	 * @param fen 扣分
	 * @param query_time 查询时间
	 */
	public void add(@Param("licenseid")String licenseid,
			@Param("cusid")String cusid,
			@Param("fen")String fen,
			@Param("query_time")String query_time);
	
	/**
	 * 	查询列表
	 * @param cusid 客户号
	 * @param licenseid 驾照编码
	 * @return [{licenseid,cusid,fen,query_time}]
	 */
	public List<HashMap> list(@Param("cusid")String cusid,
			@Param("licenseid")String licenseid);
	
	/**
	 * 	根据驾照编码查询
	 * @param licenseid 驾照编码
	 * @return {licenseid,cusid,fen,query_time}
	 */
	public HashMap getById(@Param("licenseid")String licenseid);
	
	/**
	 *	更新记录
	 * @param licenseid 驾照编码
	 * @param fen 扣分
	 * @param query_time 查询时间

	 */
	public void update(@Param("licenseid")String licenseid,
			@Param("fen")Integer fen,
			@Param("query_time")Timestamp query_time);
}
