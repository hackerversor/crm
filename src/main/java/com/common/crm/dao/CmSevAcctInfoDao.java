package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmSevAcctInfoDao {
	
	/**
	 * 	查询账号信息
	 * @param acct_id	账号编码
	 * @param login_name	登录名
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param user_id	用户名
	 * @param status	状态 normal正常 cancel注销
	 * @return [{acct_id,login_name,login_pwd,login_url,acct_desc,server_sup,auth_type,auth_set,user_id,name,phone,email,status,remark}]
	 */
	public List<HashMap> listAccts(@Param("acct_id")String acct_id,
			@Param("login_name")String login_name,
			@Param("server_sup")String server_sup,
			@Param("user_id")String user_id,
			@Param("status")String status);
	
	/**
	 * 	查询账号信息
	 * @param acct_id	账号编码
	 * @param login_name	登录名
	 * @return [{acct_id,login_name,login_pwd,login_url,acct_desc,server_sup,auth_type,auth_set,user_id,name,phone,email,status,remark}]
	 */
	public HashMap query(@Param("acct_id")String acct_id,
			@Param("login_name")String login_name);
	
	/**
	 * 	查询账号信息 事务锁定
	 * @param acct_id	账号编码
	 * @param login_name	登录名
	 * @return [{acct_id,login_name,login_pwd,login_url,acct_desc,server_sup,auth_type,auth_set,user_id,name,phone,email,status,remark}]
	 */
	public HashMap queryLock(@Param("acct_id")String acct_id,
			@Param("login_name")String login_name);
	
	/**
	 * 	添加服务器账号 
	 * @param acct_id  编码
	 * @param login_name 登录名
	 * @param login_pwd 登录密码
	 * @param login_url	登录地址
	 * @param acct_desc	描述
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id	负责人编码
	 * @param phone 手机号
	 * @param email	邮箱
	 * @param status 状态  normal正常 cancel注销
	 * @param remark 备注
	 */
	public void add(@Param("acct_id")String acct_id,
			@Param("login_name")String login_name,
			@Param("login_pwd")String login_pwd,
			@Param("login_url")String login_url,
			@Param("acct_desc")String acct_desc,
			@Param("server_sup")String server_sup,
			@Param("auth_type")String auth_type,
			@Param("auth_set")String auth_set,
			@Param("user_id")String user_id,
			@Param("phone")String phone,
			@Param("email")String email,
			@Param("status")String status,
			@Param("remark")String remark);
	/**
	 * 	删除
	 * @param acct_id
	 */
	public void del(@Param("acct_id")String acct_id);
	
	/**
	 * 	更新
	 * @param acct_id  编码
	 * @param login_name 登录名
	 * @param login_pwd 登录密码
	 * @param login_url	登录地址
	 * @param acct_desc	描述
	 * @param server_sup 服务提供者 wx微信公众平台 aly阿里云  juhe聚合  xx欣享 xy欣意 yk越开
	 * @param auth_type 认证方式  sms短信  wx微信
	 * @param auth_set 认证设备   微信号或者手机号
	 * @param user_id	负责人编码
	 * @param phone 手机号
	 * @param email	邮箱
	 * @param status 状态  normal正常 cancel注销
	 * @param remark 备注
	 */
	public void update(@Param("acct_id")String acct_id,
			@Param("login_name")String login_name,
			@Param("login_pwd")String login_pwd,
			@Param("login_url")String login_url,
			@Param("acct_desc")String acct_desc,
			@Param("server_sup")String server_sup,
			@Param("auth_type")String auth_type,
			@Param("auth_set")String auth_set,
			@Param("user_id")String user_id,
			@Param("phone")String phone,
			@Param("email")String email,
			@Param("status")String status,
			@Param("remark")String remark);
	
}
