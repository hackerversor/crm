package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuTelOilFilterParamDao {
	
	/**
	 * 	查询列表
	 * @param payno 流水号
	 * @param cusid	客户号
	 * @param rech_no	充值号
	 * @param status 状态 init  deal finish
	 * @return [{payno,cusid,pay_type,rech_type,rech_no,amt,status,ext,remark}]
	 */
	public List<HashMap> list(@Param("payno")String payno,
			@Param("cusid")String cusid,
			@Param("rech_no")String rech_no,
			@Param("status")String status);
	
	/**
	 * 	添加记录
	 * @param payno
	 * @param cusid
	 * @param pay_type bean
	 * @param rech_type tel zsh zsy
	 * @param rech_no
	 * @param amt
	 * @param status 状态 init  deal finish
	 * @param ext
	 * @param remark
	 */
	public void add(@Param("payno")String payno,
			@Param("cusid")String cusid,
			@Param("pay_type")String pay_type,
			@Param("rech_type")String rech_type,
			@Param("rech_no")String rech_no,
			@Param("amt")BigDecimal amt,
			@Param("status")String status,
			@Param("ext")String ext,
			@Param("remark")String remark);
}
