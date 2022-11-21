package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface StatDao {
	/**
	 * 	统计数据情况
	 * 
	累计用户数量: 
	当天新增用户数量: 
	累计激活金额: 
	当天新增激活金额； 
	累计消费金额:  
	累计消费油话金额:  
	累计消费交易市场金额: 
	当天新增消费金额: 
	当天新增消费油话金额: 
	当天新增消费交易市场金额: 
	累计客户余额
	累计未激活金额: 
	 * @return {name,count}
	 */
	public List<HashMap> queryForQR();
	
	
	/**
	 *	台账汇总查询 --  按照日、月、年等维度汇总台账信息
	 * @param company 承保单位
	 * @param channel渠道
	 * @param under_write_date_s 开始日期
     * @param under_write_date_e 结束日期
     * @param stat_type 统计类型 
     * @param kind 险种类型
	 * @return [{count,premium,kind,under_write_date}]
	 */
	public List<HashMap> dmyQueryByMonth(@Param("company")String company,
			@Param("channel")String channel,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("user_id")String user_id,
			@Param("kind")String[] kind);
	
	
	/**
	 *	台账汇总查询 --  按照日、月、年等维度汇总台账信息
	 * @param company 承保单位
	 * @param channel渠道
	 * @param under_write_date_s 开始日期
     * @param under_write_date_e 结束日期
     * @param stat_type 统计类型
     * @param kind 险种类型 
	 * @return [{date,datalist:kind,premium,count;total_premium,total_count}]
	 */
	public List<HashMap> dmyQueryByDay(@Param("company")String company,
			@Param("channel")String channel,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("user_id")String user_id,
			@Param("kind")String[] kind);


	/**
	 *	员工保费汇总查询 --  按照员工分组
	 * @param sdate 承保开始日、周、月
     * @param edate 承保结束日、周、月
     * @param order_field 排序字段 
     * @param order_type  排序类型
	 * @return [{user_id,name,policy_count,cx_premium,fc_premium}]
	 */
	public List<HashMap> queryByUser(@Param("sdate")String sdate,
			@Param("edate")String edate,
			@Param("order_field")String order_field,
			@Param("order_type")String order_type);
	
	/**
	 * 	员工保费汇总  按照员工和时间分组  按照时间人员正序
	 * @param under_write_date_s 承保日期起期
	 * @param under_write_date_e 承保日期止期
	 * @param kind[] 险种列表
	 * @param user_id[] 用户列表
	 * @param stat_type M按月 D按天
	 * @return [{under_write_date,user_id,user_name,count,premium}]
	 */
	public List<HashMap> stat_user_data(@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("kind")String[] kind,
			@Param("user_id")String[] user_id,
			@Param("stat_type")String stat_type);


	/**
	 *	周期业绩统计查询  按照保费倒叙  
	 * 	@param under_write_date_s 日期    yyyy-MM-dd
	 *  @param under_write_date_e 
	 *  @param kind
	 *  @param user_id
	 *  @return [{user_id,user_name,count,premium}]
	 */
	public List<HashMap> premium_by_date(@Param("under_write_date_s")String under_write_date_s, 
			@Param("under_write_date_e")String under_write_date_e, 
			@Param("kind")String[] kind,
			@Param("user_id")String[] user_id);

}
