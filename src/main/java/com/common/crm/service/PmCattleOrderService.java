package com.common.crm.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.common.crm.util.HttpUtil;
import com.common.crm.util.PublicUtil;
import com.common.crm.util.Pubutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.PmCattleOrderInfoDao;
import com.common.crm.util.DateTimeUtil;

@Service
public class PmCattleOrderService {
	@Autowired
	private PmCattleOrderInfoDao pmCattleOrderInfoDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private CacheService cacheService;

	/**
	 * 	收购订单查询
	 * @param order_id	订单编码
	 * @param payno	流水号
	 * @param card_no	卡号
	 * @param status	状态
	 * @param s_date	开始日期        开始日期 默认当天 yyyy-MM-dd
	 * @param e_date	结束日期        结束日期 默认当天 yyyy-MM-dd
	 * @param page_num	页数        从0开始   默认0
	 * @param page_count	页容量      默认30  最大100
	 * @return	{retCode,retMsg,total_count,total_amt,discount_total_amt,orders[{order_id,payno,cusid,cus_name,shunt,bank_code, bank_name,card_no,notice_id,amt,discount,discount_amt,status,status_msg,oper_status,ctime,utime,a.remark}]}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject order_query(String order_id,String payno,String card_no, String status,String s_date,String e_date,Integer page_num,Integer page_count) {
		
		JSONObject retJson = new JSONObject();
		// 时间和分页加条件 开始时间和结束时间 默认当天 yyyy-MM-dd
		String nowDate = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd");
		if(s_date == null || "".equals(s_date)) {
			s_date = nowDate;
		}
		if(e_date == null || "".equals(e_date)) {
			e_date = nowDate;
		}
		page_num = page_num == null ? 0 : page_num;
		if(page_num > 0){ 
			page_num = page_num -1;
		}
		page_count = page_count == null ? 30 : page_count;
		if(page_count > 100) {
			page_count = 100;
		}
		Integer total_count= pmCattleOrderInfoDao.statCount(order_id, payno, card_no, status, s_date, e_date);

		Integer start_index = page_count*page_num;
		// order_id,payno,cusid,cus_name,shunt,bank_code, bank_name,card_no,notice_id,amt,discount,discount_amt,status,status_msg,oper_status,ctime,utime,a.remark
		List<HashMap> orderList = pmCattleOrderInfoDao.order_query(order_id, payno, card_no, status,s_date, e_date, start_index, page_count);
		if(orderList == null || orderList.isEmpty()){
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "查无记录");
			return retJson;
		}
		
		BigDecimal  total_amt = BigDecimal.ZERO;
		BigDecimal  discount_total_amt = BigDecimal.ZERO;
		for (int i = 0 ;i<orderList.size();i++){
			HashMap order = orderList.get(i);
			Timestamp ctime = (Timestamp)order.get("ctime");
			order.put("ctime",DateTimeUtil.date2Str(ctime.getTime(), "yyyy-MM-dd HH:mm:ss"));
			if(order.get("utime") != null ) {
				Timestamp utime = (Timestamp)order.get("utime");
				order.put("utime",DateTimeUtil.date2Str(utime.getTime(), "yyyy-MM-dd HH:mm:ss"));
			}
			BigDecimal amt = (BigDecimal)order.get("amt");
			total_amt.add(amt);
			BigDecimal discount_amt = (BigDecimal)order.get("discount_amt");
			discount_total_amt = discount_total_amt.add(discount_amt);
		}

		retJson.put("total_count",total_count);
		retJson.put("total_amt",total_amt);
		retJson.put("discount_total_amt",discount_total_amt);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("orders", orderList);
		return retJson;
	}
	/**
	 * 	收购订单查询
	 * @param order_ids	订单编码
	 * @return
	 *
	 */
	public JSONObject order_deal(List<String> order_ids) {
		JSONObject retJson = new JSONObject();

		for (String order_id: order_ids) {
			if(order_id !=null && !"".equals(order_id)){
				// TODO: 2020/7/26 根据订单号调用
				String url = cacheService.getCacheParamValue("catlte_order_deal_url");
				HashMap<String,String> params = new HashMap<String, String>();
				params.put("order_id",order_id);
				String result = HttpUtil.httpPost(url, params);
				JSONObject resultJson = JSONObject.parseObject(result);
			}
		}

		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}

}
