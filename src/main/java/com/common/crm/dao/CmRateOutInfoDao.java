package com.common.crm.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface CmRateOutInfoDao {

	/**
	 *	添加费率
	 * @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param channel	适用渠道   dx电销 sd收单 other其他
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param rate 	费率  小数点后两位有效数字 0-100之间的数字
	 * @param stime	开始时间  yyyy-MM-dd hh:mm:ss
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @param ctime	创建时间  yyyy-MM-dd hh:mm:ss
	 * @param status 状态 use使用中 wait_use待使用 invalid失效 delete删除
	 * @param user_id 员工编码
	 * @param remark 备注
	 * @return [{company,channel,kind,rate,stime }]
	 */
	public void add(@Param("company")String company,
			@Param("rate_type")String channel,
			@Param("kind")String kind,
			@Param("inner_rate")Double inner_rate,
			@Param("out_rate")Double out_rate,
			@Param("rate")Double rate,
			@Param("stime")String stime,
			@Param("etime")String etime,
			@Param("ctime")String ctime,
			@Param("status")String status,
			@Param("user_id")String user_id,
			@Param("remark")String remark);
	/**
	 * 	查询费率信息
	* @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param channel	适用渠道   dx电销 sd收单 other其他
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param rate 	费率  小数点后两位有效数字 0-100之间的数字
	 * @param stime	开始时间  yyyy-MM-dd hh:mm:ss
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @param ctime	创建时间  yyyy-MM-dd hh:mm:ss
	 * @param status 状态 use使用中 wait_use待使用 invalid失效 delete删除
	 * @param user_id 员工编码
	 * @param remark 备注
	 * @return [{company,channel,kind,rate,stime }]
	 */
	public List<HashMap> queryMax(
			@Param("company")String company,
			@Param("kind")String kind,
			@Param("stime")String stime,
			@Param("etime")String etime,
			@Param("status")String status);
	
	/**
	 * 	查询费率信息
	 * @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param inner_rate 账外费率
	 * @param out_rate 账外费率
	 * @param rate_type 费率类型  毛费率mfl 净费率jfl
	 * @param rate  费率小数点后两位有效数字 0-100之间的数字
	 * @param stime	开始时间  yyyy-MM-dd hh:mm:ss
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @param ctime	创建时间  yyyy-MM-dd hh:mm:ss
	 * @param status 状态 use使用中 wait_use待使用 invalid失效 delete删除
	 * @param user_id 员工编码
	 * @param remark 备注
	 * @return {retCode,retMsg,list:pid,company,kind,rate,stime,etime,ctime,status}
	 */
	public List<HashMap> query(
			@Param("company")String company,
			@Param("kind")String kind,
			@Param("status")String status,
			@Param("stime")String stime,
			@Param("etime")String etime);
	
	/**
	 *	更新费率
	 * @param pid	编码
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @param ctime	创建时间  yyyy-MM-dd hh:mm:ss
	 */
	public void modify(@Param("pid")Integer pid,
			@Param("etime")String etime);
	
	/**
	 *	删除
	 * @param pid	编码
	 */
	public int delete(@Param("pid")Integer pid);
}
