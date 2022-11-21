package com.common.crm.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CuAcctChangeInfoDao;
import com.common.crm.dao.CuAcctInfoDao;
import com.common.crm.dao.CuConsumeInfoDao;
import com.common.crm.dao.CuCusInfoDao;
import com.common.crm.dao.CuTelOilFilterParamDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;
import com.common.crm.util.HttpUtil;

@Service
public class CuCusService {
	@Autowired
	private CuCusInfoDao cuCusInfoDao;
	@Autowired
	private CuAcctInfoDao cuAcctInfoDao;
	@Autowired
	private CuConsumeInfoDao cuConsumeInfoDao;
	@Autowired
	private CuAcctChangeInfoDao acctChangeInfoDao;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CuTelOilFilterParamDao cuTelOilFilterParamDao;
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 	查询客户信息
	 * @param phone 手机号
	 * @return {retCode,retMsg,phone,ctime,last_login_time,status,cus_type,nickname,remark,balance,deleteList:[{retCode,retMsg,phone,ctime,last_login_time,status,cus_type,nickname,remark}]}
	 */
	public JSONObject cusInfo(String phone) {
		JSONObject retJson = new JSONObject();
		HashMap cusMap = cuCusInfoDao.getByPhone(phone);
		if(cusMap == null || cusMap.isEmpty()) {
			retJson.put("retCode","error");
			retJson.put("retMsg","查无客户信息");
			return retJson;
		}
		JSONObject ss = sessionManager.getSession();
		retJson.putAll(cusMap);
		String cusid = cusMap.get("cusid").toString();
		BigDecimal balance = cuAcctInfoDao.balance(cusid);
		retJson.put("balance",balance);
		Timestamp ctime = (Timestamp)cusMap.get("ctime");
		Timestamp last_login_time = (Timestamp)cusMap.get("last_login_time");
		String nickname = cusMap.get("nickname").toString();
		nickname = new String(Base64.getDecoder().decode(nickname));
		retJson.put("ctime",DateTimeUtil.date2Str(ctime.getTime(), "yyyy-MM-dd hh:mm:ss"));
		retJson.put("last_login_time",DateTimeUtil.date2Str(last_login_time.getTime(), "yyyy-MM-dd hh:mm:ss"));
		retJson.put("nickname",nickname);
		
		List<HashMap> deleteList = cuCusInfoDao.list(null, null, phone, "delete");
		if(deleteList != null || !deleteList.isEmpty()) {
			for (HashMap hashMap : deleteList) {
				Timestamp del_ctime = (Timestamp)hashMap.get("last_login_time");
				Timestamp del_last_login_time = (Timestamp)hashMap.get("last_login_time");
				String del_nickname = hashMap.get("nickname").toString();
				del_nickname = new String(Base64.getDecoder().decode(del_nickname));
				hashMap.put("ctime",DateTimeUtil.date2Str(del_ctime.getTime(), "yyyy-MM-dd hh:mm:ss"));
				hashMap.put("last_login_time",DateTimeUtil.date2Str(del_last_login_time.getTime(), "yyyy-MM-dd hh:mm:ss"));
				hashMap.put("nickname",del_nickname);
				hashMap.remove("cusid");
				hashMap.remove("openid");
			}
			retJson.put("deleteList",deleteList);
		}
		
		retJson.remove("cusid");
		retJson.remove("openid");
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	
	/**
	 * 	查询客户交易明细信息
	 * @param phone 手机号
	 * @return {retCode,retMsg,list:[{payno,amt,pay_type,tran_type,tran_name,ctime,utime,status,rech_no,status_msg,cus_name,bank_name}]}
	 */
	public JSONObject cusOrderDetailList(String phone) {
		JSONObject retJson = new JSONObject();
		HashMap cusMap = cuCusInfoDao.getByPhone(phone);
		if(cusMap == null || cusMap.isEmpty()) {
			retJson.put("retCode","error");
			retJson.put("retMsg","查无客户信息");
			return retJson;
		}
		String cusid = cusMap.get("cusid").toString();
		List<HashMap> list = cuConsumeInfoDao.orderDetailList(cusid);
		for (HashMap hashMap : list) {
			Timestamp ctime = (Timestamp)hashMap.get("ctime");
			hashMap.put("ctime",DateTimeUtil.date2Str(ctime.getTime(), "yyyy-MM-dd hh:mm:ss"));
			if(hashMap.get("utime") != null) {
				Timestamp utime = (Timestamp)hashMap.get("utime");
				hashMap.put("utime",DateTimeUtil.date2Str(utime.getTime(), "yyyy-MM-dd hh:mm:ss"));
			}
		}
		
		retJson.put("list",list);
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	
	/**
	 * 	查询客户账户变动明细
	 * @param phone
	 * @return {retCode,retMsg,list:[{change_id,acctno,cusid,action_type,action_name,payno,amt,bf_balance,af_balance,ctime,remark}]}
	 */
	public JSONObject cusAcctDetailList(String phone) {
		JSONObject retJson = new JSONObject();
		HashMap cusMap = cuCusInfoDao.getByPhone(phone);
		if(cusMap == null || cusMap.isEmpty()) {
			retJson.put("retCode","error");
			retJson.put("retMsg","查无客户信息");
			return retJson;
		}
		String cusid = cusMap.get("cusid").toString();
		List<HashMap> list = acctChangeInfoDao.list(null, null, cusid, null, null);
		for (HashMap hashMap : list) {
			Timestamp ctime = (Timestamp)hashMap.get("ctime");
			hashMap.put("ctime",DateTimeUtil.date2Str(ctime.getTime(), "yyyy-MM-dd hh:mm:ss"));
			hashMap.remove("cusid");
		}
		retJson.put("list",list);
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	
	/**
	 * 	查询客户的拦截订单
	 * @return {retCode,retMsg,list:[{payno,cusid,pay_type,rech_type,rech_no,amt,status,ext,remark}]}
	 */
	public JSONObject file_order_query() {
		JSONObject retJson = new JSONObject();
		List<HashMap> list =  cuTelOilFilterParamDao.list(null, null, null, "init");
		if(list != null) {
			for (HashMap hashMap : list) {
				if("tel".equals( hashMap.get("rech_type"))) {
					hashMap.put("tran_name","语音服务");
				}else {
					hashMap.put("tran_name","送油服务");
				}
			}
		}
		retJson.put("list",list);
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	
	
	/**
	 * 	处理拦截订单
	 * @param paynos 流水号
	 * @return {retCode,retMsg}
	 */
	public JSONObject file_order_deal(String[] paynos) {
		JSONObject retJson = new JSONObject();
		String filter_deal_url = cacheService.getCacheParamValue("filter_deal_url");
		for (String payno : paynos) {
			HashMap params = new HashMap();
			params.put("payno",payno);
			HttpUtil.httpPost(filter_deal_url, params);
		}
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	
	
}
