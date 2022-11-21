package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
@MapperScan
public interface CmUserIssueAcctInfoDao {
	/**
	 * 	查询用户出单账号
	 * @param user_id 用户编码
	 * @return [{company,company_name,issue_acct,user_id}]
	 */
	public List<HashMap> queryByUser(@Param("user_id")String user_id,@Param("company")String company);
	
	/**
	 * 	删除记录
	 * @param user_id
	 * @param issue_acct
	 */
	public void delete(@Param("user_id")String user_id,
			@Param("issue_acct")String issue_acct);
	
	
	/**
	 *  无则插入 有则更新
	 * @param user_id 
	 * @param issue_acct
	 * @param company 承保单位编码
	 * @param company_name 承保单位名称
	 */
	public void replace(@Param("company")String company,
			@Param("company_name")String company_name,
			@Param("issue_acct")String issue_acct,
			@Param("user_id")String user_id);
}
