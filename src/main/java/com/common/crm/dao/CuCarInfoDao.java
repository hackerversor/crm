package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@MapperScan
public interface CuCarInfoDao {
	/**
	 * 	添加车辆
	 * @param carid	车辆编码
	 * @param cusid 客户号
	 * @param car_no 车牌
	 * @param engine 发动机
	 * @param frame_no 车架
	 * @param brand 品牌
	 * @param brand_model 品牌型号
	 * @param brand_date 品牌日期
	 * @param reg_date 注册日期
	 * @param mileage 公里数
	 * @param status 状态 normal delete
	 * @param ctime 创建时间
	 * @param remark 备注
	 */
	public void add(@Param("carid")String carid,
			@Param("cusid")String cusid,
			@Param("car_no")String car_no,
			@Param("engine")String engine,
			@Param("frame_no")String frame_no,
			@Param("brand")String brand,
			@Param("brand_model")String brand_model,
			@Param("brand_date")String brand_date,
			@Param("reg_date")String reg_date,
			@Param("mileage")Integer mileage,
			@Param("status")String status,
			@Param("ctime")Timestamp ctime,
			@Param("remark")String remark);
	
	/**
	 * 	查询客户的车辆信息
	 * @param carid 车辆编码
	 * @param cusid  客户号
	 * @param car_no 车牌
	 * @param status 状态 normal/delete
	 * @return [{carid,cusid,car_no,engine,frame_no,brand,brand_model,brand_date,reg_date,mileage,status,ctime,remark}]
	 */
	public List<HashMap> list(@Param("carid")String carid,
			@Param("cusid")String cusid,
			@Param("car_no")String car_no,
			@Param("status")String status);
	
	
	/**
	 *	根据车辆编码查询车辆
	 * @param carid
	 * @return {carid,cusid,car_no,engine,frame_no,brand,brand_model,brand_date,reg_date,mileage,status,ctime,remark}
	 */
	public HashMap getByCarid(@Param("carid")String carid);
}
