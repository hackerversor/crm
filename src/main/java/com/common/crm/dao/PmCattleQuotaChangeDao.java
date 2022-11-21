package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface PmCattleQuotaChangeDao {
	
	
	/**
	 * 	查询列表
	 * @param cattle_id 买家编码
	 * @param pid  序号
	 * @param payno 流水号
	 * @return [{pid,cattle_id,action_type,action_name,payno,quota,before_quota,after_quota,ctime,remark}]
	 */
	public List<HashMap> list(@Param("cattle_id")String cattle_id,
			@Param("pid")Integer pid,
			@Param("payno")String payno);
	/**
	 * 	查询列表  事务锁定
	 * @param cattle_id 买家编码
	 * @param pid  序号
	 * @param payno 流水号
	 * @return [{pid,cattle_id,action_type,action_name,payno,quota,before_quota,after_quota,ctime,remark}]
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
	 * @param quota
	 * @param before_quota
	 * @param after_quota
	 * @param ctime
	 * @param remark
	 */
	public void add(@Param("cattle_id")String cattle_id,
			@Param("action_type")String action_type,
			@Param("action_name")String action_name,
			@Param("payno")String payno,
			@Param("quota")BigDecimal quota,
			@Param("before_quota")BigDecimal before_quota,
			@Param("after_quota")BigDecimal after_quota,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
}
