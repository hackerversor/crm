package com.common.crm.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmRateOutInfoDao;
import com.common.crm.support.SessionManager;
import com.common.crm.util.DateTimeUtil;

@Service
public class RateOutService {

	
	@Autowired
	private CmRateOutInfoDao cmRateOutInfoDao;
	
	@Autowired
	private SessionManager sessionManager;
	/**
	 * 	创建费率
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
	 * @return {retCode,retMsg}
	 */
	public JSONObject add (String company,String rate_type,String kind,Double inner_rate,Double out_rate,Double rate,
			String stime,String etime,String ctime,String status,String user_id,String remark) {
		user_id=(String) sessionManager.getSession().get("user_id");
		JSONObject retJson =  new JSONObject();
		
		Calendar c = Calendar.getInstance();
		ctime = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd kk:mm:ss");
//		同一险种，新增的记录开始时间要大于正在使用记录的记录的开始时间
		List<HashMap>rateOutList=cmRateOutInfoDao.queryMax(company,kind,stime,etime,status);
		for (HashMap rateOutMap  : rateOutList) {
			if(rateOutMap!=null) {
				String rateStime = rateOutMap.get("stime")== null ? null : rateOutMap.get("stime").toString();
				String rateEtime = rateOutMap.get("etime")== null ? null : rateOutMap.get("etime").toString();
				String newStime=rateStime.substring(0, rateStime.indexOf("."));
				String newEtime=rateEtime.substring(0, rateEtime.indexOf("."));
					if(newStime.compareTo(stime)!=-1) {
						 retJson.put("retCode", "time_error");
						 retJson.put("retMsg", "开始时间要大于正在使用的开始时间");
						 return retJson;
					 }
				}
			
			cmRateOutInfoDao.add(company,rate_type,kind,inner_rate,out_rate,rate,stime,etime,ctime,status,user_id,remark);
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}
		return retJson;
	}
	
	/**
	 * 	费率查询
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
	 * @return {retCode,retMsg,list:pid,company,rate_type,kind,inner_rate,out_rate,rate,stime,etime,ctime,status,user_id,remark}
	 */
	public JSONObject query(String company,String kind,String status,String stime,String etime) {
		JSONObject retJson =  new JSONObject();
		List<HashMap> rateOutInfoList=cmRateOutInfoDao.query(company,kind,status,stime,etime);
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", rateOutInfoList);
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
		cmRateOutInfoDao.modify(pid,etime);

		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}
	
	/**
	 * 	删除费率
	 * @param pid 编码
	 * @return {retCode,retMsg}
	 */
	public JSONObject delete(Integer pid) {
		JSONObject retJson =  new JSONObject();
		int num=cmRateOutInfoDao.delete(pid);
		if(num>0) {
			retJson.put("retCode", "success");
			retJson.put("retMsg", "成功");
		}else {
			retJson.put("retCode", "delete_error");
			retJson.put("retMsg", "其它状态的数据不可以删除");
		}
		
		return retJson;
		
	}
}

