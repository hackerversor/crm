package com.common.crm.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class HttpUtil {
	@Autowired
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(com.common.crm.util.HttpUtil.class);
	
	
	/**
	 * 	发送get请求
	 * @param url
	 * @param params
	 * @return 如果为null表示发送状态未知
	 */
	public static String httpGet(String url, HashMap params)  {
		HttpClient client = HttpClients.createDefault();
		url = url+"?"+urlencode(params);
		HttpGet httpGet = new HttpGet(url);
		String ss = null;
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			ss = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ss;
	}
	
	/**
	 * 	发送httppost请求
	 * @param url 
	 * @param params
	 * @return 如果为null表示发送状态未知
	 */
	public static String httpPost(String url, HashMap<String,String> params) {
		HttpClient httpClient =HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator iterator = params.entrySet().iterator();
		while(iterator.hasNext()) {
			 Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
			 list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
		}
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(list,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(entity);
		logger.info("请求{}接口的参数为{}",url,params);
		String result = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity= httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			logger.info("请求返回的数据{}",result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}  
		return result;
	}
	private static String urlencode(Map<String, String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return sb.toString();
	}
}
