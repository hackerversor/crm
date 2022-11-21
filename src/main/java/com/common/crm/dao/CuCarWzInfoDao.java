package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuCarWzInfoDao {
	
	
	/**
	 * 	添加记录
	 * @param carid  车辆编码
	 * @param cusid 客户号
	 * @param query_date 查询日期
	 * @param wz_time 违章时间
	 * @param addr 违章地址
	 * @param act 违章行为
	 * @param code 违章编码
	 * @param fen 扣分
	 * @param city 违章城市
	 * @param money 扣款
	 * @param handled 是否处理
	 * @param archiveno 文书编号
	 */
	public void add(@Param("carid")String carid,
			@Param("cusid")String cusid,
			@Param("query_date")Date query_date,
			@Param("wz_time")Timestamp wz_time,
			@Param("addr")String addr,
			@Param("act")String act,
			@Param("code")String code,
			@Param("fen")Integer fen,
			@Param("city")String city,
			@Param("money")Integer money,
			@Param("handled")String handled,
			@Param("archiveno")String archiveno );
	
	
	/**
	 *  	查询违章信息
	 * @param carid
	 * @param cusid
	 * @param car_no
	 * @return [{pid,carid,cusid,query_date,wz_time,addr,act,code,fen,city,money,handled,archiveno}]
	 */
	public List<HashMap> list(@Param("carid")String carid,
			@Param("cusid")String cusid,
			@Param("query_date")Date query_date);
	
	

}
