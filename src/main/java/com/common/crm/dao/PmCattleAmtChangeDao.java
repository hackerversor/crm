package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface PmCattleAmtChangeDao {

	/**
	 * 	查询列表
	 * @param cattle_id 买家编码
	 * @param pid  序号
	 * @param payno 流水号
	 * @return [{pid,cattle_id,action_type,action_name,payno,amt,before_amt,after_amt,ctime,remark}]
	 */
	public List<HashMap> list(@Param("cattle_id")String cattle_id,
			@Param("pid")Integer pid,
			@Param("payno")String payno);
	/**
	 * 	查询列表  事务锁定
	 * @param cattle_id 买家编码
	 * @param pid  序号
	 * @param payno 流水号
	 * @return [{pid,cattle_id,action_type,action_name,payno,amt,before_amt,after_amt,ctime,remark}]
	 */
	public List<HashMap> listLock(@Param("cattle_id")String cattle_id,
			@Param("pid")Integer pid,
			@Param("payno")String payno);
	
	/**
	 * 	添加记录 
	 * @param cattle_id
	 * @param action_type in_add out_sub
	 * @param action_name
	 * @param payno
	 * @param amt
	 * @param before_amt
	 * @param after_amt
	 * @param ctime
	 * @param remark
	 */
	public void add(@Param("cattle_id")String cattle_id,
			@Param("action_type")String action_type,
			@Param("action_name")String action_name,
			@Param("payno")String payno,
			@Param("amt")BigDecimal amt,
			@Param("before_amt")BigDecimal before_amt,
			@Param("after_amt")BigDecimal after_amt,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
}
