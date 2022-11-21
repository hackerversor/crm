package com.common.crm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmUserInfoDao;
import com.common.crm.dao.CmUserIssueAcctInfoDao;
import com.common.crm.dao.CmUserLoginInfoDao;
import com.common.crm.support.SessionManager;

@Service
public class PersonService {
	
	@Autowired
	private CmUserLoginInfoDao cmUserLoginInfoDao;
	@Autowired
	private CmUserInfoDao cmUserInfoDao;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CmUserIssueAcctInfoDao cmUserIssueAcctInfoDao;
	
	/**
	 * 	用户基本信息查询
	 * @return {retCode,retMsg,user_id,name,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark,login_name}
	 */
	public JSONObject userBaseInfo() {
		JSONObject retJson = new JSONObject();
		String user_id = sessionManager.getSession().getString("user_id");
		HashMap userMap = cmUserInfoDao.queryOne(user_id);
		retJson.putAll(userMap);
		HashMap loginMap = cmUserLoginInfoDao.getLongInfo(null, user_id);
		retJson.put("login_name", loginMap.get("login_name"));
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	查询出单账号
	 * @return {retCode,retMsg,list:[{company,company_name,issue_acct,user_id}]}
	 */
	public JSONObject issue_acct_query(String company) {
		JSONObject retJson = new JSONObject();
		String user_id = sessionManager.getSession().getString("user_id");
		List<HashMap> acctList = cmUserIssueAcctInfoDao.queryByUser(user_id,company);
		retJson.put("list", acctList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 *  	删除账号
	 * @param issue_acct 账号
	 * @return {retCode,retMsg}
	 */
	
	public JSONObject issue_acct_delete(String issue_acct) {
		JSONObject retJson = new JSONObject();
		String user_id = sessionManager.getSession().getString("user_id");
		cmUserIssueAcctInfoDao.delete(user_id, issue_acct);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	无则插入 有则更新
	 * @param issue_acct 
	 * @param company
	 * @param company_name
	 * @return {retCode,retMsg}
	 */
	public JSONObject issue_acct_add(String company,String company_name,String issue_acct) {
		JSONObject retJson = new JSONObject();
		String user_id = sessionManager.getSession().getString("user_id");
		cmUserIssueAcctInfoDao.replace(company, company_name,issue_acct,user_id );
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	
}
