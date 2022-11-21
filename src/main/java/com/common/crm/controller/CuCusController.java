package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CuCusService;

@Controller
public class CuCusController {
	
	@Autowired
	private CuCusService cuCusService;
	
	
	/**
	 * 	查询客户信息
	 * @param phone 手机号
	 * @return {retCode,retMsg,phone,ctime,last_login_time,status,cus_type,nickname,remark,balance,deleteList:[{phone,ctime,last_login_time,status,cus_type,nickname,remark}]}
	 */
	@RequestMapping(path = "/crm/cus/cusInfo")
	@ResponseBody
	public String cusInfo(@RequestParam(name = "phone",required = true)String phone) {
		JSONObject retJson = cuCusService.cusInfo(phone);
		return retJson.toJSONString();
	}
	
	/**
	 * 	查询客户交易明细信息
	 * @param phone 手机号
	 * @return {retCode,retMsg,list:[{payno,amt,pay_type,tran_type,tran_name,ctime,utime,status,rech_no,status_msg,cus_name,bank_name}]}
	 */
	@RequestMapping(path = "/crm/cus/cusOrderDetailList")
	@ResponseBody
	public String cusOrderDetailList(@RequestParam(name = "phone",required = true)String phone) {
		JSONObject retJson = cuCusService.cusOrderDetailList(phone);
		return retJson.toJSONString();
	}
	
	
	/**
	 * 	查询客户账户变动明细
	 * @param phone
	 * @return {retCode,retMsg,list:[{change_id,acctno,action_type,action_name,payno,amt,bf_balance,af_balance,ctime,remark}]}
	 */
	@RequestMapping(path = "/crm/cus/cusAcctDetailList")
	@ResponseBody
	public String cusAcctDetailList(@RequestParam(name = "phone",required = true)String phone) {
		JSONObject retJson = cuCusService.cusAcctDetailList(phone);
		return retJson.toJSONString();
	}
	
	
	@RequestMapping(path = "/crm/cus/file_order_query")
	@ResponseBody
	public String file_order_query() {
		JSONObject retJson = cuCusService.file_order_query();
		return retJson.toJSONString();
	}
	
	/**
	 * 	查询客户的拦截订单
	 * @return {retCode,retMsg,list:[{payno,cusid,pay_type,rech_type,rech_no,amt,status,ext,remark}]}
	 */
	@RequestMapping(path = "/crm/cus/file_order_deal")
	@ResponseBody
	public String file_order_deal(@RequestParam(name = "payno",required = true)String[] paynos) {
		JSONObject retJson = cuCusService.file_order_deal(paynos);
		return retJson.toJSONString();
	}
	
	
}
