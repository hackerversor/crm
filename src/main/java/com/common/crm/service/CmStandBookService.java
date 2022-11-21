package com.common.crm.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmRateInnerInfoDao;
import com.common.crm.dao.CmStandBookDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;
import com.common.crm.util.MatchUtil;
import com.common.crm.util.PublicUtil;

@Service
public class CmStandBookService {
	@Autowired
	private CmStandBookDao cmStandBookDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private CmUserService cmUserService;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CmRateInnerInfoDao cmRateInnerInfoDao;
	@Autowired
	private StatService statService;
	
	/**
	 *	添加台账
	 * @param tzid	编码
	 * @param policys	保单信息
		 * @param policy	保单号
		 * @param under_write_date	承保日期
		 * @param kind	险种
		 * @param premium 保费
		 * @param rate  费率
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param recrod_status 记录状态 used已使用 unused未使用
	 * @param cusid 客户编码
	 * @param ctime 添加时间
	 * @param act_time 激活时间 
	 * @return {retCode,retMsg}
	 * @throws IOException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject add (String channel,String company,String issu_time,String issu_acct_no,String auther,String insure_name,String phone1,String phone2,
			String insure_idno,String car_owner_idno,String Insured_name,String Insured_idno,String car_no,String engine,String vin,String brand,String reg_date,
			String turn_renew,String policys,String acct_name,String acct_amt,String bank_code,String card_no,String send_auth,String premium_type) throws IOException  {
		
		String user_id=(String) sessionManager.getSession().get("user_id");
		String auth_status="init";
		Timestamp ctime = new Timestamp(System.currentTimeMillis());
		
		StringBuffer strbuf=new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object> ();
		JSONObject retJson =  new JSONObject();
		//1.把字符串类型的json数组对象转化JSONArray
		JSONArray json=JSONArray.parseArray(policys);
		//2、policys是完整保单休息，包含以下内容
		for(int i=0;i<json.size();i++){
			JSONObject job = json.getJSONObject(i);
			String tzid = sequenceService.getNewSequence("tzid");
			String policy=job.getString("policy"); 
			if(policy.length() !=20 ) {
				retJson.put("retCode", "length_error");
				retJson.put("retMsg", "保单号长度需等于20位数字！");
				return retJson;
			}
			if(!MatchUtil.isInteger(policy)) {
				retJson.put("retCode", "num_error");
				retJson.put("retMsg", "保单号必须是数字！");
				return retJson;
			}
			List<HashMap> db_policyList=cmStandBookDao.queryByPolicy(policy);
			for(HashMap db_policys:db_policyList) {
				String db_policy=db_policys.get("policy").toString();
				if(policy.equals(db_policy)) {
					retJson.put("retCode", "policy_error");
					retJson.put("retMsg", "保单号"+policy+"重复，请核实后重新添加！");
					return retJson;
				}
			}
			
			
			Date uwdate=job.getSqlDate("under_write_date");
			String under_write_date= DateTimeUtil.date2Str(uwdate.getTime(), "yyyy-MM-dd");
			
			String kind=job.getString("kind");
			Double premium=job.getDouble("premium");
			Double tax=job.getDouble("tax");
			
			Date sdateStr=job.getSqlDate("sdate");
			String sdate= DateTimeUtil.date2Str(sdateStr.getTime(), "yyyy-MM-dd");
			
			Date edateStr=job.getSqlDate("edate");
			String edate= DateTimeUtil.date2Str(edateStr.getTime(), "yyyy-MM-dd");
			
			Double discount=job.getDouble("discount");
			//打折比例显示为整数，也就是百分数格式
			//打折比例=打折金额/保费
			Double discount_amt=job.getDouble("discount_amt");
			Double jy_amt=job.getDouble("jy_amt");
			Double cul_jy_amt=null;
			String rate =null;
			List<HashMap> rateInfoList=cmRateInnerInfoDao.query(company,channel,kind,under_write_date);
			if(rateInfoList!=null && rateInfoList.size()> 0) {
//				for (HashMap innerMap  : rateInfoList) {
//					Double db_rate=Double.parseDouble(innerMap.get("rate").toString());
//				for (int m=0;m < rateInfoList.size();  m++) {
					Double db_rate=Double.parseDouble(rateInfoList.get(0).get("rate").toString());
					rate =PublicUtil.doubleToStr(db_rate,2);
					//结余金额=保费*费率/100-打折金额
					cul_jy_amt=premium * db_rate / 100 - discount_amt;
//					cul_jy_amt=MatchUtil.formatDouble2(cul_jy_amt,2);
//				}
				map.put("保单号",policy);
				map.put("计算后金额",cul_jy_amt);
				strbuf=strbuf.append(map);
				if(cul_jy_amt.compareTo(jy_amt) != 0) {
					retJson.put("retCode", "amt_error");
					retJson.put("retMsg", "保存失败"+strbuf.toString());
//					retJson.put("list", strbuf.toString());
				} else {
					
					Double acctamt ;
					if(acct_amt!=null && !acct_amt.equals("")) {
						acctamt=Double.parseDouble(acct_amt);
					}else {
						acctamt = null;
					}
					
//					if(send_auth.equals("true")) {
//						auth_status="send";
//						cmStandBookDao.add(tzid,channel,company,policy,issu_time,issu_acct_no,insure_name,phone1,phone2,insure_idno,car_owner_idno,
//								Insured_name,Insured_idno,under_write_date,car_no,engine,vin,brand,reg_date,turn_renew,kind,premium,rate,tax,sdate,edate,
//								discount_amt,discount,jy_amt,acct_name,acctamt,bank_code,card_no,user_id,auth_status,"unused",ctime);
//							cmStandBookDao.addStandBookAuth(tzid, auth_status, user_id, ctime, auther, null, null, null, null, ctime);
//					}else {
					cmStandBookDao.add(tzid,channel,company,policy,issu_time,issu_acct_no,insure_name,phone1,phone2,insure_idno,car_owner_idno,Insured_name,
						Insured_idno,under_write_date,car_no,engine,vin,brand,reg_date,turn_renew,kind,premium,rate,tax,sdate,edate,discount_amt,discount,
						jy_amt,acct_name,acctamt,bank_code,card_no,user_id,auth_status,premium_type,"unused",ctime);
					cmStandBookDao.addStandBookAuth(tzid, auth_status, user_id, ctime, auther, null, null, null, null, ctime);
//					}
					//websocket推送实时出单数据
					//推送消息  更新排名 更新榜单 同事成单通知 推送奖励通知 
//					if(!kind.equals("jqx")) {
//						statService.refreshAchievement(user_id, car_no);
//					}
					
					retJson.put("retCode", "success");
					retJson.put("retMsg", "成功");
				}
			}else {
				retJson.put("retCode", "null_error");
				retJson.put("retMsg", "未找到险种及相关配置费率信息！");
			}
		}
		//websocket推送实时出单数据
		//推送消息  更新排名 更新榜单 同事成单通知 推送奖励通知 
		try{
			statService.refreshAchievement(user_id, car_no);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return retJson;
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	public JSONObject queryForSalesman(String company,String channel,String under_write_date_s,String under_write_date_e,String insure_name,String phone1,
			String car_no,String auth_status ) {
		
		JSONObject retJson =  new JSONObject();
		String user_id=(String) sessionManager.getSession().get("user_id");

		List<HashMap> standBookList=cmStandBookDao.queryForSalesman(company,channel,under_write_date_s,under_write_date_e,insure_name,phone1,car_no,auth_status,user_id);
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", standBookList);
		return retJson;
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	public JSONObject queryForLeader(String company,String channel,String user_id,String under_write_date_s,String under_write_date_e,String insure_name,String phone1,
			String car_no) {
		
		JSONObject retJson =  new JSONObject();
		HashMap map = cmUserService.getLeaderOrManager("leader");
		
		String auth_status="send";
		String auth_user_id = "";
		if(map != null && !map.isEmpty()) {
			auth_user_id = map.get("user_id").toString();
		}
		
		List<HashMap> standBookList=cmStandBookDao.queryForLeader(company,channel,user_id,under_write_date_s,under_write_date_e,insure_name,phone1,car_no,auth_status,auth_user_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", standBookList);
		return retJson;
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no,auth_status,auther,cusid}
	 */
	public JSONObject queryForManager(String company,String channel,String user_id,String under_write_date_s,String under_write_date_e,String insure_name,String phone1,
			String car_no) {
		
		JSONObject retJson =  new JSONObject();
		String auth_status="auth_succ";
		
		HashMap map = cmUserService.getLeaderOrManager("manager");
		String auth_user_id = "";
		if(map != null && !map.isEmpty()) {
			auth_user_id = map.get("user_id").toString();
		}
		
		List<HashMap> standBookList=cmStandBookDao.queryForManager(company,channel,user_id,under_write_date_s,under_write_date_e,insure_name,phone1,car_no,auth_status,auth_user_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", standBookList);
		return retJson;
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,list:tzid,channel,company,issu_time,issu_acct_no,auther,insure_name,phone1,phone2,insure_idno,car_owner_idno,
	 * Insured_name,Insured_idno,car_no,engine,vin,brand,reg_date,turn_renew,policy,under_write_date,kind,premium,tax,sdate,edate,discount_amt,
	 * discount,jy_amt,acct_name,acct_amt,bank_code,card_no,auth_status,user_id}
	 */
	public JSONObject queryByPolicy(String policy) {
		
		JSONObject retJson =  new JSONObject();
		String send_user_id=(String) sessionManager.getSession().get("user_id");
		
		String intstatus="init";
		String failstatus="auth_fail";
		String fail="fail";
		String[] auth_status= {intstatus,failstatus,fail};
		
		List<HashMap> standBookList=new ArrayList();
		List<HashMap> carNoList=cmStandBookDao.queryByPolicy(policy);
		for(HashMap carNo:carNoList) {
			String car_no=carNo.get("car_no").toString();
			standBookList= cmStandBookDao.queryByCarNo(car_no,auth_status,send_user_id);
			
		}
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", standBookList);
		
		return retJson;
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
	
	public JSONObject queryForSalesmanModify(String company,String channel,String under_write_date_s,String under_write_date_e,String insure_name,
			String phone1,String car_no) {
		
		JSONObject retJson =  new JSONObject();
		String user_id=(String) sessionManager.getSession().get("user_id");
		
		String intstatus="init";
		String failstatus="auth_fail";
		String fail="fail";
		String[] auth_status= {intstatus,failstatus,fail};
		
		List<HashMap> standBookList=cmStandBookDao.queryForSalesmanModify(company,channel,under_write_date_s, under_write_date_e,insure_name,phone1,car_no,auth_status,user_id);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", standBookList);
		
		return retJson;
	}
	
	/**
	 *	台账修改  业务员修改未提交审核的台账信息,接口取消了
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
	 * @param auth_status init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
	 * @param auther 审核人
	 * @param cusid 客户编码
	 * @return {retCode,retMsg,policy,under_write_date,kind,premium,tax,sdate,edate,discount,discount_amt,issu_acct_no,jy_amt,turn_renew,car_no,
	 * engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,Insured_name,acct_name,acct_amt,bank_code,card_no,
	 * channel,company,issu_time,user_id,issu_acct_no}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject modifyUnAuthStandBook(String tzid,String policy,String under_write_date,String kind,String premium,String tax,String sdate,String edate,
			String discount,String discount_amt,String jy_amt,String turn_renew,String car_no,String engine,String vin,String brand,String reg_date,String insure_name,
			String phone1,String phone2,String insure_idno,String car_owner_idno,String car_owner,String Insured_name,String acct_name,String acct_amt,
			String bank_code,String card_no,String channel,String company,String issu_time,String user_id,String issu_acct_no) {
		
		JSONObject retJson =  new JSONObject();
		String auth_status="init";
		String db_user_id=(String) sessionManager.getSession().get("user_id");
		Timestamp ctime = new Timestamp(System.currentTimeMillis());
		
		String auther="";
		if(db_user_id.equals(user_id)) {
			cmStandBookDao.modifyUnAuthStandBook(tzid, user_id, ctime, auther, null, null, null, null, ctime, auth_status);
			
			cmStandBookDao.updateStandBook(tzid, policy,under_write_date, kind, premium, tax,sdate,edate,discount,discount_amt,jy_amt,turn_renew,
					car_no,engine,vin,brand,reg_date,insure_name,phone1,phone2,insure_idno,car_owner_idno,car_owner,Insured_name,acct_name,acct_amt,bank_code,
					card_no,channel,company,issu_time,user_id,issu_acct_no,auth_status);
			
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}else {
			retJson.put("retCode", "modify_error");
			retJson.put("retMsg", "失败");
			retJson.put("list", "传入经办人和正在使用的系统经办人不是同一人！");
		}
		
		return retJson;
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
	@Transactional(rollbackFor = Exception.class)
	public JSONObject standBookModify(String channel,String company,String issu_time,String user_id,String issu_acct_no,String insure_name,String phone1,
			String phone2,String insure_idno,String insured_name,String insured_idno,String car_owner,String car_owner_idno,String car_no,String engine,
			String vin,String brand,String reg_date,String acct_name,String acct_amt,String bank_code,String card_no,String policys) {
		
		JSONObject retJson =  new JSONObject();
		StringBuffer strbuf=new StringBuffer();
		Map<String,Object> map=new HashMap<String,Object> ();
		//1.把字符串类型的json数组对象转化JSONArray
		JSONArray json=JSONArray.parseArray(policys);
		//2、policys是完整保单休息，包含以下内容
		for(int i=0;i<json.size();i++){
			JSONObject job = json.getJSONObject(i);
			String tzid=job.getString("tzid");
			String policy=job.getString("policy");
			if(policy.length() !=20 ) {
				retJson.put("retCode", "length_error");
				retJson.put("retMsg", "保单号长度需等于20位数字！");
				return retJson;
			}
			if(!MatchUtil.isInteger(policy)) {
				retJson.put("retCode", "num_error");
				retJson.put("retMsg", "保单号必须是数字！");
				return retJson;
			}
			
			Date uwdate=job.getSqlDate("under_write_date");
			String under_write_date= DateTimeUtil.date2Str(uwdate.getTime(), "yyyy-MM-dd");
			
			String kind=job.getString("kind");
			Double premium=job.getDouble("premium");
			Double tax=job.getDouble("tax");
			
			Date sdateStr=job.getSqlDate("sdate");
			String sdate= DateTimeUtil.date2Str(sdateStr.getTime(), "yyyy-MM-dd");
			
			Date edateStr=job.getSqlDate("edate");
			String edate= DateTimeUtil.date2Str(edateStr.getTime(), "yyyy-MM-dd");
			
			String turn_renew=job.getString("turn_renew");
			
			Double discount=job.getDouble("discount");
			//打折比例显示为整数，也就是百分数格式
			//打折比例=打折金额/保费
			Double discount_amt=job.getDouble("discount_amt");
			Double jy_amt=job.getDouble("jy_amt");
			Double cul_jy_amt=null;
			List<HashMap> rateInfoList=cmRateInnerInfoDao.query(company,channel,kind,under_write_date);
			if(rateInfoList!=null && rateInfoList.size()> 0) {
				Double db_rate=Double.parseDouble(rateInfoList.get(0).get("rate").toString());
				//结余金额=保费*费率/100-打折金额
				cul_jy_amt=premium * db_rate / 100 - discount_amt;
//				cul_jy_amt=MatchUtil.formatDouble2(cul_jy_amt,2);
				map.put("保单号",policy);
				map.put("计算后金额",cul_jy_amt);
				strbuf=strbuf.append(map);
				if(cul_jy_amt.compareTo(jy_amt) != 0) {
					retJson.put("retCode", "amt_error");
					retJson.put("retMsg", "保存失败"+strbuf.toString());
				} else {
					Double acctamt ;
					if(acct_amt!=null && !acct_amt.equals("")) {
						acctamt=Double.parseDouble(acct_amt);
					}else {
						acctamt = null;
					}
		
					cmStandBookDao.standBookModify(tzid, policy,channel,company, issu_time, user_id, issu_acct_no,insure_name,phone1,phone2,insure_idno,insured_name,
							insured_idno,car_owner,car_owner_idno,car_no,engine,vin,brand,reg_date,acct_name,acctamt,bank_code,card_no,under_write_date,kind,
							premium,tax,sdate,edate,discount,discount_amt,jy_amt,turn_renew);
					
					retJson.put("retCode", "success");
					retJson.put("retMsg", "成功");
				}
			}else {
				retJson.put("retCode", "null_error");
				retJson.put("retMsg", "未找到险种及相关配置费率信息！");
			}
		}
		return retJson;
	}
	
	
	/**
	 *	台账修改 业务员删除未提交审核或者审核失败的台账  只能删除自己的台账
	 * @param tzid 台账编码
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject standBookDelete(String[] tzid) {
		JSONObject retJson =  new JSONObject();
		if(tzid.length > 0) {
			for(int i=0;i<tzid.length;i++) {
				cmStandBookDao.standBookDelete(tzid[i]);
			}
		}
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		
		return retJson;
	}
	
	/**
	 *	发起台账申请 业务员发起审核，对同一车辆的所有保单发起审核
	 * @param tzid 台账编码
	 * @param car_no 车牌号
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject standBookSendAuth(String[] tzid,String car_no) {
		
		JSONObject retJson =  new JSONObject();
		String send_user_id=(String) sessionManager.getSession().get("user_id");
		
		String auth_status="send";
		String intstatus="init";
		String failstatus="auth_fail";
		String fail="fail";
		String[] status= {intstatus,failstatus,fail};
		if(tzid.length > 0) {
			List<HashMap> standBookList= cmStandBookDao.queryByCarNo(car_no,status,send_user_id);
			for(int i=0;i<tzid.length;i++) {
				if(tzid.length == standBookList.size()) {
					cmStandBookDao.standBookSendAuth(tzid[i],car_no,send_user_id,auth_status);
					cmStandBookDao.standBookAuth(tzid[i],send_user_id,auth_status);
				}else {
					retJson.put("retCode", "car_no_error");
					retJson.put("retMsg", "传入车辆信息和实际存在车辆信息数量不一致，请确认后重试！");
					return retJson;
				}
			}
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		
		return retJson;
	}
	
	/**
	 *	业务经理审核(初审)       对同一辆车的所有保单发起审核  只能审核 send审批中状态的数据  同一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject standBookLeaderAuth(String[] tzid,String auth_status,String auth_remark){
		
		JSONObject retJson =  new JSONObject();
		Timestamp auth_time = new Timestamp(System.currentTimeMillis());
		//初审成功置审核人auth_user_id为终审审核人，初审失败置审核人auth_user_id为初审审核人
		HashMap manMap = cmUserService.getLeaderOrManager("manager");
		HashMap leamap = cmUserService.getLeaderOrManager("leader");
		String man_auth_user_id = "";
		String lea_auth_user_id = "";
		if(manMap != null && !manMap.isEmpty()) {
			man_auth_user_id = manMap.get("user_id").toString();
		}
		if(leamap != null && !leamap.isEmpty()) {
			lea_auth_user_id = leamap.get("user_id").toString();
		}
		
		List<HashMap> tzidList=new ArrayList();
		List<HashMap> carNoList= cmStandBookDao.queryByTzid(tzid,"send");
		for(HashMap db_car_no:carNoList) {
			String car_no=db_car_no.get("car_no").toString();
			tzidList= cmStandBookDao.queryByCar(car_no,"send");
		}
		if(tzid.length > 0 ) {
			for(int i=0;i<tzid.length;i++) {
				if(tzidList.size() == tzid.length) {
					if(auth_status.equals("auth_succ")) {
						cmStandBookDao.standBookLeaderAuth(tzid[i],auth_status);
						cmStandBookDao.standBookLdAuth(tzid[i],man_auth_user_id,auth_status,auth_remark,auth_time);
						
					}else {
						cmStandBookDao.standBookLeaderAuth(tzid[i],auth_status);
						cmStandBookDao.standBookLdAuth(tzid[i],lea_auth_user_id,auth_status,auth_remark,auth_time);
					}
				}else {
					retJson.put("retCode", "car_no_error");
					retJson.put("retMsg", "传入初审台账信息和实际存在台账信息数量不一致，请确认后重试！");
					return retJson;
				}
				
			}
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		
		return retJson;
		
	}
	
	/**
	 *	总经理审核审核(终审)         对同一辆车的所有保单发起审核  只能审核 auth_succ初审成功状态的数据  统一车辆的记录必须同时提交
	 * @param tzid 台账编码
	 * @param auth_status	init初始  send审批中  auth_succ初审成功  auth_fail初审失败  succ审批通过  fail审批失败 
     * @param auth_remark	审核状态    审批不通过的时候必输
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject standBookManagerAuth(String[] tzid,String auth_status,String auth_remark){
		
		JSONObject retJson =  new JSONObject();
		Timestamp last_time = new Timestamp(System.currentTimeMillis());
		//初审成功置审核人auth_user_id为终审审核人，初审失败置审核人auth_user_id为初审审核人
		HashMap manMap = cmUserService.getLeaderOrManager("manager");
		HashMap leamap = cmUserService.getLeaderOrManager("leader");
		String man_auth_user_id = "";
		String lea_auth_user_id = "";
		if(manMap != null && !manMap.isEmpty()) {
			man_auth_user_id = manMap.get("user_id").toString();
		}
		if(leamap != null && !leamap.isEmpty()) {
			lea_auth_user_id = leamap.get("user_id").toString();
		}
	
		List<HashMap> tzidList=new ArrayList();
		List<HashMap> carNoList= cmStandBookDao.queryByTzid(tzid,"auth_succ");
		for(HashMap db_car_no:carNoList) {
			String car_no=db_car_no.get("car_no").toString();
			tzidList= cmStandBookDao.queryByCar(car_no,"auth_succ");
		}
		
		
		if(tzid.length > 0) {
			for(int i=0;i<tzid.length;i++) {
				if(tzidList.size()==tzid.length) {
					if(auth_status.equals("succ")) {
						cmStandBookDao.standBookManagerAuth(tzid[i], auth_status);
						cmStandBookDao.standBookMgAuth(tzid[i],man_auth_user_id,auth_status,auth_remark,man_auth_user_id,last_time);
					}else {
						cmStandBookDao.standBookManagerAuth(tzid[i], auth_status);
						cmStandBookDao.standBookMgAuth(tzid[i],lea_auth_user_id,auth_status,auth_remark,lea_auth_user_id,last_time);
					}
				}else {
					retJson.put("retCode", "car_no_error");
					retJson.put("retMsg", "传入终审台账信息和实际存在台账信息数量不一致，请确认后重试！");
					return retJson;
				}
				
			}
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		
		return retJson;
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
		card_no，auth_status，user_id，total_premium，count
	 */
	public JSONObject queryForAll(String company,String channel,String under_write_date_s,String under_write_date_e,String kind,String auth_status,String page_num,String page_count,String user_id){
		
		JSONObject retJson =  new JSONObject();

		int ipage_num = page_num == null ? 0 : Integer.parseInt(page_num);
		if(page_num!=null) {
			if(Integer.parseInt(page_num) > 0){ 
				ipage_num = Integer.parseInt(page_num) -1;
			}
		}
		
		int ipage_count = page_count == null ? 30 : Integer.parseInt(page_count);
		Integer start_index = ipage_num * ipage_count ;
		
		List<HashMap> standBookList=cmStandBookDao.list(company,channel,under_write_date_s,under_write_date_e,null,kind,null,auth_status,start_index,ipage_count,user_id);
		Double total_premium = 0.0;
		Double total_discount_amt = 0.0;
		Double total_jy_amt = 0.0;
		Integer total_car_no=0;
		List<String> list =new ArrayList<String>();
		for (HashMap standBookMap : standBookList) {
			Double premium = standBookMap.get("premium") == null ? 0.0 : (Double)standBookMap.get("premium");
			Double discount_amt = standBookMap.get("discount_amt") == null ? 0.0 : (Double)standBookMap.get("discount_amt");
			Double jy_amt = standBookMap.get("jy_amt") == null ? 0.0 : (Double)standBookMap.get("jy_amt");
			String car_no = standBookMap.get("car_no").toString();
			list.add(car_no);
			
			total_premium += premium;
			total_discount_amt += discount_amt;
			total_jy_amt += jy_amt;
			total_premium=MatchUtil.formatDouble2(total_premium,2);
			total_discount_amt=MatchUtil.formatDouble2(total_discount_amt,2);
			total_jy_amt=MatchUtil.formatDouble2(total_jy_amt,2);
		}
		//车辆总数，去重
		total_car_no=removeDuplicate(list);
		//保单总数
		Integer total_policy=standBookList.size();
		Long total_count=cmStandBookDao.queryStatCount(company, channel, under_write_date_s, under_write_date_e, null, kind, null, auth_status, user_id);
		if(standBookList.size() > 0) {
			retJson.put("total_premium",total_premium);
			retJson.put("total_discount_amt",total_discount_amt);
			retJson.put("total_jy_amt",total_jy_amt);
			retJson.put("total_count",total_count);
			retJson.put("total_car_no",total_car_no);
			retJson.put("total_policy",total_policy);
			retJson.put("list", standBookList);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}else {
			retJson.put("retCode", "query_error");
			retJson.put("retMsg", "查无数据！");
		}
		
		return retJson;
		
	}
	//利用set集合自动去重的特性
	public Integer removeDuplicate(List<String> list){
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i));
		}
        return set.size();  
    }

}
