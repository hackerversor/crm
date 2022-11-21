package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuAcctChangeInfoDao {
	
	/**
	 * 	查询列表
	 * @param change_id
	 * @param acctno
	 * @param cusid
	 * @param payno
	 * @param action_type in_add out_sub
	 * @return [{change_id,acctno,cusid,action_type,action_name,payno,amt,bf_balance,af_balance,ctime,remark}]
	 */
	public List<HashMap> list(@Param("change_id")String change_id,
			@Param("acctno")String acctno,
			@Param("cusid")String cusid,
			@Param("payno")String payno,
			@Param("action_type")String action_type);
	
	/**
	 * 	查询列表 事务锁定
	 * @param change_id
	 * @param acctno
	 * @param cusid
	 * @param payno
	 * @param action_type in_add out_sub
	 * @return [{change_id,acctno,cusid,action_type,action_name,payno,amt,bf_balance,af_balance,ctime,remark}]
	 */
	public List<HashMap> listLock(@Param("change_id")String change_id,
			@Param("acctno")String acctno,
			@Param("cusid")String cusid,
			@Param("payno")String payno,
			@Param("action_type")String action_type);

	/**
	 * 添加
	 * @param change_id	
	 * @param acctno
	 * @param cusid
	 * @param action_type in_add out_sub
	 * @param action_name
	 * @param payno
	 * @param amt
	 * @param bf_balance
	 * @param af_balance
	 * @param ctime
	 * @param remark
	 */
	public void add(@Param("change_id")String change_id,
			@Param("acctno")String acctno,
			@Param("cusid")String cusid,
			@Param("action_type")String action_type,
			@Param("action_name")String action_name,
			@Param("payno")String payno,
			@Param("amt")BigDecimal amt,
			@Param("bf_balance")BigDecimal bf_balance,
			@Param("af_balance")BigDecimal af_balance,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
	
}
