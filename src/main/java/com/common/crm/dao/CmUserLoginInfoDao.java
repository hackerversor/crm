package com.common.crm.dao;

import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmUserLoginInfoDao {
	/**
	 * 	获取登录信息  两个参数必须输入其中一个
	 * @param login_name 登录密码
	 * @param user_id	用户编码
	 * @return {user_id,login_name,login_pwd,ctime,pwd_status,status,last_time,last_pwd,remark}
	 */
	public HashMap getLongInfo(@Param("login_name")String login_name,
			@Param("user_id")String user_id);
	
	/**
	 * 	获取登录信息 事务锁定 两个参数必须输入其中一个
	 * @param login_name 登录密码
	 * @param user_id	用户编码
	 * @return {user_id,login_name,login_pwd,ctime,pwd_status,status,last_time,last_pwd,remark}
	 */
	public HashMap getLongInfoLock(@Param("login_name")String login_name,
			@Param("user_id")String user_id);
	
	/**
	 * 	添加登录信息
	 * @param user_id	用户编码
	 * @param login_name	登录名
	 * @param login_pwd 	密码
	 * @param ctime	创建时间
	 * @param pwd_status	密码状态 init初始 reset重置 normal正常
	 * @param status	状态	normal正常 cancel注销
	 * @param last_time	上次修时间
	 * @param last_pwd	上次密码
	 * @param remark	备注
	 */
	public void add(@Param("user_id")String user_id,
			@Param("login_name")String login_name,
			@Param("login_pwd")String login_pwd,
			@Param("ctime")Timestamp ctime,
			@Param("pwd_status")String pwd_status,
			@Param("status")String status,
			@Param("last_time")String last_time,
			@Param("last_pwd")String last_pwd,
			@Param("remark")String remark);
	
	/**
	 * 	更新登录信息
	 * @param user_id	用户编码
	 * @param login_name	登录名
	 * @param login_pwd 	密码
	 * @param pwd_status	密码状态 init初始 reset重置 normal正常
	 * @param status	状态	normal正常 cancel注销
	 * @param last_time	上次修时间
	 * @param last_pwd	上次密码
	 * @param remark	备注
	 */
	public void update(@Param("user_id")String user_id,
			@Param("login_name")String login_name,
			@Param("login_pwd")String login_pwd,
			@Param("pwd_status")String pwd_status,
			@Param("status")String status,
			@Param("last_time")String last_time,
			@Param("last_pwd")String last_pwd,
			@Param("remark")String remark);

	/**
	 *  	重置密码
	 * @param login_name 登陆名称
	 * @return {retCode,retMsg}
	 */
	public void reset(@Param("user_id")String user_id,
			@Param("login_name")String login_name,
			@Param("login_pwd")String login_pwd,
			@Param("ctime")Timestamp ctime,
			@Param("pwd_status")String pwd_status,
			@Param("status")String status,
			@Param("last_time")Timestamp last_time,
			@Param("last_pwd")String last_pwd,
			@Param("remark")String remark);
	
}
