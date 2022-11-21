package com.common.crm.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmRateInnerInfoDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;
import com.common.crm.util.PublicUtil;

@Service
public class RateInnerService {

	
	@Autowired
	private CmRateInnerInfoDao cmRateInnerInfoDao;
	
	@Autowired
	private SessionManager sessionManager;
	
	/**
	 * 	创建费率
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
	 * @return {retCode,retMsg}
	 */
	public JSONObject add (String company,String channel,String kind,Double rate,String stime,String etime) {
		JSONObject retJson =  new JSONObject();
		String user_id=(String) sessionManager.getSession().get("user_id");
		Calendar c = Calendar.getInstance();
		String ctime = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd kk:mm:ss");
//		同一险种，新增的记录开始时间要大于正在使用记录的记录的开始时间
		List<HashMap>innerList=cmRateInnerInfoDao.queryMax(company,channel,kind,stime);
		for (HashMap innerMap  : innerList) {
			if(innerMap!=null) {
				String rateStime = innerMap.get("stime")== null ? null : innerMap.get("stime").toString();
				String rateEtime = innerMap.get("etime")== null ? null : innerMap.get("etime").toString();
				String newStime=rateStime.substring(0, rateStime.indexOf("."));
				String newEtime=rateEtime.substring(0, rateEtime.indexOf("."));
				
				if(newStime.compareTo(etime)!=-1) {
					retJson.put("retCode", "time_error");
					retJson.put("retMsg", "同一险种，新增的记录开始时间要大于已经存在记录的结束时间");
					return retJson;
				}
				
				if(newStime.compareTo(stime)!=-1) {
					retJson.put("retCode", "time_error");
					retJson.put("retMsg", "同一险种，新增的记录开始时间要大于已经存在记录的开始时间");
					return retJson;
				}
				
				if(newEtime.compareTo(etime)!=-1) {
					retJson.put("retCode", "time_error");
					retJson.put("retMsg", "同一险种，新增的记录结束时间要大于已经存在记录的结束时间");
					return retJson;
				}
				
			}
//			保留2位小数
			String strRate =PublicUtil.doubleToStr(rate,2);
			cmRateInnerInfoDao.add(company,channel,kind,strRate,stime,etime,ctime,user_id,null);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		return retJson;
	}
	
	/**
	 * 	费率查询
	 * @param pid	编码
	 * @param company	承保单位   pa平安  rb人保 tpy太平洋
	 * @param channel	适用渠道   dx电销 sd收单 other其他
	 * @param kind	保单种类  交强险-jqx  商险续保-sxxb  商险转保sxzb  驾意险-jyx 其他-other
	 * @param etime	承保日期 yyyy-MM-dd
	 * @return {retCode,retMsg,list:pid,company,channel,kind,rate,stime,etime,ctimeremark}
	 */
	public JSONObject query(String company,String channel,String kind,String underwrite_date) {
		List<HashMap> rateInfoList=cmRateInnerInfoDao.query(company,channel,kind,underwrite_date);
		JSONObject retJson =  new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", rateInfoList);
		return retJson;
	}
	
	/**
	 * 	更新费率
	 * @param pid	编码
	 * @param etime	结束时间 yyyy-MM-dd hh:mm:ss
	 * @return {retCode,retMsg}
	 */
	public JSONObject modify(Integer pid,String etime) {
		JSONObject retJson =  new JSONObject();
		cmRateInnerInfoDao.modify(pid,etime);
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
	
	/**
	 * 	删除费率
	 * @param pid 编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject delete(String[] pid) {
		JSONObject retJson =  new JSONObject();
		for(int i=0;i<pid.length;i++) {
			cmRateInnerInfoDao.delete(pid[i]);
		}
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
}

