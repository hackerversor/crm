package com.common.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.crm.dao.CmStandBookDao;
import com.common.crm.dao.CmTeamUserDao;
import com.common.crm.dao.CmUserInfoDao;
import com.common.crm.dao.StatDao;
import com.common.crm.support.ApplicationContext;
import com.common.crm.support.WebSocket;
import com.common.crm.util.DateTimeUtil;


@Service
public class StatService {
	@Autowired
	private StatDao statDao;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private CmStandBookDao cmStandBookDao;
	@Autowired
	private CmTeamUserDao cmTeamUserDao;
	@Autowired
	private WebSocket webSocket;
	@Autowired
	private CmUserInfoDao cmUserInfoDao;
	
	public JSONObject queryForQR() {
		List<HashMap>  list = statDao.queryForQR();
		JSONObject retJson = new JSONObject();
		if(list == null || list.isEmpty()) {
			retJson.put("retCode","fail");
			retJson.put("retMsg","查无数据");
			return retJson;
		}else {
			for (HashMap hashMap : list) {
				byte[] bb = hashMap.get("count") == null ? null : (byte[])hashMap.get("count");
				if(bb != null) {
					hashMap.put("count",new String(bb));
				}else {
					hashMap.put("count",0);
				}
				
			}
		}
		retJson.put("retCode","success");
		retJson.put("retMsg","成功");
		retJson.put("list",list);
		return retJson;
	}
	
