package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

@MapperScan
public interface CuAcctInfoDao {
	
	/**
	 * 	添加账户
	 * @param acctno 账号
	 * @param cusid 客户号
	 * @param type 类型 预留
	 * @param balance 余额
	 * @param status 状态 normal delete
	 * @param ctime 创建时间
	 * @param remark 备注
	 */
	public void add(@Param("acctno")String acctno,
			@Param("cusid")String cusid,
			@Param("type")String type,
			@Param("balance")BigDecimal balance,
			@Param("status")String status,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
	
	
	/**
	 * 	余额查询  正常状态的账户
	 * @param cusid 客户号
	 * @return balance
	 */
	public BigDecimal balance(@Param("cusid")String cusid);
	
	/**
	 * 	查询客户余账户信息 正常状态的账户  事务锁定
	 * @param cusid 客户号
	 * @return {acctno,cusid,type,balance,status,ctime,remark}
	 */
	public HashMap getByCusidLock(@Param("cusid")String cusid);
	
	/**
	 *  	更新余额
	 * @param acctno
	 * @param balance
	 */
	public void updateBalance(@Param("acctno")String acctno,
			@Param("balance")BigDecimal balance);
	
}
