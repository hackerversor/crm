package com.common.crm.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmStandBookDao {

	/**
	 *	添加台账
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param recrod_status 记录状态 used已使用 unused未使用
	 * @param cusid 客户编码
	 * @param ctime 添加时间
	 * @param act_time 激活时间 
	 * @return {retCode,retMsg}
	 */
	public void add(@Param("tzid")String tzid,
			@Param("channel")String channel,
			@Param("company")String company,
			@Param("policy")String policy,
			@Param("issu_time")String issu_time,
			@Param("issu_acct_no")String issu_acct_no,
			@Param("insure_name")String insure_name,
			@Param("phone1")String phone1,
			@Param("phone2")String phone2,
			@Param("insure_idno")String insure_idno,
			@Param("car_owner_idno")String car_owner_idno,
			@Param("Insured_name")String Insured_name,
			@Param("Insured_idno")String Insured_idno,
			@Param("under_write_date")String under_write_date,
			@Param("car_no")String car_no,
			@Param("engine")String engine,
			@Param("vin")String vin,
			@Param("brand")String brand,
			@Param("reg_date")String reg_date,
			@Param("turn_renew")String turn_renew,
			@Param("kind")String kind,
			@Param("premium")Double premium,
			@Param("rate")String rate,
			@Param("tax")Double tax,
			@Param("sdate")String sdate,
			@Param("edate")String edate,
			@Param("discount_amt")Double discount_amt,
			@Param("discount")Double discount,
			@Param("jy_amt")Double jy_amt,
			@Param("acct_name")String acct_name,
			@Param("acct_amt")Double acct_amt,
			@Param("bank_code")String bank_code,
			@Param("card_no")String card_no,
			@Param("user_id")String user_id,
			@Param("auth_status")String auth_status,
			@Param("premium_type")String premium_type,
			@Param("recrod_status")String recrod_status,
			@Param("ctime")Timestamp ctime);
	
	
	/**
	 *	添加台账
	 * @Param tzid  台账编码
	 * @Param status '状态 send发起审批申请   auth_succ审核成功  auth_fail审核失败   succ终审成功 fail终审失败
	 * @Param  send_user_id 发起人
	 * @Param  send_time 发起时间
	 * @Param  auth_user_id 审核人
	 * @Param  auth_time 审核时间
	 * @Param  last_user_id 终审人
	 * @Param  last_time 终审时间
	 * @Param  auth_remark 审核备注
	 * @Param  ctime 创建时间
	 * @return {retCode,retMsg}
	 */
	public void addStandBookAuth(@Param("tzid")String tzid,
			@Param("status")String status,
			@Param("send_user_id")String send_user_id,
			@Param("send_time")Timestamp send_time,
			@Param("auth_user_id")String auth_user_id,
			@Param("auth_time")Timestamp auth_time,
			@Param("last_user_id")String last_user_id,
			@Param("last_time")Timestamp last_time,
			@Param("auth_remark")String auth_remark,
			@Param("ctime")Timestamp ctime);
	
	/**
	 *	台账查询--业务员
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	
	public List<HashMap> queryForSalesman(
			@Param("company")String company,
			@Param("channel")String channel,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("insure_name")String insure_name,
			@Param("phone1")String phone1,
			@Param("car_no")String car_no,
			@Param("auth_status")String auth_status,
			@Param("user_id")String user_id);
	
	/**
	 *	台账查询-业务经理   业务经理使用
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	public List<HashMap> queryForLeader(
			@Param("company")String company,
			@Param("channel")String channel,
			@Param("user_id")String user_id,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("insure_name")String insure_name,
			@Param("phone1")String phone1,
			@Param("car_no")String car_no,
			@Param("auth_status")String auth_status,
			@Param("auth_user_id")String auth_user_id);
	
	
	/**
	 *	台账查询-总经理   总经理使用 
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	
	public List<HashMap> queryForManager(
			@Param("company")String company,
			@Param("channel")String channel,
			@Param("user_id")String user_id,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("insure_name")String insure_name,
			@Param("phone1")String phone1,
			@Param("car_no")String car_no,
			@Param("auth_status")String auth_status,
			@Param("auth_user_id")String auth_user_id);
	
	
	/**
	 *	台账查询-根据保单号查询所属车辆的所有保单未发起审核和审批失败的保单信息
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,list:tzid,channel,company,issu_time,issu_acct_no,auther,insure_name,phone1,phone2,insure_idno,car_owner_idno,
	 * Insured_name,Insured_idno,car_no,engine,vin,brand,reg_date,turn_renew,policy,under_write_date,kind,premium,tax,sdate,edate,discount_amt,
	 * discount,jy_amt,acct_name,acct_amt,bank_code,card_no,auth_status,user_id}
	 */
	
	public List<HashMap> queryByPolicy(@Param("policy")String policy);
	
	/**
	 *	台账查询-根据保单号查询所属车辆的所有保单未发起审核和审批失败的保单信息
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,list:tzid,channel,company,issu_time,issu_acct_no,auther,insure_name,phone1,phone2,insure_idno,car_owner_idno,
	 * Insured_name,Insured_idno,car_no,engine,vin,brand,reg_date,turn_renew,policy,under_write_date,kind,premium,tax,sdate,edate,discount_amt,
	 * discount,jy_amt,acct_name,acct_amt,bank_code,card_no,auth_status,user_id}
	 */
	
	public List<HashMap> queryByCarNo(@Param("car_no")String car_no,
			@Param("auth_status")String[] auth_status,
			@Param("send_user_id")String send_user_id);
	
	
	/**
	 *	台账查询-业务员   业务员使用   从session中取得操作人的userid，只查询可以修改的记录 init  auth_fail fail
	 * @param under_write_date_s 承保开始日期
	 * @param under_write_date_e 承保结束日期
	 * @param car_no 车牌号
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param user_id 经办人
	 * @return {retCode,retMsg,list:tzid,channel,company,issu_time,issu_acct_no,auther,insure_name,phone1,phone2,insure_idno,car_owner_idno,Insured_name,Insured_idno,car_no,engine,vin,
	 * brand,reg_date,turn_renew,policy,under_write_date,kind,premium,tax,sdate,edate,discount_amt,discount,jy_amt,acct_name,acct_amt,bank_code,card_no,
	 * auth_status,user_id}
	 */
	
	public List<HashMap> queryForSalesmanModify(@Param("company") String company,
			@Param("channel") String channel,
			@Param("under_write_date_s") String under_write_date_s,
			@Param("under_write_date_e") String under_write_date_e,
			@Param("insure_name") String insure_name,
			@Param("phone1") String phone1,
			@Param("car_no") String car_no,
			@Param("auth_status") String[] auth_status,
			@Param("user_id") String user_id);
	
	/**
	 *	台账修改-恢复到初始状态
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	
	public void modifyUnAuthStandBook(@Param("tzid") String tzid, 
			@Param("send_user_id") String send_user_id, 
			@Param("send_time") Timestamp send_time, 
			@Param("auth_user_id") String auth_user_id, 
			@Param("auth_time") Timestamp auth_time,
			@Param("last_user_id") String last_user_id,
			@Param("last_time") Timestamp last_time,
			@Param("auth_remark") String auth_remark,
			@Param("ctime") Timestamp ctime,
			@Param("auth_status") String auth_status);
	
	/**
	 *	台账更新-恢复到初始状态
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param issu_acct_no 	出单账号
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @param auth_status 审批状态 init初始 authing审批中 auth_succ审批通过 auth_fail视频失败
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	
	public void updateStandBook(@Param("tzid") String tzid, 
			@Param("policy") String policy,
			@Param("under_write_date") String under_write_date, 
			@Param("kind") String kind, 
			@Param("premium") String premium, 
			@Param("tax") String tax,
			@Param("sdate") String sdate,
			@Param("edate") String edate,
			@Param("discount") String discount,
			@Param("discount_amt") String discount_amt,
			@Param("jy_amt") String jy_amt,
			@Param("turn_renew") String turn_renew,
			@Param("car_no") String car_no,
			@Param("engine") String engine,
			@Param("vin") String vin,
			@Param("brand") String brand,
			@Param("reg_date") String reg_date,
			@Param("insure_name") String insure_name,
			@Param("phone1") String phone1,
			@Param("phone2") String phone2,
			@Param("insure_idno") String insure_idno,
			@Param("car_owner_idno") String car_owner_idno,
			@Param("car_owner") String car_owner,
			@Param("Insured_name") String Insured_name,
			@Param("acct_name") String acct_name,
			@Param("acct_amt") String acct_amt,
			@Param("bank_code") String bank_code,
			@Param("card_no") String card_no,
			@Param("channel") String channel,
			@Param("company") String company,
			@Param("issu_time") String issu_time,
			@Param("user_id") String user_id,
			@Param("issu_acct_no") String issu_acct_no,
			@Param("auth_status") String auth_status);
	
	
	/**
	 *	台账修改 业务员修改未提交审核和审批失败的台账信息
	 * @param tzid	编码
	 * @param policy	保单号
	 * @param under_write_date	承保日期
	 * @param kind	险种
	 * @param premium 保费
	 * @param tax  车船税
	 * @param sdate 保险起期
	 * @param edate 保险止期
	 * @param discount 折扣
	 * @param discount_amt 折后金额
	 * @param jy_amt 结余金额
	 * @param turn_renew 转保续保
	 * @param car_no 车牌号
	 * @param engine 发动机号
	 * @param vin 车架号
	 * @param brand 车辆品牌型号
	 * @param reg_date 车辆注册日期
	 * @param insure_name	投保人
	 * @param phone1	投保手机1
	 * @param phone2            投保手机2
	 * @param insure_idno 投保人身份证
	 * @param car_owner_idno 车主身份证
	 * @param car_owner 车主
	 * @param Insured_name 被保险人
	 * @param acct_name 收款姓名
	 * @param acct_amt 收款金额
	 * @param bank_code 银行编码
	 * @param card_no 卡号
	 * @param channel 渠道
	 * @param company 承保单位
	 * @param issu_time 出单时间
	 * @param user_id 经办人
	 * @param issu_acct_no 出单账号
	 * @return {retCode,retMsg}
	 */
	public void standBookModify(@Param("tzid") String tzid, 
			@Param("policy") String policy,
			@Param("channel") String channel,
			@Param("company") String company, 
			@Param("issu_time") String issu_time, 
			@Param("user_id") String user_id, 
			@Param("issu_acct_no") String issu_acct_no,
			@Param("insure_name") String insure_name,
			@Param("phone1") String phone1,
			@Param("phone2") String phone2,
			@Param("insure_idno") String insure_idno,
			@Param("insured_name") String insured_name,
			@Param("insured_idno") String insured_idno,
			@Param("car_owner") String car_owner,
			@Param("car_owner_idno") String car_owner_idno,
			@Param("car_no") String car_no,
			@Param("engine") String engine,
			@Param("vin") String vin,
			@Param("brand") String brand,
			@Param("reg_date") String reg_date,
			@Param("acct_name") String acct_name,
			@Param("acct_amt") Double acct_amt,
			@Param("bank_code") String bank_code,
			@Param("card_no") String card_no,
			@Param("under_write_date") String under_write_date,
			@Param("kind") String kind,
			@Param("premium") Double premium,
			@Param("tax") Double tax,
			@Param("sdate") String sdate,
			@Param("edate") String edate,
			@Param("discount") Double discount,
			@Param("discount_amt") Double discount_amt,
			@Param("jy_amt") Double jy_amt,
			@Param("turn_renew") String turn_renew);
	
	
	/**
	 *	台账修改 业务员删除未提交审核或者审核失败的台账  只能删除自己的台账
	 * @param tzid 台账编码
	 * @return {retCode,retMsg}
	 */
	public void standBookDelete(@Param("tzid") String tzid);
	
	/**
	 *	发起台账申请 业务员发起审核，对同一车辆的所有保单发起审核
	 * @param tzid 台账编码
	 * @param car_no 车牌号
	 * @return {retCode,retMsg}
	 */
	public void standBookSendAuth(@Param("tzid") String tzid,
			@Param("car_no") String car_no,
			@Param("user_id") String user_id,
			@Param("auth_status") String auth_status);
	
	/**
	 *	发起台账申请 业务员发起审核，对同一车辆的所有保单发起审核
	 * @param tzid 台账编码
	 * @param car_no 车牌号
	 * @return {retCode,retMsg}
	 */
	public void standBookAuth(@Param("tzid") String tzid,
			@Param("send_user_id") String send_user_id,
			@Param("auth_status") String auth_status);

	/**
	 *	总经理审核审核 对同一辆车的所有保单发起审核  只能审核 auth_succ初审成功状态的数据  统一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	审核状态auth_succ初审成功  auth_fail初审失败
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	public void standBookLeaderAuth(@Param("tzid") String tzid,
			@Param("auth_status") String auth_status);
	
	public void standBookLdAuth(@Param("tzid") String tzid,
			@Param("auth_user_id") String auth_user_id,
			@Param("auth_status") String auth_status,
			@Param("auth_remark") String auth_remark,
			@Param("auth_time") Timestamp auth_time);
	
	/**
	 *	总经理审核审核 对同一辆车的所有保单发起审核  只能审核 auth_succ初审成功状态的数据  统一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	审核状态auth_succ初审成功  auth_fail初审失败
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	public void standBookManagerAuth(@Param("tzid") String tzid,
			@Param("auth_status") String auth_status);
	
	public void standBookMgAuth(@Param("tzid") String tzid,
			@Param("auth_user_id") String auth_user_id,
			@Param("auth_status") String auth_status,
			@Param("auth_remark") String auth_remark,
			@Param("last_user_id") String last_user_id,
			@Param("last_time") Timestamp last_time);


	/**
	 *	台账查询-根据台账编码查询未发起审核所属车辆的所有车牌号
	 * @param tzid	编码
	 * @param car_no 车牌号
	 * @return {car_no}
	 */
	public List<HashMap> queryByTzid(@Param("tzid")String[] tzid,
			@Param("auth_status")String auth_status);
	
	/**
	 *	台账查询-根据台账编码查询未发起审核所属车辆的所有车牌号
	 * @param tzid	编码
	 * @param car_no 车牌号
	 * @return {car_no}
	 */
	public List<HashMap> queryByCar(@Param("car_no")String car_no,
			@Param("auth_status")String auth_status);
	
	/**
	 *	台账查询-all
	 * @param company 承保单位
	 * @param channel渠道
	 * @param under_write_date_s 承保开始日期
     * @param under_write_date_e 承保结束日期
     * @param insure_name 投保人 
     * @param phone1 投保手机1
     * @param car_no 车牌号
     * @param auth_status 审核状态
     * @param page_num 页数
     * @param page_count 页容量
	 * @return [{tzid,channel,company,policy,issu_time,issu_acct_no,insure_name,phone1,phone2,insure_idno,car_owner_idno,Insured_name,
		insured_idno,under_write_date,car_no,engine,vin,brand,reg_date,turn_renew,kind,premium,rate,tax,sdate,edate,discount_amt,
		discount,jy_amt,acct_name,acct_amt,bank_code,card_no,user_id,auth_status,recrod_status,ctime,
		c.name user_name,d.name auther}]
	 */
	public List<HashMap> list(@Param("company")String company,
			@Param("channel")String channel,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("insure_name")String insure_name,
			@Param("kind")String kind,
			@Param("car_no")String car_no,
			@Param("auth_status")String auth_status,
			@Param("page_num")Integer page_num,
			@Param("page_count")Integer page_count,
			@Param("user_id")String user_id);
	
	/**
	 *	计算符合条件记录数量
	 * @param company 承保单位
	 * @param channel渠道
	 * @param under_write_date_s 承保开始日期
     * @param under_write_date_e 承保结束日期
     * @param insure_name 投保人 
     * @param phone1 投保手机1
     * @param car_no 车牌号
     * @param auth_status 审核状态
     * @param page_num 页数
     * @param page_count 页容量
	 * @return {retCode,retMsg list，tzid，channel，company，issu_time，issu_acct_no，auther，insure_name，phone1，phone2
		，insure_idno，car_owner_idno，Insured_name，Insured_idno，car_no，engine，vin，brand，reg_date，turn_renew，policy，
		under_write_date，kind，premium，tax，sdate，edate，discount_amt，discount，jy_amt，acct_name，acct_amt，bank_code，
		card_no，auth_status，user_id，total_premium，count
	 */
	public Long queryStatCount(@Param("company")String company,
			@Param("channel")String channel,
			@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("insure_name")String insure_name,
			@Param("kind")String kind,
			@Param("car_no")String car_no,
			@Param("auth_status")String auth_status,
			@Param("user_id")String user_id);
	
	
	/**
	 * 	商险成单数排序    
	 * @param under_write_date_s 开始日期  yyyy-MM-dd
	 * @param under_write_date_e 结束日期  yyyy-MM-dd
	 * @return [{count,premium,user_id,name}]
	 */
	public List<HashMap> count_user_order(@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("kind")String[] kind);
	
	
	/**
	 * 	商险保费排序  
	 * @param under_write_date_s 开始日期 yyyy-MM-dd
	 * @param under_write_date_e 结束日期  yyyy-MM-dd
	 * @param kinds 险种
	 * @return [{premium,user_id,name}]
	 */
	public List<HashMap> premium_user_order(@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("kind")String[] kind);
	
	/**
	 * 	统计保费和数量
	 * @param under_write_date_s
	 * @param under_write_date_e
	 * @param kind
	 * @param car_no
	 * @param user_id
	 * @return [{premium,count}]
	 */
	public HashMap premium_count(@Param("under_write_date_s")String under_write_date_s,
			@Param("under_write_date_e")String under_write_date_e,
			@Param("kind")String[] kind,
			@Param("car_no")String car_no,
			@Param("user_id")String user_id);
	
}
