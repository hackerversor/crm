package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface PmCattleOrderInfoDao {
	
	/**
	 * 	查询订单列表
	 * @param order_id	订单编码
	 * @param payno	流水号
	 * @param card_no	卡号
	 * @param status	状态
	 * @param s_date	开始日期        开始日期 默认当天 yyyy-MM-dd
	 * @param e_date	结束日期        结束日期 默认当天 yyyy-MM-dd
	 * @param page_num	页数          默认0
	 * @param page_count	页容量      默认30  最大100
	 * @return [{ order_id,payno,cusid,cus_name,shunt,bank_code, bank_name,card_no,notice_id,amt,discount,discount_amt,status,status_msg,oper_status,ctime,utime,a.remark }]
	 * */
	public List<HashMap> order_query(@Param("order_id")String order_id,
									 @Param("payno")String payno,
									 @Param("card_no")String card_no,
									 @Param("status")String status,
									 @Param("s_date")String s_date,
									 @Param("e_date")String e_date,
									 @Param("start_index")Integer start_index,
									 @Param("page_count")Integer page_count);
	/**
	 *  	获取符合条件的数据的条数
	 * @param order_id
	 * @param payno
	 * @param card_no
	 * @param status
	 * @param s_date  开始日期 默认当天 yyyy-MM-dd
	 * @param e_date   结束日期 默认当天 yyyy-MM-dd
	 * @return
	 */
	public Integer statCount(@Param("order_id")String order_id,
						   @Param("payno")String payno,
						   @Param("card_no")String card_no,
						   @Param("status")String status,
						   @Param("s_date")String s_date,
						   @Param("e_date")String e_date);

}
