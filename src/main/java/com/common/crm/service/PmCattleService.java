package com.common.crm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.common.crm.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PmCattleService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CuCusInfoDao cuCusInfoDao;
	@Autowired
	private PmCattleInfoDao pmCattleInfoDao;
	@Autowired
	private PmCattleQuotaDao pmCattleQuotaDao;
	@Autowired
	private PmCattleAmtDao pmCattleAmtDao;
	@Autowired
	private SequenceService sequenceService;


	/**
	 * 	添加买家
	 * @param phone	手机号 正常状态的手机号只能出现一次
	 * @param name	姓名
	 * @param email	邮箱
	 * @return {retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject create(String phone,String name,String email) {
		JSONObject retJson = new JSONObject();
		HashMap cusMap = cuCusInfoDao.getByPhone(phone);
		if(cusMap==null||cusMap.isEmpty()){
			retJson.put("retCode","error");
			retJson.put("retMsg","查无用户信息");
			return retJson;
		}
		String cusid = cusMap.get("cusid").toString();
		
		HashMap cattleMap = pmCattleInfoDao.getCattleByPhone(phone);
		if(cattleMap != null && !cattleMap.isEmpty()) {
			retJson.put("retCode","error");
			retJson.put("retMsg","手机号已经被使用");
			return retJson;
		}
		
		cattleMap =pmCattleInfoDao.getByCusid(cusid, "normal ");
		if(cattleMap != null && !cattleMap.isEmpty()) {//
			retJson.put("retCode","error");
			retJson.put("retMsg","该用户已经是买家，不需要重复添加");
			return retJson;
		}
		String cattle_id = sequenceService.getNewSequence("cattle_id");
		pmCattleInfoDao.create(cattle_id,cusid,name,phone,email,new Date(),"normal","");
		pmCattleQuotaDao.create(cattle_id,BigDecimal.ZERO);
		pmCattleAmtDao.create(cattle_id,BigDecimal.ZERO);
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		return retJson;
	}
	

}
	


