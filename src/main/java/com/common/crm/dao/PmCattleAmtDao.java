package com.common.crm.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.HashMap;

@MapperScan
public interface PmCattleAmtDao {
	/**
	 * 	根据买家编码查询
	 * @param cattle_id 买家编码
	 * @return {cattle_id,amt}
	 */
	public HashMap getByCattleid(@Param("cattle_id")String cattle_id);
	/**
	 * 	获取买家额度余额
	 * @param cattle_id
	 * @return
	 */
	public BigDecimal getByCattleAmt(@Param("cattle_id")String cattle_id);
	
	/**
	 * 	获取买家额度余额   事务锁定
	 * @param cattle_id
	 * @return
	 */
	public BigDecimal getByCattleAmtLock(@Param("cattle_id")String cattle_id);
	
	/**
	 * 	更新额度
	 * @param cattle_id  买家编码
	 * @param quota 权益
	 */
	public void updateAmt(@Param("cattle_id")String cattle_id,@Param("quota")BigDecimal quota);
	/**
	 * 	创建额度
	 * @param cattle_id  买家编码
	 * @param amt 权益
	 */
	public void create(@Param("cattle_id")String cattle_id,@Param("amt")BigDecimal amt);
	
}
