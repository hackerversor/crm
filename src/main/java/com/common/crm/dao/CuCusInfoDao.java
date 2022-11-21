package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuCusInfoDao {
	
	/**
	 * 	查询用户列表
	 * @param cusid
	 * @param openid
	 * @param phone
	 * @param status
	 * @return [{cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}]
	 */
	public List<HashMap> list(@Param("cusid")String cusid,
			@Param("openid")String openid,
			@Param("phone")String phone,
			@Param("status")String status);
	/**
	 * 	根据客户号查询客户信息
	 * @param cusid
	 * @return  {cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}
	 */
	public HashMap getByCusid(@Param("cusid")String cusid);
	/**
	 * 	根据手机号查询正常状态的客户信息
	 * @param phone
	 * @return {cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}
	 */
	public HashMap getByPhone(@Param("phone")String phone);
	
	/**
	 * 	根据手机号查询正常状态的客户信息 事务锁定
	 * @param phone
	 * @return {cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}
	 */
	public HashMap getByPhoneLock(@Param("phone")String phone);
	/**
	 * 	根据openid查询正常状态的客户信息
	 * @param openid
	 * @return {cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}
	 */
	public HashMap getByOpenid(@Param("openid")String openid);
	
	/**
	 * 	根据openid查询正常状态的客户信息 事务锁定
	 * @param openid
	 * @return {cusid,openid,phone,ctime,last_login_time,status,cus_type,nickname,remark}
	 */
	public HashMap getByOpenidLock(@Param("openid")String openid);
	
	/**
	 * 	更新登录时间和昵称
	 * @param cusid
	 * @param nickname
	 * @param last_login_time
	 */
	public void upadte(@Param("cusid")String cusid,
			@Param("nickname")String nickname,
			@Param("last_login_time")Timestamp last_login_time);
	
	/**
	 * 	添加客户
	 * @param cusid	客户号
	 * @param openid 微信编码
	 * @param phone 手机号
	 * @param ctime 创建时间
	 * @param last_login_time 上次登录时间
	 * @param status 状态  normal delete 
	 * @param cus_type 类型  inner内部  cust正常客户 
	 * @param nickname 昵称
	 * @param remark 备注
	 */
	public void add(@Param("cusid")String cusid,
			@Param("openid")String openid,
			@Param("phone")String phone,
			@Param("ctime")Timestamp ctime,
			@Param("last_login_time")Timestamp last_login_time,
			@Param("status")String status,
			@Param("cus_type")String cus_type,
			@Param("nickname")String nickname,
			@Param("remark")String remark);
	
}
