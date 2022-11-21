package com.common.crm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.StatService;

@Controller
public class StatController {
	@Autowired
	private StatService statService;
	
	@RequestMapping(path = "/crm/stat/queryForQR")
	@ResponseBody
	public String queryForQR() {
		return statService.queryForQR().toJSONString();
	}
	
	/**
	 *	台账汇总查询 --  按照日、月、年等维度汇总台账信息
	 * @param company 承保单位
	 * @param channel渠道
	 * @param ctime_s 开始日期
     * @param ctime_e 结束日期
     * @param stat_type 统计类型 
     * @param kind 险种类型
	 * @return {retCode,retMsg, list:[{date,datalist:[{kind,premium,count}]}],total_premium,total_count}
	 */
	@RequestMapping(path = "/crm/stand_book/stat/dmy_query")
	@ResponseBody
	public String dmyQuery(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "stat_type",required = false)String stat_type,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "kind",required = false)String[] kind){

		JSONObject retJson = statService.dmyQuery(company,channel,under_write_date_s,under_write_date_e,stat_type,user_id,kind);
		return retJson.toJSONString();
	}
	
	/**
	 *	员工保费汇总查询 --  按照员工分组
	 * @param stat_type 统计类型
     * @param order_field 排序字段 
     * @param order_type  排序类型
	 * @return {retCode,retMsg, list:[{user_id,name,policy_count,cx_premium,fc_premium}]}
	 */
	@RequestMapping(path = "/crm/stand_book/stat/query_by_user")
	@ResponseBody
	public String queryByUser(@RequestParam(name = "stat_type",required = true)String stat_type,
			@RequestParam(name = "order_field",required = true)String order_field,
			@RequestParam(name = "order_type",required = true)String order_type){

		JSONObject retJson = statService.queryByUser(stat_type,order_field,order_type);
		return retJson.toJSONString();
	}	
	
	/**
	 *	员工保费汇总  按照员工分组  --当日光荣榜
	 * @param date 日期  默认当天日期  yyyy-MM-dd
	 	{
	 * 	retCode = "",
	 * 	retMsg = "",
	 * 	sx_count_data = {first:[{user_id = "",name = "",policy_count = ""}],second:[{user_id= "",name= "",policy_count= ""}],third:[{user_id= "",name= "",policy_count= ""}] },
	 *  sx_premium_data = {first:[{user_id = "",name = "",premium = ""}],second:[{user_id = "",name = "",premium = ""}],third:[{user_id = "",name= "",premium = ""}] },
	 *  fc_count_data = {first:[{user_id = "",name = "",policy_count = ""}],second:[{user_id= "",name= "",policy_count= ""}],third:[{user_id= "",name= "",policy_count= ""}] },
	 *  fc_premium_data =  {first:[{user_id = "",name = "",premium = ""}],second:[{user_id = "",name = "",premium = ""}],third:[{user_id = "",name= "",premium = ""}] }
	 * }
	 */
	@RequestMapping(path = "/crm/stand_book/stat/day_honor_list")
	@ResponseBody
	public String dayHonorList(@RequestParam(name = "date",required = false)String date){
		JSONObject retJson = statService.day_honor_list(date);
		return retJson.toJSONString();
	}	
	
	/**
	 *	员工保费汇总  按照员工分组  
	 * 	@param under_write_date_s 
	 *  @param under_write_date_e 
	 *  @param kind
	 *  @param user_id
	 *  @param stat_type
	 *  @return {retCode,retMsg, list:[{under_write_date,user_list:[{user_id,user_name,count,premium}]}]}
	 * 
	 */
	@RequestMapping(path = "/crm/stand_book/stat/user_premium_query")
	@ResponseBody
	public String user_premium_query(@RequestParam(name = "under_write_date_s",required = true)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = true)String under_write_date_e,
			@RequestParam(name = "stat_type",required = true)String stat_type,
			@RequestParam(name = "kind",required = false)String[] kind,
			@RequestParam(name = "user_id",required = false)String[] user_id) {
			
		JSONObject retJson = statService.user_premium_query(under_write_date_s, under_write_date_e, stat_type, kind, user_id);
		return retJson.toJSONString();
	}	
	
	/**
	 *	周期业绩统计查询  按照保费倒叙  
	 * 	@param under_write_date_s  yyyy-MM-dd
	 *  @param under_write_date_e 
	 *  @param kind
	 *  @param user_id
	 *  @return {retCode,retMsg, list:[{user_id,user_name,count,premium}]}
	 * 
	 */
	@RequestMapping(path = "/crm/stand_book/stat/premium_by_date")
	@ResponseBody
	public String premium_by_date(@RequestParam(name = "under_write_date_s",required = false)String under_write_date_s,
			@RequestParam(name = "under_write_date_e",required = false)String under_write_date_e,
			@RequestParam(name = "kind",required = false)String[] kind,
			@RequestParam(name = "user_id",required = false)String[] user_id) {
			
		JSONObject retJson = statService.premium_by_date(under_write_date_s, under_write_date_e, kind, user_id);
		return retJson.toJSONString();
	}	
}
