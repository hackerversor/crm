package com.common.crm.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmServerInfoDao;
import com.common.crm.util.DateTimeUtil;

@Service
public class CmServerService {
	@Autowired
	private CmServerInfoDao cmServerInfoDao;
	@Autowired
	private SequenceService sequenceService;
	
	/**
	 * 	添加服务器
	 * @param short_name	简称
	 * @param addr_ip ip地址
	 * @param acct_id 账号
	 * @param disk	硬盘
	 * @param memory	内存
	 * @param cpu	cpu数
	 * @param buy_date	购买日期 yyyyMMdd
	 * @param exp_date	过期日期yyyyMMdd
	 * @param remark	备注
	 * @return	{retCode,retMsg}
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONObject add_server(String short_name,String addr_ip,String acct_id,
			String disk,String memory,String cpu,String buy_date,String exp_date,String remark) {
		JSONObject retJson = new JSONObject();
		HashMap sevMap = cmServerInfoDao.query(null, addr_ip);
		if(sevMap != null && !sevMap.isEmpty()) {
			retJson.put("retCode", "fail");
			retJson.put("retMsg", "该服务器已经添加");
			return retJson;
		}
		
		String server_id = sequenceService.getNewSequence("cm_server_id");
		cmServerInfoDao.add(server_id, short_name, addr_ip, acct_id, disk,
				memory, cpu, buy_date, exp_date, "normal", remark);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 查询服务器信息
	 * @param server_id	服务器编码
	 * @param short_name	简称
	 * @param addr_ip	ip地址
	 * @return  {retCode,retMsg,list[{server_id,short_name,addr_ip,acct_id,disk,memory,cpu,buy_date,exp_date,status,remark}]}
	 */
	public JSONObject query(String server_id,String short_name,String addr_ip,String status) {
		List<HashMap> list =  cmServerInfoDao.listServers(server_id, short_name, addr_ip,status);
		for (HashMap hashMap : list) {
			Date buy_date = (Date)hashMap.get("buy_date");
			Date exp_date = (Date)hashMap.get("exp_date");
			String buy_date_str = DateTimeUtil.date2Str(buy_date.getTime(), "yyyy-MM-dd");
			String exp_date_str = DateTimeUtil.date2Str(exp_date.getTime(), "yyyy-MM-dd");
			hashMap.put("buy_date", buy_date_str);
			hashMap.put("exp_date", exp_date_str);
		}
		JSONObject retJson = new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		retJson.put("list", list);
		return retJson;
	}
	
	/**
	 * 	注销服务器
	 * @param server_id
	 * @return {retCode,retMsg}
	 */
	public JSONObject cancel(String server_id) {
		cmServerInfoDao.update(server_id, null, null, null, null, null, null, null, null, "cancel", null);
		JSONObject retJson = new JSONObject();
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
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
	public JSONObject update(String server_id,String short_name,String addr_ip,String acct_id,String disk,String memory,
			String cpu,String buy_date,String exp_date,String remark) {
		JSONObject retJson = new JSONObject();
		
		if(addr_ip != null && addr_ip.length() > 0) {//如果需要更新ip信息 需要判断新ip是否已经存在
			List<HashMap> servers  = cmServerInfoDao.listServers(null, null, addr_ip, null);
			for (HashMap hashMap : servers) {
				String id = hashMap.get("server_id").toString();
				String ip = hashMap.get("addr_ip").toString();
				if(server_id.equals(id) && addr_ip.equals(ip)) {//与原数据相同 跳过
					continue;
				}else {
					retJson.put("retCode", "fail");
					retJson.put("retMsg", "该服务器已经添加");
					return retJson;
				}
			}
		}
		cmServerInfoDao.update(server_id, short_name, addr_ip, acct_id, disk, memory, cpu, buy_date, exp_date, null, remark);
		
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
}
