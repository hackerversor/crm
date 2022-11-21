package com.common.crm.controller;

import com.common.crm.service.PmCattleOrderService;
import com.common.crm.service.PmCattleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class PmCattleController {
	
	@Autowired
	private PmCattleService pmCattleService;
	
	@Autowired
	private PmCattleOrderService pmCattleOrderService;
	
	
	/**
	 * 	添加买家
	 * @param phone	手机号
	 * @param name	姓名
	 * @param email	邮箱
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cattle/cattle_add")
	@ResponseBody
	public String create(@RequestParam(name = "phone",required = true)String phone,
						 @RequestParam(name = "name",required = true)String name,
						 @RequestParam(name = "email",required = false)String email) {
		JSONObject retJson = pmCattleService.create(phone, name,  email);
		return retJson.toJSONString();
	}
	
	
	
	/**
	 * 	收购订单查询
	 * @param order_id	订单编码
	 * @param payno	流水号
	 * @param card_no	卡号
	 * @param status	状态
	 * @param s_date	开始日期        开始日期 默认当天 yyyy-MM-dd
	 * @param e_date	结束日期        结束日期 默认当天 yyyy-MM-dd
	 * @param page_num	页数          默认1 从1开始
	 * @param page_count	页容量      默认30  最大100
	 * @return	{retCode,retMsg,total_count,total_amt,discount_total_amt,orders[{order_id,payno,cusid,cus_name,shunt,bank_code, bank_name,card_no,notice_id,amt,discount,discount_amt,status,status_msg,oper_status,ctime,utime,a.remark}]}
	 */
	@RequestMapping(path = "/crm/cattle/order_query")
	@ResponseBody
	public String order_query(@RequestParam(name = "order_id",required = false)String order_id,
							 @RequestParam(name = "payno",required = false)String payno,
							 @RequestParam(name = "card_no",required = false)String card_no,
							 @RequestParam(name = "status",required = false)String status,
							 @RequestParam(name = "s_date",required = false)String s_date,
							 @RequestParam(name = "e_date",required = false)String e_date,
							 @RequestParam(name = "page_num",required = false)Integer page_num,
							 @RequestParam(name = "page_count",required = false)Integer page_count) {
		JSONObject retJson = pmCattleOrderService.order_query(order_id, payno, card_no, status, s_date, e_date, page_num,page_count);
		return retJson.toJSONString();
	}
	/**
	 * 	收购订单查询
	 * @param order_ids	订单编码
	 * @return
	 *
	 */
	@RequestMapping(path = "/crm/cattle/order_deal")
	@ResponseBody
	public String order_deal(@RequestParam(name = "order_ids",required = true) List<String> order_ids) {
		JSONObject retJson = pmCattleOrderService.order_deal(order_ids);
		return retJson.toJSONString();
	}

	
}
