package com.common.crm.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CmUserInfoDao {
	
	/**
	 * 	查询信息
	 * @param user_id
	 * @return {user_id,name,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}
	 */
	public HashMap queryOne(@Param("user_id")String user_id);
	 
	/**
	 * 	添加用户
	 * @param user_id 用户编码
	 * @param name 姓名
	 * @param user_no 用户编号
	 * @param sex 性别  M男 W女
	 * @param birthday 生日
	 * @param id_no 身份证号
	 * @param hiredate 入职日期
	 * @param email	邮箱
	 * @param phone	手机号
	 * @param leavedate 离职日期
	 * @param ctime	创建时间
	 * @param status 状态 normal正常 quit离职
	 * @param remark 备注
	 */
	public void add(@Param("user_id")String user_id,
			@Param("name")String name,
			@Param("user_no")String user_no,
			@Param("sex")String sex,
			@Param("birthday")String birthday,
			@Param("id_no")String id_no,
			@Param("hiredate")Timestamp hiredate,
			@Param("email")String email,
			@Param("phone")String phone,
			@Param("leavedate")Timestamp leavedate,
			@Param("ctime")Timestamp ctime,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	查询用户列表
	 * @param user_id
	 * @param name
	 * @param status 状态 normal正常 quit离职
	 * @param have_team 是否已经加入team true是  false否
	 * @return  [{user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}]
	 */
	public List<HashMap> listUsers(@Param("user_id")String user_id,
			@Param("name")String name,
			@Param("status")String status,
			@Param("have_team")String have_team);
	
	/**
	 * 	更新用户
	 * @param user_id 用户编码
	 * @param name 姓名
	 * @param user_no 用户编号
	 * @param sex 性别  M男 W女
	 * @param birthday 生日
	 * @param id_no 身份证号
	 * @param email	邮箱
	 * @param phone	手机号
	 * @param leavedate 离职日期
	 * @param status 状态 normal正常 quit离职
	 * @param remark 备注
	 */
	public void update(@Param("user_id")String user_id,
			@Param("name")String name,
			@Param("user_no")String user_no,
			@Param("sex")String sex,
			@Param("birthday")String birthday,
			@Param("id_no")String id_no,
			@Param("email")String email,
			@Param("phone")String phone,
			@Param("leavedate")Timestamp leavedate,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	查询有指定菜单访问权限的人
	 * @param link_url 菜单全路径
	 * @return [{user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}]
	 */
	public List<HashMap> getByMenuPath(@Param("link_url")String link_url);

	/**
	 * 	根据用户号查询用户表内是否已经有记录
	 * @param user_no 用户号
	 * @return [{user_id,name,user_no,sex,birthday,id_no,hiredate,email,phone,leavedate,ctime,status,remark}]
	 */
	public HashMap queryByUserNo(@Param("user_no")String user_no);
	
}
