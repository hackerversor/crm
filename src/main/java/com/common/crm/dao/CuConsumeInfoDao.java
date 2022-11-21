package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuConsumeInfoDao {
	
	/**
	 * 	查询订单信息
	 * @param payno
	 * @param cusid
	 * @param status init fail  succ
	 * @param tran_type
	 * @return [{payno,cusid,amt,pay_type,tran_type,ctime,utime,status,remark}]
	 */
	public List<HashMap> list(@Param("payno")String payno,
			@Param("cusid")String cusid,
			@Param("status")String status,
			@Param("tran_type")String tran_type);
	
	/**
	 * 	获取订单信息 根据流水号
	 * @param payno
	 * @return
	 */
	public HashMap getByPayno(@Param("payno")String payno);
	
	/**
	 * 	获取订单信息 根据流水号 事务锁定
	 * @param payno
	 * @return
	 */
	public HashMap getByPaynoLock(@Param("payno")String payno);
	
	/**
	 * 	添加记录
	 * @param payno
	 * @param cusid
	 * @param amt
	 * @param pay_type bean
	 * @param tran_type
	 * @param ctime
	 * @param utime
	 * @param status init fail  succ
	 * @param remark
	 */
	public void add(@Param("payno")String payno,
			@Param("cusid")String cusid,
			@Param("amt")BigDecimal amt,
			@Param("pay_type")String pay_type,
			@Param("tran_type")String tran_type,
			@Param("ctime")Timestamp ctime,
			@Param("utime")Timestamp utime,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	更新状态
	 * @param payno
	 * @param status init fail  succ
	 */
	public void updateStatus(@Param("payno")String payno,
			@Param("status")String status,
			@Param("utime")Timestamp utime);
	
	/**
	 * 	查询客户订单详情
	 * @param cusid
	 * @return [{payno,amt,pay_type,tran_type,tran_name,ctime,utime,status,rech_no,status_msg,cus_name,bank_name}]
	 */
	public List<HashMap> orderDetailList(@Param("cusid")String cusid);
	
}

