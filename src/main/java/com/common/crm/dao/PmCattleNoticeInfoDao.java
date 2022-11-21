package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface PmCattleNoticeInfoDao {
	
	/**
	 * 	查询列表
	 * @param notice_id	编码
	 * @param cattle_id 买家编码
	 * @param status 状态 useable unuseable
	 * @return [{notice_id,cattle_id,title,sdate,edate,ctime,amt,discount,max_times,used_times,status,remark}]
	 */
	public List<HashMap> list(@Param("notice_id")String notice_id,
			@Param("cattle_id")String cattle_id,
			@Param("status")String status);
	
	/**
	 * 	查询买家已经使用的额度
	 * @param cattle_id
	 * @return 
	 */
	public BigDecimal used_quota(@Param("cattle_id")String cattle_id);
	
	/**
	 * 	查询所有可用的收购信息  状态可用  在有效期内
	 * @return [{notice_id,cattle_id,title,sdate,edate,ctime,amt,discount,max_times,used_times,status,remark}]
	 */
	public List<HashMap> qryAllCanUse();
	
	/**
	 * 	添加记录
	 * @param notice_id	收购信息编码
	 * @param cattle_id 买家编码
	 * @param title 标题
	 * @param sdate 开始日期
	 * @param edate 结束日期
	 * @param ctime 添加时间
	 * @param amt 面额
	 * @param discount 折扣
	 * @param max_times 最大次数
	 * @param used_times 使用次数
	 * @param status 状态 useable unuseable
	 * @param remark 备注
	 */
	public void add(@Param("notice_id")String notice_id,
			@Param("cattle_id")String cattle_id,
			@Param("title")String title,
			@Param("sdate")String sdate,
			@Param("edate")String edate,
			@Param("ctime")String ctime,
			@Param("amt")BigDecimal amt,
			@Param("discount")BigDecimal discount,
			@Param("max_times")Integer max_times,
			@Param("used_times")Integer used_times,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	查询正常状态的收购信息  状态正常 有效期内 次数未被使用完
	 * @param notice_id
	 * @return {notice_id,cattle_id,title,sdate,edate,ctime,amt,discount,max_times,used_times,status,remark}
	 */
	public HashMap getById(@Param("notice_id")String notice_id);
	
	/**
	 * 	查询正常任何状态的收购信息
	 * @param notice_id
	 * @return {notice_id,cattle_id,title,sdate,edate,ctime,amt,discount,max_times,used_times,status,remark}
	 */
	public HashMap getAllById(@Param("notice_id")String notice_id);

	/**
	 * 	查询正常状态的收购信息  状态正常 有效期内 次数未被使用完 事务锁定
	 * @param notice_id
	 * @return {notice_id,cattle_id,title,sdate,edate,ctime,amt,discount,max_times,used_times,status,remark}
	 */
	public HashMap getByIdLock(@Param("notice_id")String notice_id);
	
	/**
	 * 	使用次数加一
	 * @param notice_id
	 */
	public void usedTimeAdd(@Param("notice_id")String notice_id);
	/**
	 * 	使用次数 减一
	 * @param notice_id
	 */
	public void usedTimeSub(@Param("notice_id")String notice_id);
}
