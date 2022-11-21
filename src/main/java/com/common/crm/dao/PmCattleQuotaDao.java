package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.HashMap;

@MapperScan
public interface PmCattleQuotaDao {
	/**
	 * 	根据买家编码查询
	 * @param cattle_id 买家编码
	 * @return {cattle_id,quota}
	 */
	public HashMap getByCattleid(@Param("cattle_id")String cattle_id);
	/**
	 * 	获取买家额度余额
	 * @param cattle_id
	 * @return
	 */
	public BigDecimal getByCattleQuota(@Param("cattle_id")String cattle_id);
	
	/**
	 * 	获取买家额度余额   事务锁定
	 * @param cattle_id
	 * @return
	 */
	public BigDecimal getByCattleQuotaLock(@Param("cattle_id")String cattle_id);
	
	/**
	 * 	更新额度
	 * @param cattle_id  买家编码
	 * @param quota 额度
	 */
	public void updateQuota(@Param("cattle_id")String cattle_id,@Param("quota")BigDecimal quota);

	/**
	 * 	新增额度
	 * @param cattle_id  买家编码
	 * @param quota 额度
	 */
	public void create(@Param("cattle_id")String cattle_id,@Param("quota")BigDecimal quota);
	
}