	/**
	 *	台账汇总查询 --  按照日、月、年等维度汇总台账信息
	 * @param company 承保单位
	 * @param channel渠道
	 * @param ctime_s 开始日期
     * @param ctime_e 结束日期
     * @param stat_type 统计类型  d日 m月 
     * @param kind 险种类型
	 * @return {retCode,retMsg, list:[{date,datalist:[{kind,premium,count}]}],total_premium,total_count}
	 */
	public JSONObject dmyQuery(String company,String channel,String under_write_date_s,String under_write_date_e,String stat_type,String user_id,String[] kind){
		JSONObject retJson =  new JSONObject();
		
		List list = new ArrayList();
		if(stat_type == null || stat_type.length() == 0) {
			stat_type = "d";
		}
		List<HashMap> dblist = null;
		if("d".equals(stat_type)) {//按日统计
			dblist = statDao.dmyQueryByDay(company, channel, under_write_date_s, under_write_date_e, user_id,kind);
		}else if("m".equals(stat_type)) {//按月统计
			dblist = statDao.dmyQueryByMonth(company, channel, under_write_date_s, under_write_date_e, user_id,kind);
		}
		if(dblist == null || dblist.isEmpty()) {
			retJson.put("retCode", "fair");
			retJson.put("retMsg", "查无数据");
			retJson.put("list", list);
			return retJson;
		}else {
			HashMap<String,List<HashMap>> map = new HashMap<String,List<HashMap>>();
			for (HashMap hashMap : dblist) {
				String date1 = hashMap.get("date").toString();
				String date = date1;
				List datalist = map.get(date);
				if(datalist == null) {
					datalist= new ArrayList<HashMap>();
					map.put(date,datalist);
				}
				Double premium = hashMap.get("premium") == null ? 0.0 : (Double)hashMap.get("premium");
				String ckind = hashMap.get("kind").toString();
				Long count = (Long)hashMap.get("count");
				Map sub_map = new HashMap();
				sub_map.put("premium",premium);
				sub_map.put("kind",ckind);
				sub_map.put("count",count);
				datalist.add(sub_map);
				
			}
			System.out.println(map);
			for (Map.Entry entry : map.entrySet()) {
				String date = entry.getKey().toString();
				List<HashMap> datalist = (List<HashMap>)entry.getValue();
				Double total_premium = 0.0;
				Long total_count = 0L;
				for (HashMap sub_map : datalist) {
					Double premium = sub_map.get("premium") == null ? 0.0 : (Double)sub_map.get("premium");
					Long count = (Long)sub_map.get("count");
					total_premium = total_premium + premium;
					total_count = total_count + count;
				}
				HashMap  datamap = new HashMap ();
				datamap.put("date",date);
				datamap.put("total_premium",total_premium);
				datamap.put("total_count",total_count);
				datamap.put("datalist",datalist);
				list.add(datamap);
			}
		}
		//排序
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("date").toString().compareTo(o2.get("date").toString());
			}
			
		});
		retJson.put("list", list);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
		
	}

	/**
	 *	员工保费汇总查询 --  按照员工分组
	 * @param stat_type 统计类型
     * @param order_field 排序字段 
     * @param order_type  排序类型
	 * @return {retCode,retMsg, list:[{user_id,name,policy_count,cx_premium,fc_premium}]}
	 */
	public JSONObject queryByUser( String stat_type, String order_field,String order_type) {
		JSONObject retJson =  new JSONObject();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		
		if(stat_type == null || stat_type.length() == 0) {
			stat_type = "d";
		}
		
		List<HashMap> dblist = null;
		if("d".equals(stat_type)) {//按日统计
			String sdate = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd");
			String edate = DateTimeUtil.date2Str(c.getTimeInMillis(),"yyyy-MM-dd");
			dblist = statDao.queryByUser(sdate,edate,order_field,order_type);
		}else if("w".equals(stat_type)) {//按周统计
			c.add(Calendar.WEEK_OF_MONTH, 0);
		    c.set(Calendar.DAY_OF_WEEK, 2);
		    Date first=c.getTime();
		    String sdate = format.format(first);
		    //本周第一天	
			c.set(Calendar.DAY_OF_WEEK, c.getActualMaximum(Calendar.DAY_OF_WEEK));
	        c.add(Calendar.DAY_OF_WEEK, 1);
	        Date last=c.getTime();
	        String edate = format.format(last);
	        //本周最后一天	
			dblist = statDao.queryByUser(sdate, edate,order_field,order_type);
		}else if("m".equals(stat_type)) {//按月统计
			c.setTime(new Date());
			//本月第一天
			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date first = c.getTime();
			String sdate = format.format(first);
			//本月最后一天
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date last = c.getTime();
			String edate = format.format(last);
			dblist = statDao.queryByUser(sdate,edate,order_field,order_type);
		}
		
		List listMap =new ArrayList();
		for (HashMap standMap : dblist) {
			Map map=new HashMap();
			String user_id=standMap.get("user_id").toString();
			String name=standMap.get("name").toString();
			//车险金额
			Double cx_premium = standMap.get("cx_premium") == null ? 0.0 : (Double)standMap.get("cx_premium");
		    //成单数
			Double policy_count = standMap.get("policy_count")== null ? 0.0 :(Long)standMap.get("policy_count");
			//非车金额
			Double fc_premium = standMap.get("fc_premium") == null ? 0.0 : (Double)standMap.get("fc_premium");

			map.put("user_id",user_id);
			map.put("name",name);
			map.put("policy_count",policy_count);
			map.put("cx_premium",cx_premium);	
			map.put("fc_premium",fc_premium);
			
			listMap.add(map);
		}
		String socketUrl=cacheService.getCacheParamValue("websocket_url");
		retJson.put("socketUrl", socketUrl);
		retJson.put("list", listMap);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}


	  /**
	 *  	当日光荣榜
	 * @param date 日期
	 * @return
	 * {
	 * 	retCode = "",
	 * 	retMsg = "",
	 * 	sx_count_data = {first:[{user_id = "",name = "",policy_count = ""}],second:[{user_id= "",name= "",policy_count= ""}],third:[{user_id= "",name= "",policy_count= ""}] },
	 *  sx_premium_data = {first:[{user_id = "",name = "",premium = ""}],second:[{user_id = "",name = "",premium = ""}],third:[{user_id = "",name= "",premium = ""}] },
	 *  fc_count_data = {first:[{user_id = "",name = "",policy_count = ""}],second:[{user_id= "",name= "",policy_count= ""}],third:[{user_id= "",name= "",policy_count= ""}] },
	 *  fc_premium_data =  {first:[{user_id = "",name = "",premium = ""}],second:[{user_id = "",name = "",premium = ""}],third:[{user_id = "",name= "",premium = ""}] }
	 * }
	 * 
	 * 
	 */
	public JSONObject day_honor_list(String date){
		if(date == null || date.length() == 0) {
			date = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd");
		}
		//sx_count_data	商险成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
		ConcurrentHashMap sx_count_data = ApplicationContext.sx_count_data;
		
		//sx_premium_data	商险保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
		ConcurrentHashMap sx_premium_data = ApplicationContext.sx_premium_data;

		//fc_count_data	非车成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
		ConcurrentHashMap fc_count_data = ApplicationContext.fc_count_data;

		//fc_premium_data	非车保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
		ConcurrentHashMap fc_premium_data = ApplicationContext.fc_premium_data;

		JSONObject retJson =  new JSONObject();
		retJson.put("sx_count_data",sx_count_data);
		retJson.put("sx_premium_data",sx_premium_data);
		retJson.put("fc_count_data",fc_count_data);
		retJson.put("fc_premium_data",fc_premium_data);
		return retJson;
	}
	
	//event_type	
	//	order_change	排名变动事件 调用员工保费汇总(/crm/stand_book/stat/query_by_user)接口 刷新排名
	//	complete_order	成单事件
	//	challenge_count_premium	完成任务事件(单数和保费金额同时完成) 
	//	challenge_count	完成任务事件(单数完成)
	//	challenge_premium 完成任务事件(保费金额完成)
	//	day_honor_change	日榜变化
	//data name:张三 	 challenge_count_premium  challenge_count  challenge_premium 事件时有值
	//desc 事件说明
	//time 停留时间单位秒
	//示例:
	//{
	//    event_type: complete_order,
	//    data: {
	//        name:张三
	//    },
	//    desc: 成单事件,
	//    time: 20
	//}
	public void refreshAchievement(String user_id,String car_no) {
		
		String param_value = cacheService.getCacheParamValue("tv_show_times");
		Integer complete_order_times = 20;
		Integer challenge_count_premium_times = 20;
		Integer challenge_count_times = 20;
		Integer challenge_premium_times = 20;
		Integer day_honor_change_times = 20;
		if(param_value != null ) {
			JSONObject paramJson = JSONObject.parseObject(param_value);
			complete_order_times = paramJson.getInteger("complete_order");
			challenge_count_premium_times = paramJson.getInteger("challenge_count_premium");
			challenge_count_times =  paramJson.getInteger("challenge_count");
			challenge_premium_times =  paramJson.getInteger("challenge_premium");
			day_honor_change_times =  paramJson.getInteger("day_honor_change");
		}
		
		
		
		HashMap userMap = cmUserInfoDao.queryOne(user_id);
		String name = userMap.get("name").toString();
		/* 刷新整体排名 order_list */
		webSocket.sendEnent("order_change", name, "排名变动事件", 20);
		/* 推送成单通知 complete_order */
		webSocket.sendEnent("complete_order", name, "成单事件", complete_order_times);
		/* 挑战保单数和保费金额同时完成 challenge_count_premium */
		String under_write_date_s = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd");
		String under_write_date_e = under_write_date_s;
		String[]kind={"sxzb","sxxb"};
		//当前车辆的金额
		HashMap premium_count_map = cmStandBookDao.premium_count(under_write_date_s, under_write_date_e, kind, car_no, user_id);
		if(premium_count_map != null && !premium_count_map.isEmpty()) {
			Double  premium = (Double)premium_count_map.get("premium");
			//默认第一天第一单没有数据
			if(premium != null ) 
			{
				Long count = (long)premium_count_map.get("count");
				//当天所有的保单数和金额
				HashMap all_premium_count_map = cmStandBookDao.premium_count(under_write_date_s, under_write_date_e, kind, null, user_id);
				Double  all_premium = (Double)all_premium_count_map.get("premium");//当天所有的金额
				//默认第一天没有数据
				if(all_premium != null) 
				{
					Long all_count = (long)all_premium_count_map.get("count");//当天所有的保单数
					if(all_count == 5 && (all_premium - premium) < 10000 && all_premium >= 10000) {//正好是第五单 而且总保费刚好大于10000   触发事件:  挑战保单数和保费金额同时完成 challenge_count_premium 
						/* 挑战保单数和保费金额同时完成 challenge_count_premium */
						webSocket.sendEnent("challenge_count_premium", name, "挑战保单数和保费金额", challenge_count_premium_times);
					}else if(all_count == 5){ //只完成保单挑战
						/* 挑战保单数完成 challenge_count 5 */
						webSocket.sendEnent("challenge_count", name, "挑战保单数", challenge_count_times);
					}else if((all_premium - premium) < 10000 && all_premium >= 10000) {//只完成保费挑战
						/* 挑战保费金额完成 challenge_premium 10000 */
						webSocket.sendEnent("challenge_premium", name, "挑战保费金额", challenge_premium_times);
					}
				}
				
			}
			
		}
		/* 日榜变动 day_honor_change */
		boolean changed = false;
		{//商险成单数
			//成单数排名列表 [{count,user_id,name}]
			List<HashMap> sx_count_list = cmStandBookDao.count_user_order(under_write_date_s, under_write_date_e,kind);
			//新排名
			HashMap  new_sx_count_data = getSX123(sx_count_list, "count");
			//sx_count_data	原商险成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
			ConcurrentHashMap sx_count_data = ApplicationContext.sx_count_data;
			changed = check_changed(sx_count_data, new_sx_count_data, "count") || changed;
		}
		{//商险金额
			//保费排名列表 [{premium,user_id,name}]
			List<HashMap> sx_premium_list = cmStandBookDao.premium_user_order(under_write_date_s, under_write_date_e,kind);
			//新排名
			HashMap new_sx_premium_data = getSX123(sx_premium_list, "premium");
			//sx_premium_data	商险保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
			ConcurrentHashMap sx_premium_data = ApplicationContext.sx_premium_data;
			changed = check_changed(sx_premium_data, new_sx_premium_data, "premium") || changed;
		}
		
		{//非车单数	
			String[]kinds={"jyx"};
			//成单数排名列表 [{count,user_id,name}]
			List<HashMap> fc_count_list = cmStandBookDao.count_user_order(under_write_date_s, under_write_date_e,kinds);
			//新排名
			HashMap  new_fc_count_data = getFC123(fc_count_list, "count");
			//fc_count_data	非车成单数排名 {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }
			ConcurrentHashMap fc_count_data = ApplicationContext.fc_count_data;
			changed = check_changed(fc_count_data, new_fc_count_data, "count") || changed;
		}
		{//非车保费
			String[]kinds={"jyx"};
			//保费排名列表 [{premium,user_id,name}]
			List<HashMap> fc_premium_list = cmStandBookDao.premium_user_order(under_write_date_s, under_write_date_e,kinds);
			//新排名
			HashMap  new_fc_premium_data = getFC123(fc_premium_list, "premium");
			//fc_premium_data	非车保费排名  {first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
			ConcurrentHashMap fc_premium_data = ApplicationContext.fc_premium_data;
			changed = check_changed(fc_premium_data, new_fc_premium_data, "premium") || changed;
		}
		if(changed){//排名有变动
			webSocket.sendEnent("day_honor_change", name, "日榜排名变动", day_honor_change_times);
		}
	}
	
	
	
	/**
	 * 	获取第一 第二 第三 
	 * @param listData 数据集  按照比较因子倒叙 [{count/premium,user_id,name}] 
	 * @param type count 单数  premium 保费 
	 * @return   {first:[{user_id,name,count}],second:[{user_id,name,count}],third:[{user_id,name,count}] }/{first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
	 */
	private static HashMap getSX123(List<HashMap> listData,String type)  {
		List first = new ArrayList();
		List second = new ArrayList();
		List third = new ArrayList();

		Collections.sort(listData, new Comparator<HashMap>() { 
			@Override
			public int compare(HashMap o1, HashMap o2) {
				if(type.equals("count")) {//对比成单数 优先count排序  count相同则premium
					Integer count1 = Integer.valueOf(o1.get("count").toString());
					Integer count2 = Integer.valueOf(o2.get("count").toString());
					if(count1 > count2){
						return -1;
					}else if(count1.intValue() == count2.intValue()) {
						Double premium1 = (Double)o1.get("premium");
						Double premium2 = (Double)o2.get("premium");
						if(premium1 > premium2) {
							return -1;
						}else {
							return 1;
						}
					}else {
						return 1;
					}
				}else {// 对比保费 premium排序
					Double premium1 = (Double)o1.get("premium");
					Double premium2 = (Double)o2.get("premium");
					if(premium1 > premium2) {
						return -1;
					}else {
						return 1;
					}
				}
			}
		});
		
		
		HashMap result = new HashMap();
		if(listData.get(0)!=null) {
			first.add(listData.get(0));
		}
		if(listData.size() >=2 && listData.get(1)!=null) {
			second.add(listData.get(1));
		}
		if(listData.size() >=3 && listData.get(2)!=null) {
			third.add(listData.get(2));
		}
		result.put("first",first);
		result.put("second",second);
		result.put("third",third);
		return result;
	}
	

	/**
	 * 	获取第一 第二 第三 
	 * @param listData 数据集  按照比较因子倒叙 [{count/premium,user_id,name}] 
	 * @param type count 单数  premium 保费 
	 * @return   {first:[{user_id,name,count}],second:[{user_id,name,count}],third:[{user_id,name,count}] }/{first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
	 */
	private static HashMap getFC123(List<HashMap> listData,String type)  {
		List first = new ArrayList();
		List second = new ArrayList();
		List third = new ArrayList();
		boolean ok_1 = false;
		boolean ok_2 = false;
		boolean ok_3 = false;
		if(listData.size() > 0) 
		{
			HashMap o_map = listData.get(0);
			Double tmp_count = null;
			if(type.equals("count")) {//比数量
				tmp_count = Double.valueOf(o_map.get("count").toString());
			}else {//比金额
				tmp_count = (Double)o_map.get("premium");
			}
			for (HashMap map : listData) {
				Double count = null;
				if(type.equals("count")) {//比数量
					count = Double.valueOf(map.get("count").toString());
				}else {//比金额
					count = (Double)map.get("premium");
				}
				if(!ok_1){
					if(tmp_count.equals(count)) {
						first.add(map);
						continue;
					}else {
						ok_1 = true;
						tmp_count =  count;
					}
				}
				if(!ok_2){
					if(tmp_count.equals(count)) {
						second.add(map);
						continue;
					}else {
						ok_2 = true;
						tmp_count =  count;
					}
				}
				
				if(!ok_3){
					if(tmp_count.equals(count)) {
						third.add(map);
						continue;
					}else {
						ok_3 = true;
						tmp_count =  count;
						break;
					}
				}
			}
		}
		
		HashMap result = new HashMap();
		result.put("first",first);
		result.put("second",second);
		result.put("third",third);
		return result;
	}
	
	/**
	 * 	 比较是否变动  并且修改变动后的数据
	 * @param cmap  {first:[{user_id,name,policy_count}],second:[{user_id,name,policy_count}],third:[{user_id,name,policy_count}] }/{first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
	 * @param map  {first:[{user_id,name,count}],second:[{user_id,name,count}],third:[{user_id,name,count}] }/{first:[{user_id,name,premium}],second:[{user_id,name,premium}],third:[{user_id,name,premium}] }
	 * @param type count 单数  premium 保费
	 * @return true变动/false未变动
	 */
	public static boolean check_changed(Map cmap,Map map,String type) {
		List cfirst = cmap.get("first") == null ? new ArrayList() : (List)cmap.get("first");
		cmap.put("first",cfirst);
		List first =  map.get("first") == null ? new ArrayList() : (List)map.get("first");
		
		List csecond = cmap.get("second") == null ? new ArrayList() : (List)cmap.get("second");
		cmap.put("second",csecond);
		List second = map.get("second") == null ? new ArrayList() : (List)map.get("second");
		
		List cthird = cmap.get("third") == null ? new ArrayList() : (List)cmap.get("third");
		cmap.put("third",cthird);
		List third = map.get("third") == null ? new ArrayList() : (List)map.get("third");
		
		boolean changed = false;
		changed = compare_modify(cfirst,first,type) || changed;
		changed = compare_modify(csecond,second,type) || changed;
		changed = compare_modify(cthird,third,type) || changed;
		
		return changed;
	}
	
	/**
	 * 	比较和修改排名数据
	 * @param cList [{user_id,name,policy_count}]/[{user_id,name,premium}]
	 * @param list [{user_id,name,count}]/[{user_id,name,premium}]
	 * @param type count 单数  premium 保费
	 * @return true变动/false未变动
	 */
	public static boolean compare_modify(List<HashMap> cList,List<HashMap> list,String type) {
		boolean changed = false;
		if(cList.size() != list.size()) {
			changed = true;
		}
		for (int i = 0; i < list.size(); i++) {
			HashMap cmap = new HashMap();
//			HashMap cmap = cList.get(0);
			HashMap map = list.get(i);
			String userid = map.get("user_id").toString();
			String cUserid = null;
			if(cList.size() > 0 && !cList.isEmpty())  {
				for(int j = 0;j < cList.size();j++) {
					cmap = cList.get(j);
					cUserid = cmap.get("user_id").toString();
				}
			}
			
			if(!userid.equals(cUserid)) {
				changed = true;
			}
			
			if(type.equals("count")) {//数量
				map.put("policy_count", map.get("count"));
			}
		}
		cList.clear();
		cList.addAll(list);
		//cList = list;
		return changed;
	}
	
	
	/**
	 * 	员工保费汇总  按照员工分组
	 * @param under_write_date_s 承保日期起期
	 * @param under_write_date_e 承保日期止期
	 * @param stat_type M按月 D按天
	 * @param kind[]  险种类型数组 
	 * @param user_id[] 员工编码数组
	 * @return {retCode,retMsg, list:[{under_write_date,user_list:[{user_id,user_name,count,premium}]}]}
	 */
	public JSONObject user_premium_query(String under_write_date_s,String under_write_date_e,String stat_type,String[] kind,String[] user_id) {
		JSONObject retJson = new JSONObject();
		//user_id,name,underwrite_date,count,premium
		stat_type=stat_type.toUpperCase();
		List<HashMap> dbList = statDao.stat_user_data(under_write_date_s, under_write_date_e, kind, user_id, stat_type);
		String tmp_date = null;
		List<HashMap> list = new ArrayList<HashMap>();
		List<HashMap> user_list = null;
		for (HashMap hashMap : dbList) {
			String date = hashMap.get("under_write_date").toString();
			if(date.equals(tmp_date)) {
				user_list.add(hashMap);
			}else {
				tmp_date = date;
				user_list = new ArrayList<HashMap>();
				user_list.add(hashMap);
				HashMap map = new HashMap();
				map.put("under_write_date",date);
				map.put("user_list",user_list);
				list.add(map);
			}
		}
		List<HashMap> users = cmTeamUserDao.queryMerberName(null,user_id);
		List<String> allDate = getAllDates(under_write_date_s, under_write_date_e, stat_type);
		if(!allDate.isEmpty() && !list.isEmpty()) {
			
			for (int i = 0; i < allDate.size(); i++) {
				String under_write_date = allDate.get(i);
				//List<HashMap> user_list1 = new ArrayList<HashMap>();
				HashMap dataMap = null;
				if(i < list.size()) {
					dataMap = list.get(i);
				}
				String db_under_write_date = null;
				if(dataMap != null && !dataMap.isEmpty()) {
					db_under_write_date = dataMap.get("under_write_date").toString();
				}
				if(!under_write_date.equals(db_under_write_date)) {
					dataMap = new HashMap();
					dataMap.put("under_write_date",under_write_date);
					dataMap.put("user_list",new ArrayList());
					list.add(i, dataMap);
				}
			}
		}
		for (HashMap hashMap : list) {
			
			List<HashMap> user_list1 = hashMap.get("user_list") == null ? null : (List<HashMap>)hashMap.get("user_list");
			fillUser(users, user_list1);
		}
		retJson.put("list",list);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
	/**
	 * 	补充用户信息   当某天的数据没有某个人的数据时 把人补录进去 0单 0保费
	 * @param users [{user_id,name}]
	 * @param data_list  [{user_id,name,count,premium}]
	 */
	private static void fillUser(List<HashMap> allUser,List<HashMap> data_list) {
		List<HashMap> tmpList = new ArrayList<HashMap>();
		for (HashMap user : allUser) {
			String user_id = user.get("user_id").toString();
			boolean exist = false;
			for (HashMap data : data_list) {
				if(user_id.equals(data.get("user_id"))) {
					exist = true;
					break;
				}
			}
			if(!exist) {//不存在 添加进去
				String name = user.get("name").toString();
				HashMap newUser = new HashMap();
				newUser.put("user_id",user_id);
				newUser.put("name",name);
				newUser.put("count",0);
				newUser.put("premium",0);
				tmpList.add(newUser);
			}
			
		}
		data_list.addAll(tmpList);
	}
	
	
	/**
	 * 	获取两个日期之间的天数/月数
	 * @param s_date 开始日期
	 * @param e_date 结束日期
	 * @param stat_type M按月 D按天
	 * @return 
	 */
	public static List<String> getAllDates(String s_date,String e_date,String stat_type){
		if(s_date == null ){
			s_date = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd");
		}
		if(e_date == null ){
			e_date = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyy-MM-dd");
		}
		
		List<String> list = new ArrayList<String>();
		if("D".equals(stat_type)) {//按天计算
			if(s_date.equals(e_date)) {//起止日期相同则直接返回一个日期
				list.add(s_date);
				return list;
			}
			String dateFormat = "yyyy-MM-dd";
			Date date1= DateTimeUtil.str2Date(s_date, dateFormat);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			Date date2= DateTimeUtil.str2Date(e_date, dateFormat);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date2);
			
			while(c1.getTimeInMillis() <= c2.getTimeInMillis()  ) {//开始日期小于结束日期 加一天
				String date_add = DateTimeUtil.date2Str(c1.getTimeInMillis(), dateFormat);
				list.add(date_add);
				c1.add(Calendar.DAY_OF_YEAR, 1);
			}
		}else if("M".equals(stat_type)){//按月计算
			s_date = s_date.substring(0,7);
			e_date = e_date.substring(0,7);
			if(s_date.equals(e_date)) {//起止日期相同则直接返回一个日期
				list.add(s_date);
				return list;
			}
			String dateFormat = "yyyy-MM";
			Date date1= DateTimeUtil.str2Date(s_date, dateFormat);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			Date date2= DateTimeUtil.str2Date(e_date, dateFormat);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date2);
			while(c1.getTimeInMillis() <= c2.getTimeInMillis()  ) {//开始日期小于结束日期 加一天
				String date_add = DateTimeUtil.date2Str(c1.getTimeInMillis(), dateFormat);
				list.add(date_add);
				c1.add(Calendar.MONTH, 1);
			}
		}
		return list;
	}
	
	/**
	 *	周期业绩统计查询  按照保费倒叙  
	 * 	@param under_write_date_s yyyy-MM-dd
	 *  @param under_write_date_e 
	 *  @param kind
	 *  @param user_id
	 *  @return {retCode,retMsg, list:[{user_id,user_name,count,premium}]}
	 */
	public JSONObject premium_by_date(String under_write_date_s,String under_write_date_e,String[] kind,String[] user_id) {
		JSONObject retJson = new JSONObject();
		//user_id,name,underwrite_date,count,premium
		List<HashMap> dbList = statDao.premium_by_date(under_write_date_s, under_write_date_e, kind, user_id);
		List<HashMap> users = cmTeamUserDao.queryMerberName(null,user_id);
		HashMap map=new HashMap();
		map.put("list",dbList);
		List<HashMap> listUser = new ArrayList<HashMap>();
		listUser.add(map);
		for (HashMap hashMap : listUser) {
			List<HashMap> db_list = hashMap.get("list") == null ? null : (List<HashMap>)hashMap.get("list");
			fillUser(users, db_list);
		}
		retJson.put("list",dbList);
		retJson.put("retCode", "success");
		retJson.put("retMsg", "成功");
		return retJson;
	}
	
//	public static void main(String[] args) {
//		List<String> allDate = getAllDates("2020-10-28", "2020-12-05", "M");
//		System.out.println(allDate);
//	}

	
}
