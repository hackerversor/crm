package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public interface CuDriverLicenseDao {
	/**
	 * 	添加驾照
	 * @param licenseid 系统编码
	 * @param license_no 驾驶证号
	 * @param cusid 客户号
	 * @param archive 档案编码
	 * @param get_date 领取日期
	 * @param sdate 有效起期
	 * @param edate	有效止期
	 * @param license_type 驾照类型 A1 C1
	 * @param name	姓名
	 * @param sex M男 W女
	 * @param city 城市
	 * @param status 状态 normal delete
	 * @param ctime 添加时间
	 * @param remark 备注
	 */
	public void add(@Param("licenseid")String licenseid,
			@Param("license_no")String license_no,
			@Param("cusid")String cusid,
			@Param("archive")String archive,
			@Param("get_date")String get_date,
			@Param("sdate")String sdate,
			@Param("edate")String edate,
			@Param("license_type")String license_type,
			@Param("name")String name,
			@Param("sex")String sex,
			@Param("city")String city,
			@Param("status")String status,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
	
	
	/**
	 * 	查询数据
	 * @param cusid 客户号
	 * @param licenseid 证书编码
	 * @param license_no  驾驶证号
	 * @param archive 档案编码
	 * @param status  状态 normal delete
	 * @return [{licenseid,license_no,cusid,archive,get_date,sdate,edate,license_type,name,sex,city,status,ctime,remark}]
	 */
	public List<HashMap> list(@Param("cusid")String cusid,
			@Param("licenseid")String licenseid,
			@Param("license_no")String license_no,
			@Param("archive")String archive,
			@Param("status")String status);
}


