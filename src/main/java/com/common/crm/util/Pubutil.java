package com.common.crm.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;

public class Pubutil {

	 static final String ORDER = "ORDER";
	 static final String TRAN = "TRAN";
	/**
	 * 获取主机ip
	 * @return
	 * @throws IOException
	 */
	public static String getHostIp() throws IOException {
		InetAddress addr = InetAddress.getLocalHost();  
		String ip = addr.getHostAddress();
		return ip;
	}
	/**
	 * MD5
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
	/**
	 * HMACSHA256
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
	/**
	 * status_msg
	 * @param type
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public static String status_msg(String type, String status) {
		String status_msg = "";
		if (TRAN.equals(type)){
         	if("succ".equals(status)) status_msg= "成功";
			if("running".equals(status)) status_msg= "充值中";
			if("fail".equals(status)) status_msg= "失败已冲正";
			else status_msg= "未知";
		}
		if (ORDER.equals(type)){
			if("wait_pay".equals(status)) status_msg= "卖家提交，等待买家付款";
			if("wait_pay_result".equals(status)) status_msg= "买家确认并支付";
			if("success".equals(status)) status_msg= "成功";
			if("pay_failed".equals(status)) status_msg= "支付失败";
			else status_msg= "未知";
		}
		return status_msg;
	}
}
