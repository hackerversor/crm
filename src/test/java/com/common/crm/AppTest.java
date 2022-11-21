package com.common.crm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		String ss = "{\"list\":[{\"under_write_date\":\"2020-10-06\",\"user_list\":[{\"premium\":7000.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":1,\"under_write_date\":\"2020-10-06\"},{\"premium\":6500.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":1,\"under_write_date\":\"2020-10-06\"},{\"premium\":55000.0,\"user_id\":\"CMU20201120000000139\",\"name\":\"永远\",\"count\":5,\"under_write_date\":\"2020-10-06\"},{\"premium\":20000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":2,\"under_write_date\":\"2020-10-06\"}]},{\"under_write_date\":\"2020-10-07\",\"user_list\":[{\"premium\":12200.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":2,\"under_write_date\":\"2020-10-07\"},{\"premium\":6500.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":1,\"under_write_date\":\"2020-10-07\"},{\"premium\":17000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":3,\"under_write_date\":\"2020-10-07\"}]},{\"under_write_date\":\"2020-10-08\",\"user_list\":[{\"premium\":5200.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":1,\"under_write_date\":\"2020-10-08\"},{\"premium\":16500.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":3,\"under_write_date\":\"2020-10-08\"},{\"premium\":15000.0,\"user_id\":\"CMU20201120000000139\",\"name\":\"永远\",\"count\":2,\"under_write_date\":\"2020-10-08\"},{\"premium\":22000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":3,\"under_write_date\":\"2020-10-08\"}]},{\"under_write_date\":\"2020-10-09\",\"user_list\":[{\"premium\":10400.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":2,\"under_write_date\":\"2020-10-09\"},{\"premium\":5000.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":1,\"under_write_date\":\"2020-10-09\"},{\"premium\":5000.0,\"user_id\":\"CMU20201120000000139\",\"name\":\"永远\",\"count\":1,\"under_write_date\":\"2020-10-09\"},{\"premium\":22000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":3,\"under_write_date\":\"2020-10-09\"}]},{\"under_write_date\":\"2020-11-03\",\"user_list\":[{\"premium\":6000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":2,\"under_write_date\":\"2020-11-03\"}]},{\"under_write_date\":\"2020-11-07\",\"user_list\":[{\"premium\":8000.0,\"user_id\":\"CMU20201012000000109\",\"name\":\"开发\",\"count\":3,\"under_write_date\":\"2020-11-07\"},{\"premium\":12000.0,\"user_id\":\"CMU20201012000000112\",\"name\":\"小辛\",\"count\":5,\"under_write_date\":\"2020-11-07\"},{\"premium\":1000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-07\"}]},{\"under_write_date\":\"2020-11-08\",\"user_list\":[{\"premium\":3000.0,\"user_id\":\"CMU20201012000000109\",\"name\":\"开发\",\"count\":1,\"under_write_date\":\"2020-11-08\"}]},{\"under_write_date\":\"2020-11-09\",\"user_list\":[{\"premium\":6000.0,\"user_id\":\"CMU20201012000000108\",\"name\":\"测试\",\"count\":3,\"under_write_date\":\"2020-11-09\"},{\"premium\":1000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-09\"},{\"premium\":5200.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":1,\"under_write_date\":\"2020-11-09\"},{\"premium\":5000.0,\"user_id\":\"CMU20201120000000139\",\"name\":\"永远\",\"count\":1,\"under_write_date\":\"2020-11-09\"}]},{\"under_write_date\":\"2020-11-10\",\"user_list\":[{\"premium\":3000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-10\"},{\"premium\":8000.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":1,\"under_write_date\":\"2020-11-10\"}]},{\"under_write_date\":\"2020-11-11\",\"user_list\":[{\"premium\":2000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-11\"}]},{\"under_write_date\":\"2020-11-12\",\"user_list\":[{\"premium\":2000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-12\"}]},{\"under_write_date\":\"2020-11-13\",\"user_list\":[{\"premium\":2000.0,\"user_id\":\"CMU20201106000000123\",\"name\":\"洋洋\",\"count\":1,\"under_write_date\":\"2020-11-13\"}]},{\"under_write_date\":\"2020-11-18\",\"user_list\":[{\"premium\":9200.0,\"user_id\":\"CMU20201120000000137\",\"name\":\"缘分\",\"count\":2,\"under_write_date\":\"2020-11-18\"}]},{\"under_write_date\":\"2020-11-19\",\"user_list\":[{\"premium\":5000.0,\"user_id\":\"CMU20201120000000139\",\"name\":\"永远\",\"count\":1,\"under_write_date\":\"2020-11-19\"},{\"premium\":10000.0,\"user_id\":\"CMU20201120000000141\",\"name\":\"相伴\",\"count\":2,\"under_write_date\":\"2020-11-19\"}]},{\"under_write_date\":\"2020-11-20\",\"user_list\":[{\"premium\":8000.0,\"user_id\":\"CMU20201120000000138\",\"name\":\"尘心\",\"count\":1,\"under_write_date\":\"2020-11-20\"}]}],\"retCode\":\"success\",\"retMsg\":\"成功\"}";
		JSONObject jj = JSONObject.parseObject(ss);
		JSONArray aa = jj.getJSONArray("list");
		for (int i = 0; i < aa.size(); i++) {
			JSONObject dayobj = aa.getJSONObject(i);
			JSONArray users = dayobj.getJSONArray("user_list");
			for (int j = 0; j < users.size(); j++) {
				JSONObject data = users.getJSONObject(j);
				String premium = data.getString("premium");
				String user_id = data.getString("user_id");
				String name = data.getString("name");
				String under_write_date = dayobj.getString("under_write_date");
				System.out.println(under_write_date+"\t"+user_id+"\t"+premium+"\t"+name);
				
			}
		}
		//System.out.println(jj);
	}
}
