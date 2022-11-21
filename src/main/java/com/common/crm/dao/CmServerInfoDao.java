package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface CmServerInfoDao {
	/**
	 * 	查询服务器信息  ip和id必须有一个
	 * @param server_id 服务器编码
	 * @param addr_ip	ip地址
	 * @return {server_id,short_name,addr_ip,acct_id,disk,memory,cpu,buy_date,exp_date,status,remark}
	 */
	public HashMap query(@Param("server_id")String server_id,
			@Param("addr_ip")String addr_ip);
	
	/**
	 * 	查询服务器信息 事务锁定  ip和id必须有一个
	 * @param server_id 服务器编码
	 * @param addr_ip	ip地址
	 * @return {server_id,short_name,addr_ip,acct_id,disk,memory,cpu,buy_date,exp_date,status,remark}
	 */
	public HashMap queryLock(@Param("server_id")String server_id,
			@Param("addr_ip")String addr_ip);
	
	/**
	 * 	添加服务器信息
	 * @param server_id	服务器编码
	 * @param short_name	短命
	 * @param addr_ip	ip地址
	 * @param acct_id	账户编码
	 * @param disk	硬盘容量
	 * @param memory	内存
	 * @param cpu	cpu数
	 * @param buy_date	购买日期
	 * @param exp_date	过期日期
	 * @param status	状态  normal正常  cancel注销
	 * @param remark	备注
	 */
	public void add(@Param("server_id")String server_id,
			@Param("short_name")String short_name,
			@Param("addr_ip")String addr_ip,
			@Param("acct_id")String acct_id,
			@Param("disk")String disk,
			@Param("memory")String memory,
			@Param("cpu")String cpu,
			@Param("buy_date")String buy_date,
			@Param("exp_date")String exp_date,
			@Param("status")String status,
			@Param("remark")String remark);
	
	/**
	 * 	查询服务器
	 * @param server_id	服务器编码
	 * @param addr_ip	ip地址 模糊查询
	 * @param short_name	简称 模块查询
	 * @return [{server_id,short_name,addr_ip,acct_id,disk,memory,cpu,buy_date,exp_date,status,remark}]
	 */
	public List<HashMap> listServers(@Param("server_id")String server_id,
			@Param("short_name")String short_name,
			@Param("addr_ip")String addr_ip,
			@Param("status")String status);
	
	
	/**
	 * 	更新数据
	 * @param server_id
	 * @param short_name
	 * @param addr_ip
	 * @param acct_id
	 * @param disk
	 * @param memory
	 * @param cpu
	 * @param buy_date
	 * @param exp_date
	 * @param status 状态  normal正常  cancel注销
	 * @param remark
	 */
	public void update(@Param("server_id")String server_id,
			@Param("short_name")String short_name,
			@Param("addr_ip")String addr_ip,
			@Param("acct_id")String acct_id,
			@Param("disk")String disk,
			@Param("memory")String memory,
			@Param("cpu")String cpu,
			@Param("buy_date")String buy_date,
			@Param("exp_date")String exp_date,
			@Param("status")String status,
			@Param("remark")String remark);
}
