package com.common.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.service.RateOutService;

@Controller
public class RateOutController {
	
	@Autowired
	private RateOutService rateOutService;
	
	/**
	 * 	添加费率
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
	 * @return [{company,channel,kind,rate,stime }]
	 */
	@RequestMapping(path = "/crm/rate/out/add")
	@ResponseBody
	public String add(@RequestParam(name = "company",required = true)String company,
			@RequestParam(name = "rate_type",required = true)String rate_type,
			@RequestParam(name = "kind",required = true)String kind,
			@RequestParam(name = "inner_rate",required = true)Double inner_rate,
			@RequestParam(name = "out_rate",required = true)Double out_rate,
			@RequestParam(name = "rate",required = true)Double rate,
			@RequestParam(name = "stime",required = true)String stime,
			@RequestParam(name = "etime",required = false)String etime,
			@RequestParam(name = "ctime",required = false)String ctime,
			@RequestParam(name = "status",required = false)String status,
			@RequestParam(name = "user_id",required = false)String user_id,
			@RequestParam(name = "remark",required = false)String remark) {
		JSONObject retJson = rateOutService.add(company, rate_type,kind,inner_rate,out_rate, rate, stime,etime,ctime,"wait_use", user_id, remark);
		return retJson.toJSONString();
	}
	
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
	 */
	@RequestMapping(path = "/crm/rate/out/query")
	@ResponseBody
	public String query(@RequestParam(name = "company",required = false)String company,
			@RequestParam(name = "channel",required = false)String channel,
			@RequestParam(name = "kind",required = false)String kind,
			@RequestParam(name = "status",required = false)String status,
			@RequestParam(name = "stime",required = false)String stime,
			@RequestParam(name = "etime",required = false)String etime) {
		JSONObject retJson = rateOutService.query(company, kind, status, stime,etime);
		return retJson.toJSONString();
	}
	
	/**
	 * @param pid	编码
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/rate/out/modify")
	@ResponseBody
	public String modify(@RequestParam(name = "pid",required = true)Integer pid,
			@RequestParam(name = "etime",required = true)String etime) {
		JSONObject retJson = rateOutService.modify(pid,etime);
		return retJson.toJSONString();
	}
	
	/**
	 * 	删除费率
	 * @param pid 编码
	 * @return {retCode,retMsg}
	 */
	@RequestMapping(path = "/crm/rate/out/delete")
	@ResponseBody
	public String delete(@Param("pid")Integer pid) {
		JSONObject retJson = rateOutService.delete(pid);
		return retJson.toJSONString();
	}
}
