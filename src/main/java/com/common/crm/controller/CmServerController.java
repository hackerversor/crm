package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.CmServerService;

@Controller
public class CmServerController {
	@Autowired
	private CmServerService cmServerService;
	/**
	 * 	添加服务器
	 * @param short_name	简称
	 * @param addr_ip	ip地址
	 * @param acct_id 账号编码
	 * @param disk	硬盘容量
	 * @param memory	内存
	 * @param cpu	cpu数
	 * @param buy_date 购买日期 yyyy-MM-dd
	 * @param exp_date	过期日期yyyy-MM-dd
	 * @param remark 备注
	 * @return
	 */
	@RequestMapping(path = "/crm/cm_server/add")
	@ResponseBody
	public String add_server(@RequestParam(name = "short_name",required = true)String short_name,
			@RequestParam(name = "addr_ip",required = true)String addr_ip,
			@RequestParam(name = "acct_id",required = true)String acct_id,
			@RequestParam(name = "disk",required = true)String disk,
			@RequestParam(name = "memory",required = true)String memory,
			@RequestParam(name = "cpu",required = true)String cpu,
			@RequestParam(name = "buy_date",required = true)String buy_date,
			@RequestParam(name = "exp_date",required = true)String exp_date,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = cmServerService.add_server(short_name, addr_ip, acct_id, disk, memory, cpu, buy_date, exp_date, remark);
		return retJson.toJSONString();
	}
	
	/**
	 * 	查询服务器信息
	 * @param short_name	简称
	 * @param addr_ip	ip地址
	 * @param server_id 服务器编码
	 * @return {retCode,retMsg,list[{server_id,short_name,addr_ip,acct_id,disk,memory,cpu,buy_date,exp_date,status,remark}]}
	 */
	@RequestMapping(path = "/crm/cm_server/query")
	@ResponseBody
	public String query(@RequestParam(name = "short_name",required = false)String short_name,
			@RequestParam(name = "addr_ip",required = false)String addr_ip,
			@RequestParam(name = "server_id",required = false)String server_id,
			@RequestParam(name = "status",required = false)String status) {
		JSONObject retJson = cmServerService.query(server_id, short_name, addr_ip,status);
		return retJson.toJSONString();
	}
	
	/**
	 * 	注销
	 * @param server_id 服务器编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_server/cancel")
	@ResponseBody
	public String cancel(@RequestParam(name = "server_id",required = true)String server_id) {
		JSONObject retJson = cmServerService.cancel(server_id);
		return retJson.toJSONString();
	}
	
	/**
	 * 	更新数据
	 * @param server_id
	 * @param short_name
	 * @param addr_ip
	 * @param acct_id
	 * @param disk
	 * @param memory
	 * @param cpu
	 * @param buy_date
	 * @param exp_date
	 * @param remark
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/cm_server/update")
	@ResponseBody
	public String update(@RequestParam(name = "server_id",required = true)String server_id,
			@RequestParam(name = "short_name",required = false)String short_name,
			@RequestParam(name = "addr_ip",required = false)String addr_ip,
			@RequestParam(name = "acct_id",required = false)String acct_id,
			@RequestParam(name = "disk",required = false)String disk,
			@RequestParam(name = "memory",required = false)String memory,
			@RequestParam(name = "cpu",required = false)String cpu,
			@RequestParam(name = "buy_date",required = false)String buy_date,
			@RequestParam(name = "exp_date",required = false)String exp_date,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = cmServerService.update(server_id, short_name, addr_ip, acct_id, disk, memory, cpu, buy_date, exp_date, remark);
		return retJson.toJSONString();
	}
}
