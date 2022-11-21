package com.common.crm.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface SequenceDao {
	
	/**
	 * 	查询序列信息
	 * @param seq_code
	 * @return {pid,seq_code,prefix,seq_value,max_length,seq_desc}
	 */
	public HashMap getSequence(@Param("seq_code")String seq_code);
	
	
	
	/**
	 * 	更新序列值
	 * @param seq_code
	 * @param seq_value
	 */
	public void updateSequence(@Param("seq_code")String seq_code,
			@Param("seq_value")Long seq_value );
	
	
}
