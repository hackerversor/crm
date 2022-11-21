package com.common.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.RateInnerService;
@Controller
public class RateInnerController {
	
	@Autowired
	private RateInnerService rateInnerService;
	
	/**
	 * 	添加费率
	 * @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param channel	适用渠道   dx电销 sd收单 other其他
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param rate 	费率  小数点后两位有效数字 0-100之间的数字
	 * @param stime	开始时间  yyyy-MM-dd hh:mm:ss
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @param ctime	创建时间  yyyy-MM-dd hh:mm:ss
	 * @param user_id 员工编码
	 * @param remark 备注
	 * @return [{company,channel,kind,rate,stime }]
	 */
	@RequestMapping(path = "/crm/rate/inner/add")
	@ResponseBody
	public String add(@RequestParam(name = "company",required = true)String company,
			@RequestParam(name = "channel",required = true)String channel,
			@RequestParam(name = "kind",required = true)String kind,
			@RequestParam(name = "rate",required = true)Double rate,
			@RequestParam(name = "stime",required = true)String stime,
			@RequestParam(name = "etime",required = false)String etime) {
		JSONObject retJson = rateInnerService.add(company, channel, kind, rate, stime,etime);
		return retJson.toJSONString();
	}
	
	/**
	 * 	查询费率信息
	 * @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param channel	适用渠道   dx电销 sd收单 other其他
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param etime	承保日期 yyyy-MM-dd
	 * @return {retCode,retMsg,list:pid,company,channel,kind,rate,stime,etime,ctime,remark}
	 */
	@RequestMapping(path = "/crm/rate/inner/query")
	@ResponseBody
	public String query(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "kind",required = false)String kind,
			@RequestParam(name = "underwrite_date",required = false)String underwrite_date) {
		JSONObject retJson = rateInnerService.query(company, channel, kind, underwrite_date);
		return retJson.toJSONString();
	}
	
	/**
	 * @param pid	编码
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/rate/inner/modify")
	@ResponseBody
	public String modify(@RequestParam(name = "pid",required = true)Integer pid,
			@RequestParam(name = "etime",required = true)String etime) {
		JSONObject retJson = rateInnerService.modify(pid,etime);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除费率
	 * @param pid 编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/rate/inner/delete")
	@ResponseBody
	public String delete(@Param("pid")String[] pid) {
		JSONObject retJson = rateInnerService.delete(pid);
		return retJson.toJSONString();
	}
}
