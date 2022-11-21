package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuTelOilOrderDao {
	
	/**
	 * 	查询订单列表
	 * @param order_id	订单编码
	 * @param payno 流水号
	 * @param cusid 客户号
	 * @param rech_no  充值号码
	 * @param status 状态
	 * @param s_date 开始日期 yyyy-MM-dd
	 * @param e_date 结束锐气yyyy-MM-dd 
	 * @return [{order_id,payno,shunt,supplier,cusid,rech_type,rech_no,supplier_proid,amt,supplier_amt,status,status_msg,oper_status,supplier_order,stime,etime,operid,remark}]
	 */
	public List<HashMap> list(@Param("order_id")String order_id,
			@Param("payno")String payno,
			@Param("cusid")String cusid,
			@Param("rech_no")String rech_no,
			@Param("status")String status,
			@Param("s_date")String s_date,
			@Param("e_date")String e_date);
	/**
	 * 	添加记录
	 * @param order_id 订单编码
	 * @param payno 流水号
	 * @param shunt 分流渠道
	 * @param supplier 供应商
	 * @param cusid 客户号
	 * @param rech_type 充值类型 tel zsy zsh
	 * @param rech_no 充值号码
	 * @param supplier_proid 供应商产品编码
	 * @param amt 金额
	 * @param supplier_amt 供应商金额
	 * @param status 状态 succ-成功、running-充值中、fail-失败已冲正 
	 * @param status_msg 状态描述
	 * @param oper_status init初始状态  deal正在处理  finish完成
	 * @param supplier_order 供应商订单号
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param operid 操作人编码
	 * @param remark 备注
	 */
	public void add(@Param("order_id")String order_id,
			@Param("payno")String payno,
			@Param("shunt")String shunt,
			@Param("supplier")String supplier,
			@Param("cusid")String cusid,
			@Param("rech_type")String rech_type,
			@Param("rech_no")String rech_no,
			@Param("supplier_proid")String supplier_proid,
			@Param("amt")BigDecimal amt,
			@Param("supplier_amt")BigDecimal supplier_amt,
			@Param("status")String status,
			@Param("status_msg")String status_msg,
			@Param("oper_status")String oper_status,
			@Param("supplier_order")String supplier_order,
			@Param("stime")Timestamp stime,
			@Param("etime")Timestamp etime,
			@Param("operid")String operid,
			@Param("remark")String remark);
	
	/**
	 * 	删除记录
	 * @param order_id
	 */
	public void delete(@Param("order_id")String order_id);
	
	
	/**
	 * 	更新记录
	 * @param order_id
	 * @param supplier_amt
	 * @param status 状态 succ-成功、running-充值中、fail-失败已冲正
	 * @param status_msg
	 * @param oper_status init初始状态  deal正在处理  finish完成
	 * @param supplier_order
	 * @param etime
	 * @param operid
	 * @param remark
	 */
	public void update(@Param("order_id")String order_id,
			@Param("supplier_amt")BigDecimal supplier_amt,
			@Param("status")String status,
			@Param("status_msg")String status_msg,
			@Param("oper_status")String oper_status,
			@Param("supplier_order")String supplier_order,
			@Param("etime")Timestamp etime,
			@Param("operid")String operid,
			@Param("remark")String remark);
	
	/**
	 * 	根据订单编码查询数据
	 * @param order_id
	 * @return {order_id,payno,shunt,supplier,cusid,rech_type,rech_no,supplier_proid,amt,supplier_amt,status,status_msg,oper_status,supplier_order,stime,etime,operid,remark}
	 */
	public HashMap getByOrderid(@Param("order_id")String order_id);
	/**
	 * 	根据订单编码查询数据 事务锁定
	 * @param order_id
	 * @return {order_id,payno,shunt,supplier,cusid,rech_type,rech_no,supplier_proid,amt,supplier_amt,status,status_msg,oper_status,supplier_order,stime,etime,operid,remark}
	 */
	public HashMap getByOrderidLock(@Param("order_id")String order_id);
	
	/**
	 * 	查询处理中的订单
	 * @return [{order_id,payno,shunt,supplier,cusid,rech_type,rech_no,supplier_proid,amt,supplier_amt,status,status_msg,oper_status,supplier_order,stime,etime,operid,remark}]
	 */
	public List<HashMap> queryRunOrder();
	
}
