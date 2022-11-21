package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;
import java.util.HashMap;

@MapperScan
public interface PmCattleInfoDao {
	/**
	 * 	查询买家信息 根据买家编码
	 * @param cattle_id 买家编码
	 * @param status  状态 normal delete
	 * @return {cattle_id,cusid,name,phone,email,ctime,status,remark}
	 */
	public HashMap getById(@Param("cattle_id")String cattle_id,@Param("status")String status);
	
	
	/**
	 * 	查询买家信息根据 客户编码
	 * @param cusid 客户号
	 * @param status  状态 normal delete
	 * @return {cattle_id,cusid,name,phone,email,ctime,status,remark}
	 */
	public HashMap getByCusid(@Param("cusid")String cusid,@Param("status")String status);


	/**
	 * 	查询买家信息 根据买家编码
	 * @param cattle_id 买家编码
	 * @param status  状态 normal delete
	 * @return {cattle_id,cusid,name,phone,email,ctime,status,remark}
	 */
	public void create(@Param("cattle_id")String cattle_id,
						  @Param("cusid")String cusid,
						  @Param("name")String name,
						  @Param("phone")String phone,
						  @Param("email")String email,
						  @Param("ctime") Date ctime,
						  @Param("status")String status,
						  @Param("remark")String remark);
	/**
	 * 	根据手机号获取正常状态的买家信息
	 * @param phone
	 * @return {cattle_id,cusid,name,phone,email,ctime,status,remark}
	 */
	public HashMap getCattleByPhone(@Param("phone")String phone);

}
