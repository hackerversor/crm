package com.common.crm.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmPolicyDataInfoDao;
import com.common.crm.util.ExcelUtil;

@Service
public class CmPolicyService {
	
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private CmPolicyDataInfoDao cmPolicyDataInfoDao;

	/**
	 * 	导入保单数据
	 * @param policy_file	登录名
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject data_imp(MultipartFile policy_file) throws Exception {

		JSONObject retJson = new JSONObject();
		InputStream in = policy_file.getInputStream();
		List<List<String>>  list = ExcelUtil.autoReadExcelSheet(in);
		List<HashMap>  tlist = new ArrayList<HashMap>();
		Calendar calendar = new GregorianCalendar(1900,0,-1);
		Date d = calendar.getTime();
		for (List<String> cells : list) {
			if(cells !=null && cells.size()>0) {
				HashMap map = new HashMap();
				String policy = cells.get(1);
				if(policy == null || policy.length() <= 0) {
					continue;
				}
				map.put("policy", policy);
				map.put("kind", cells.get(2));
				map.put("policy_holder", cells.get(3));
				map.put("phone", cells.get(4));
				map.put("sex", "女".equals(cells.get(6)) ? "W" : "M");
				map.put("addr", cells.get(7));
				map.put("car_no", cells.get(8));
				map.put("frame_no", cells.get(9));
				map.put("engine_no", cells.get(10));
				map.put("car_owner", cells.get(11));
				map.put("idno", cells.get(12));
				map.put("car_reg_date", cells.get(13));
				map.put("underwrite_date",  cells.get(0));
				map.put("s_date", cells.get(14));
				map.put("e_date", cells.get(15));
				map.put("written_premium", cells.get(16));
				map.put("fx", cells.get(17));
				map.put("source", cells.get(19));
				map.put("cust_manager", cells.get(20));
				map.put("status", cells.get(21));
				map.put("insurance", cells.get(22));
				map.put("record_used", "unused");
				tlist.add(map);
			}
		}
		if(tlist !=null && tlist.size()>0) {
			cmPolicyDataInfoDao.batch_add(tlist);
		}
		retJson.put("count", tlist.size());
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}

}
