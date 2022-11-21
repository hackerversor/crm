package com.common.crm.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmStandBookService;


@Controller
public class CmStandBookController {
	
	@Autowired
	private CmStandBookService cmStandBookService;
	
	/**
	 *	添加台账
	 * @param tzid	编码
	 * @param policys	保单信息
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
	 * @param issu_acct_no 出单账号
	 * @param auther 审核人
	 * @return {retCode,retMsg}
	 * @throws IOException 
	 */
	
	@RequestMapping(path = "/crm/stand_book/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "company",required = true)String company,
			@RequestParam(name = "issu_time",required = false)String issu_time,
			@RequestParam(name = "issu_acct_no",required = true)String issu_acct_no,
			@RequestParam(name = "auther",required = true)String auther,
			@RequestParam(name = "insure_name",required = true)String insure_name,
			@RequestParam(name = "phone1",required = true)String phone1,
			@RequestParam(name = "phone2",required = false)String phone2,
			@RequestParam(name = "insure_idno",required = false)String insure_idno,
			@RequestParam(name = "car_owner_idno",required = false)String car_owner_idno,
			@RequestParam(name = "Insured_name",required = false)String Insured_name,
			@RequestParam(name = "Insured_idno",required = false)String Insured_idno,
			@RequestParam(name = "car_no",required = false)String car_no,
			@RequestParam(name = "engine",required = true)String engine,
			@RequestParam(name = "vin",required = true)String vin,
			@RequestParam(name = "brand",required = false)String brand,
			@RequestParam(name = "reg_date",required = false)String reg_date,
			@RequestParam(name = "turn_renew",required = false)String turn_renew,
			@RequestParam(name = "policys",required = true)String policys,
			@RequestParam(name = "acct_name",required = false)String acct_name,
			@RequestParam(name = "acct_amt",required = false)String acct_amt,
			@RequestParam(name = "bank_code",required = false)String bank_code,
			@RequestParam(name = "card_no",required = false)String card_no,
			@RequestParam(name = "send_auth",required = true)String send_auth,
			@RequestParam(name = "premium_type",required = true)String premium_type) throws IOException {
		
		JSONObject retJson = cmStandBookService.add(channel,company,issu_time,issu_acct_no,auther,insure_name,phone1,phone2,insure_idno,car_owner_idno,
				Insured_name,Insured_idno,car_no,engine,vin,brand,reg_date,turn_renew,policys,acct_name,acct_amt,bank_code,card_no,send_auth,premium_type);
		
		return retJson.toJSONString();
	}
	
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
	@RequestMapping(path = "/crm/stand_book/query_for_salesman")
	@ResponseBody
	public String queryForSalesman(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "car_no",required = false)String car_no,
			@RequestParam(name = "auth_status",required = false)String auth_status) {
		JSONObject retJson = cmStandBookService.queryForSalesman(company, channel, under_write_date_s, under_write_date_e, insure_name,phone1,car_no,auth_status);
		return retJson.toJSONString();
	}
	
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
	@RequestMapping(path = "/crm/stand_book/query_for_leader")
	@ResponseBody
	public String queryForLeader(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "car_no",required = false)String car_no) {
		JSONObject retJson = cmStandBookService.queryForLeader(company, channel,user_id,under_write_date_s, under_write_date_e, insure_name,phone1,car_no);
		return retJson.toJSONString();
	}
	
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
	@RequestMapping(path = "/crm/stand_book/query_for_manager")
	@ResponseBody
	public String queryForManager(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "car_no",required = false)String car_no) {
		JSONObject retJson = cmStandBookService.queryForManager(company, channel,user_id, under_write_date_s, under_write_date_e, insure_name,phone1,car_no);
		return retJson.toJSONString();
	}
	
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
	@RequestMapping(path = "/crm/stand_book/query_by_policy")
	@ResponseBody
	public String queryByPolicy(@RequestParam(name = "policy",required = true)String policy) {
		JSONObject retJson = cmStandBookService.queryByPolicy(policy);
		return retJson.toJSONString();
	}
	
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
	@RequestMapping(path = "/crm/stand_book/query_for_salesman_modify")
	@ResponseBody
	public String queryForSalesmanModify(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "car_no",required = false)String car_no){

		JSONObject retJson = cmStandBookService.queryForSalesmanModify(company,channel,under_write_date_s, under_write_date_e,insure_name,phone1,car_no);
		return retJson.toJSONString();
		
	}
	
	
	/**
	 *	台账修改 业务员修改未提交审核的台账信息,接口取消了
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
	@RequestMapping(path = "/crm/stand_book/modify_unauth_stand_book")
	@ResponseBody
	public String modifyUnAuthStandBook(@RequestParam(name = "tzid",required = true)String tzid,
			@RequestParam(name = "policy",required = false)String policy,
			@RequestParam(name = "under_write_date",required = false)String under_write_date,
			@RequestParam(name = "kind",required = false)String kind,
			@RequestParam(name = "premium",required = false)String premium,
			@RequestParam(name = "tax",required = false)String tax,
			@RequestParam(name = "sdate",required = false)String sdate,
			@RequestParam(name = "edate",required = false)String edate,
			@RequestParam(name = "discount",required = false)String discount,
			@RequestParam(name = "discount_amt",required = false)String discount_amt,
			@RequestParam(name = "jy_amt",required = false)String jy_amt,
			@RequestParam(name = "turn_renew",required = false)String turn_renew,
			@RequestParam(name = "car_no",required = false)String car_no,
			@RequestParam(name = "engine",required = false)String engine,
			@RequestParam(name = "vin",required = false)String vin,
			@RequestParam(name = "brand",required = false)String brand,
			@RequestParam(name = "reg_date",required = false)String reg_date,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "phone2",required = false)String phone2,
			@RequestParam(name = "insure_idno",required = false)String insure_idno,
			@RequestParam(name = "car_owner_idno",required = false)String car_owner_idno,
			@RequestParam(name = "car_owner",required = false)String car_owner,
			@RequestParam(name = "Insured_name",required = false)String Insured_name,
			@RequestParam(name = "acct_name",required = false)String acct_name,
			@RequestParam(name = "acct_amt",required = false)String acct_amt,
			@RequestParam(name = "bank_code",required = false)String bank_code,
			@RequestParam(name = "card_no",required = false)String card_no,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "issu_time",required = false)String issu_time,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "issu_acct_no",required = false)String issu_acct_no,
			@RequestParam(name = "auth_status",required = false)String auth_status) {
		JSONObject retJson = cmStandBookService.modifyUnAuthStandBook(tzid, policy,under_write_date, kind, premium, tax,sdate,edate,discount,discount_amt,jy_amt,turn_renew,
				car_no,engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,channel,company,
				issu_time,user_id,issu_acct_no);
		return retJson.toJSONString();
		
	}
	
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
	@RequestMapping(path = "/crm/stand_book/stand_book_modify")
	@ResponseBody
	public String standBookModify(@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "issu_time",required = false)String issu_time,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "issu_acct_no",required = false)String issu_acct_no,
			@RequestParam(name = "insure_name",required = false)String insure_name,
			@RequestParam(name = "phone1",required = false)String phone1,
			@RequestParam(name = "phone2",required = false)String phone2,
			@RequestParam(name = "insure_idno",required = false)String insure_idno,
			@RequestParam(name = "insured_name",required = false)String insured_name,
			@RequestParam(name = "insured_idno",required = false)String insured_idno,
			@RequestParam(name = "car_owner",required = false)String car_owner,
			@RequestParam(name = "car_owner_idno",required = false)String car_owner_idno,
			@RequestParam(name = "car_no",required = false)String car_no,
			@RequestParam(name = "engine",required = false)String engine,
			@RequestParam(name = "vin",required = false)String vin,
			@RequestParam(name = "brand",required = false)String brand,
			@RequestParam(name = "reg_date",required = false)String reg_date,
			@RequestParam(name = "acct_name",required = false)String acct_name,
			@RequestParam(name = "acct_amt",required = false)String acct_amt,
			@RequestParam(name = "bank_code",required = false)String bank_code,
			@RequestParam(name = "card_no",required = false)String card_no,
			@RequestParam(name = "policys",required = true)String policys){
		JSONObject retJson = cmStandBookService.standBookModify(channel,company, issu_time, user_id, issu_acct_no,insure_name,phone1,phone2,insure_idno,insured_name,
				insured_idno,car_owner,car_owner_idno,car_no,engine,vin,brand,reg_date,acct_name,acct_amt,bank_code,card_no,policys);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	台账修改 业务员删除未提交审核或者审核失败的台账  只能删除自己的台账
	 * @param tzid 台账编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/stand_book/stand_book_delete")
	@ResponseBody
	public String standBookDelete(@RequestParam(name = "tzid",required = true)String[] tzid){

		JSONObject retJson = cmStandBookService.standBookDelete(tzid);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	发起台账申请 业务员发起审核，对同一车辆的所有保单发起审核
	 * @param tzid 台账编码
	 * @param car_no 车牌号
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/stand_book/stand_book_send_auth")
	@ResponseBody
	public String standBookSendAuth(@RequestParam(name = "tzid",required = true)String[] tzid,
			@RequestParam(name = "car_no",required = true)String car_no){

		JSONObject retJson = cmStandBookService.standBookSendAuth(tzid,car_no);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	业务经理审核 对同一辆车的所有保单发起审核  只能审核 send审批中状态的数据  同一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	审核状态auth_succ初审成功  auth_fail初审失败
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/stand_book/stand_book_leader_auth")
	@ResponseBody
	public String standBookLeaderAuth(@RequestParam(name = "tzid",required = true)String[] tzid,
			@RequestParam(name = "auth_status",required = true)String auth_status,
			@RequestParam(name = "auth_remark",required = false)String auth_remark){

		JSONObject retJson = cmStandBookService.standBookLeaderAuth(tzid,auth_status,auth_remark);
		return retJson.toJSONString();
		
	}
	
	/**
	 *	总经理审核审核 对同一辆车的所有保单发起审核  只能审核 auth_succ初审成功状态的数据  统一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	审核状态auth_succ初审成功  auth_fail初审失败
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/stand_book/stand_book_manager_auth")
	@ResponseBody
	public String standBookManagerAuth(@RequestParam(name = "tzid",required = true)String[] tzid,
			@RequestParam(name = "auth_status",required = true)String auth_status,
			@RequestParam(name = "auth_remark",required = false)String auth_remark){

		JSONObject retJson = cmStandBookService.standBookManagerAuth(tzid,auth_status,auth_remark);
		return retJson.toJSONString();
		
	}
	
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
	 * @return {retCode,retMsg list，tzid，channel，company，issu_time，issu_acct_no，auther，insure_name，phone1，phone2
		，insure_idno，car_owner_idno，Insured_name，Insured_idno，car_no，engine，vin，brand，reg_date，turn_renew，policy，
		under_write_date，kind，premium，tax，sdate，edate，discount_amt，discount，jy_amt，acct_name，acct_amt，bank_code，
		card_no，auth_status，user_id，total_premium，count}
	 */
	@RequestMapping(path = "/crm/stand_book/query_for_all")
	@ResponseBody
	public String queryForAll(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "kind",required = false)String kind,
			@RequestParam(name = "auth_status",required = false)String auth_status,
			@RequestParam(name = "page_num",required = false)String page_num,
			@RequestParam(name = "page_count",required = false)String page_count,
			@RequestParam(name = "user_id",required = false)String user_id){

		JSONObject retJson = cmStandBookService.queryForAll(company,channel,under_write_date_s,under_write_date_e,kind,auth_status,page_num,page_count,user_id);
		return retJson.toJSONString();
	}
	
}
