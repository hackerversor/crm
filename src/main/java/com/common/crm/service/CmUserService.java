package com.common.crm.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmTeamUserDao;
import com.common.crm.dao.CmUserInfoDao;
import com.common.crm.dao.CmUserIssueAcctInfoDao;
import com.common.crm.dao.CmUserLoginInfoDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;
import com.common.crm.util.EncryptUtil;
import com.common.crm.util.HanYuConvertPinYinUtil;
import com.common.crm.util.MatchUtil;

@Service
public class CmUserService {
	@Autowired
	private CmUserLoginInfoDao cmUserLoginInfoDao;
	@Autowired
	private CmUserInfoDao cmUserInfoDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CmUserIssueAcctInfoDao cmUserIssueAcctInfoDao;
	@Autowired
	private CmTeamUserDao cmTeamUserDao;
	
	
	/**
	 * 	登陆
	 * @param login_name 登陆名称
	 * @param login_pwd	登陆密码
	 * @return	{retCode,retMsg,user_id,name}
	 */
	public JSONObject cm_user_login(String login_name,String login_pwd) {
		JSONObject retJson = new JSONObject();
		//user_id,login_name,login_pwd,ctime,pwd_status,status,last_time,last_pwd,remark
		HashMap loginMap = cmUserLoginInfoDao.getLongInfo(login_name, null);
		if(loginMap == null || loginMap.isEmpty()) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "查无用户");
			return retJson;
		}
		//检查用户状态
		String status = loginMap.get("status").toString();
		if(!"normal".equals(status)) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "用户状态异常");
			return retJson;
		}
		String user_id = loginMap.get("user_id").toString();
		String pwd = EncryptUtil.md5(user_id + login_name + login_pwd);
		String db_login_pwd = loginMap.get("login_pwd").toString();
		if(!db_login_pwd .equals(pwd)) {//密码错误
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "密码错误");
			return retJson;
		}
		//检查密码状态
		String pwd_status = loginMap.get("pwd_status").toString();
		if("init".equals(pwd_status) || "reset".equals(pwd_status)) {
			retJson.put("retCode", "pwd_status_error");
			retJson.put("retMsg", "密码状态异常");
			return retJson;
		}
		
		{
			//创建session  存缓存
			HashMap userMap = cmUserInfoDao.queryOne(user_id);
			JSONObject userJson = new JSONObject();
			
			userJson.putAll(userMap);
			userJson.put("login_name",login_name);
			sessionManager.initSession(userJson);
			retJson.put("user_id", userMap.get("user_id"));
			retJson.put("login_name", login_name);
			retJson.put("user_no", userMap.get("user_no"));
			retJson.put("name", userMap.get("name"));
		}
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	
	public JSONObject logout() {
		JSONObject retJson = new JSONObject();
		sessionManager.delSession();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	
	/**
	 * 	修改密码
	 * @param login_name 登陆名
	 * @param old_login_pwd  旧密码 
	 * @param new_login_pwd 新密码
	 * @return {retCode,retMsg}
	 */
	public JSONObject pwd_modify(String login_name,String old_login_pwd,String new_login_pwd) {
		JSONObject retJson = new JSONObject();
		HashMap loginMap = cmUserLoginInfoDao.getLongInfo(login_name, null);
		if(loginMap == null || loginMap.isEmpty()) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "查无用户");
			return retJson;
		}
		//检查用户状态
		String status = loginMap.get("status").toString();
		if(!"normal".equals(status)) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "用户状态异常");
			return retJson;
		}
		String user_id = loginMap.get("user_id").toString();
		String pwd = EncryptUtil.md5(user_id + login_name + old_login_pwd);
		String db_login_pwd = loginMap.get("login_pwd").toString();
		if(!db_login_pwd .equals(pwd)) {//原密码错误
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "密码错误");
			return retJson;
		}else {//原密码校验通过  更新密码 更新密码状态 修改时间 上次密码
			String new_pwd = EncryptUtil.md5(user_id + login_name + new_login_pwd);
			cmUserLoginInfoDao.update(user_id, null, new_pwd, "normal", null, DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), pwd, null);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
			return retJson;
		}
	}
	
	
	
	/**
	 * 	创建用户信息
	 * @param name 姓名
	 * @param user_no 用户编号
	 * @param sex 性别 M男 W女
	 * @param id_no 身份证号 18位二代身份证
	 * @param email 邮箱
	 * @param phone 手机号 11位手机号
	 * @param remark 备注
	 * @param user_no 
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject create(String name, String user_no,String sex,String id_no,String email, String phone,String remark) {
		JSONObject retJson = new JSONObject();
		if(!MatchUtil.isHanzi(name)) {
			retJson.put("retCode", "error");
			retJson.put("retMsg", "姓名格式错误，姓名只能是汉字");
			return retJson;
		}
		//创建登录名  如果已经存在 则在后边加一  以此类推
		String login_name = createLoginName(name);
		int i = 0;
		for (;;) {
			String tmp_name = login_name;
			if(i != 0) {
				tmp_name = tmp_name + i;
			}
			HashMap lognMap = cmUserLoginInfoDao.getLongInfoLock(tmp_name, null);
			if(lognMap == null || lognMap.isEmpty()) {
				login_name = tmp_name;
				break;
			}
			i++;
		}
		String user_id = sequenceService.getNewSequence("cm_user_id");
		String birthday = id_no.substring(8,14);
		Timestamp ctime = new Timestamp(System.currentTimeMillis());
		Timestamp hiredate = ctime;
		
		HashMap cmUserMap = cmUserInfoDao.queryByUserNo(user_no);
		if(cmUserMap == null || cmUserMap.isEmpty()) {	
			cmUserInfoDao.add(user_id, name, user_no, sex, birthday, id_no, hiredate, email,phone, null, ctime, "normal", remark);
			String login_pwd = EncryptUtil.md5(user_id + login_name + login_name+"123");
			cmUserLoginInfoDao.add(user_id, login_name, login_pwd, ctime, "init", "normal", null, null, remark);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
			retJson.put("login_name", login_name);
			retJson.put("login_pwd", login_name+"123");
			return retJson;
			
		}else {
			retJson.put("retCode", "error");
			retJson.put("retMsg", "用户编号重复，请更换！");
			return retJson;
		}
	}
	
	/**
	 * 	汉字转拼音
	 * @param name 汉字姓名
	 * @return  全拼
	 */
	public String createLoginName(String name) {
		StringBuffer sb = new StringBuffer();
		char[] cc = name.toCharArray();
		boolean isFirst = true;
		for (int i = 0; i < cc.length; i++) {
			String pinyin = HanYuConvertPinYinUtil.hanyu2Pinyin(cc[i]);
			if(isFirst) {
				sb.append(pinyin);
				isFirst = false;
				continue;
			}
			if(cc.length > 2) {//如果名字的数量超过2个字 则取首字母
				char c = pinyin.charAt(0);
				sb.append(c);
			}else {
				sb.append(pinyin);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 	查询用户列表
	 * @param user_id
	 * @param name
	 * @param user_no 用户编号
	 * @param status
	 * @param have_team 是否已经加入team true是  false否
	 * @return {retCode,retMsg,list:[{user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}]}
	 */
	public JSONObject listUsers(String user_id,String name,String status,String have_team){
		JSONObject retJson = new JSONObject();
		List<HashMap> userList = cmUserInfoDao.listUsers(user_id, name, status,have_team);
		//user_id,name,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark
		for (HashMap userMap : userList) {
			Object birthday = userMap.get("birthday");
			Object hiredate = userMap.get("hiredate");
			Object leavedate = userMap.get("leavedate");
			Object ctime = userMap.get("ctime");
			if(birthday != null) {
				Date birthday_tsp = (Date)birthday;
				userMap.put("birthday", DateTimeUtil.date2Str(birthday_tsp.getTime(), "yyyy-MM-dd"));
			}
			if(hiredate != null) {
				Date hiredate_tsp = (Date)hiredate;
				userMap.put("hiredate", DateTimeUtil.date2Str(hiredate_tsp.getTime(), "yyyy-MM-dd"));
			}
			if(leavedate != null) {
				Date leavedate_tsp = (Date)leavedate;
				userMap.put("leavedate", DateTimeUtil.date2Str(leavedate_tsp.getTime(), "yyyy-MM-dd"));
			}
			if(ctime != null) {
				Timestamp ctime_tsp = (Timestamp)ctime;
				userMap.put("ctime", DateTimeUtil.date2Str(ctime_tsp.getTime(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list",userList);
		return retJson;
	}
	
	/**
	 * 	离职/注销用户  登录信息也需要注销
	 * @param user_id 用户编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject leave(String user_id) {
		JSONObject retJson = new JSONObject();
		Timestamp leavedate = new Timestamp(System.currentTimeMillis());
		cmUserInfoDao.update(user_id, null, null, null, null, null, null, null, leavedate, "cancel", null);
		cmUserLoginInfoDao.update(user_id, null, null, null, "cancel", null, null, null);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	更新用户信息
	 * @param user_id
	 * @param name
	 * @param user_no 用户编号
	 * @param sex
	 * @param id_no
	 * @param birthday
	 * @param email
	 * @param phone
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	public JSONObject update(String user_id,String name,String user_no,String sex,String id_no,String birthday,String email, String phone,String remark) {
		JSONObject retJson = new JSONObject();
		cmUserInfoDao.update(user_id, name,user_no, sex, birthday, id_no, email, phone, null, null, remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	查询工作业务参数
	 * @return {retCode,retMsg,user_id,user_name,auth_user_id,auth_user_name,issueList:[{company,company_name,issue_acct,user_id}]}
	 */
	public JSONObject getUserWorkParams() {
		String user_id = sessionManager.getSession().getString("user_id");
		String user_name = sessionManager.getSession().getString("name");
		
		//HashMap map = getLeaderOrManager("leader");
		HashMap map = cmTeamUserDao.queryByMemBer(user_id);
		String auth_user_id = "";
		String auth_user_name = "";
		if(map != null && !map.isEmpty()) {
			auth_user_id = map.get("user_id").toString();
			auth_user_name = map.get("name").toString();
		}
		List<HashMap> issueList =  cmUserIssueAcctInfoDao.queryByUser(user_id,null);
		JSONObject retJson = new JSONObject();
		retJson.put("user_id", user_id);
		retJson.put("user_name", user_name);
		retJson.put("auth_user_id", auth_user_id);
		retJson.put("auth_user_name", auth_user_name);
		retJson.put("issueList", issueList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	获取leader或者经理信息
	 * @param type leader  manager
	 * @return null未分配   {user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}
	 */
	public HashMap getLeaderOrManager(String type) {
		String leader_url = "/html/stand_book/stand_book_first_auth.html";
		String manager_url = "/html/stand_book/stand_book_last_auth.html";
		List<HashMap> users = null;
		if("leader".equals(type)){//查询leader
			users = cmUserInfoDao.getByMenuPath(leader_url);
		}else if("manager".equals(type)) {//查询经理
			users = cmUserInfoDao.getByMenuPath(manager_url);
		}
		HashMap map = null;
		if(users != null && !users.isEmpty()) {
			map = users.get(0);
		}
		return map;
	}
	
	/**
	 *  	重置密码
	 * @param login_name 登陆名称
	 * @return {retCode,retMsg}
	 */
	public JSONObject pwd_reset(String login_name) {
		JSONObject retJson = new JSONObject();
		HashMap loginMap = cmUserLoginInfoDao.getLongInfo(login_name, null);
		if(loginMap == null || loginMap.isEmpty()) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "查无用户");
			return retJson;
		}
		
		Timestamp last_time = new Timestamp(System.currentTimeMillis());
		String user_id = loginMap.get("user_id").toString();
		String login_pwd = EncryptUtil.md5(user_id + login_name + login_name+"123");
		cmUserLoginInfoDao.reset(user_id, null, login_pwd, null,"init", "normal", last_time, null, null);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	public static void main(String[] args) {
		String login_pwd = EncryptUtil.md5("CU202007250000000001" + "admin" + "123456");
		System.out.println(login_pwd);
	}


	
}
