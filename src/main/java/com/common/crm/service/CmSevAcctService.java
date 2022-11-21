package com.common.crm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmSevAcctInfoDao;
@Service
public class CmSevAcctService {
	
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private CmSevAcctInfoDao cmSevAcctInfoDao;
	
	/**
	 * 	添加账号信息
	 * @param login_name	登录名
	 * @param login_pwd	
	 * @param login_url
	 * @param acct_desc
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id
	 * @param phone
	 * @param email
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject add_sev_acct(String login_name,String login_pwd,String login_url,String acct_desc,
			String server_sup,String auth_type,String auth_set,String user_id,String phone,String email,String remark) {
		
		JSONObject retJson = new JSONObject();
		HashMap acctMap = cmSevAcctInfoDao.queryLock(null, login_name);
		if(acctMap != null && !acctMap.isEmpty()) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "该账号已经登记在册");
			return retJson;
		}
		String acct_id = sequenceService.getNewSequence("sev_acct_id");
		cmSevAcctInfoDao.add(acct_id, login_name, login_pwd, login_url, acct_desc, server_sup, auth_type, auth_set, user_id, phone, email, "normal", remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	/**
	 * 	查账号信息
	 * @param acct_id	
	 * @param login_name
	 * @param server_sup
	 * @param user_id
	 * @return {retCode,retMsg,list[{acct_id,login_name,login_pwd,login_url,acct_desc,server_sup,auth_type,auth_set,user_id,name,phone,email,status,remark}]}
	 */
	public JSONObject query_sev_acct(String acct_id,String login_name,String server_sup,String user_id,String status) {
		JSONObject retJson = new JSONObject();
		List<HashMap> acctList = cmSevAcctInfoDao.listAccts(acct_id, login_name, server_sup, user_id,status);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", acctList);
		return retJson;
	}
	/**
	 * 	 注销
	 * @param acct_id
	 * @return {retCode,retMsg}
	 */
	public JSONObject cancel(String acct_id) {
		JSONObject retJson = new JSONObject();
		cmSevAcctInfoDao.update(acct_id, null, null, null, null, null, null, null, null, null, null, "cancel", null);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	/**
	 *  更新
	 * @param acct_id
	 * @param login_name	登录名
	 * @param login_pwd	
	 * @param login_url
	 * @param acct_desc
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id
	 * @param phone
	 * @param email
	 * @param status 状态  normal正常 cancel注销
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	public JSONObject update(String acct_id,String login_name,String login_pwd,String login_url,String acct_desc,
			String server_sup,String auth_type,String auth_set,String user_id,String phone,String email,String status,String remark) {
		
		JSONObject retJson = new JSONObject();
		cmSevAcctInfoDao.update(acct_id, login_name, login_pwd, login_url, acct_desc, server_sup, auth_type, auth_set, user_id, phone, email, status, remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
}
