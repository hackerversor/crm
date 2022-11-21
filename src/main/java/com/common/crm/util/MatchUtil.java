package com.common.crm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MatchUtil {
	/**
	 * 	判断是否全是汉字
	 * @param msg 字符串
	 * @return 是true/不是false
	 */
	public static boolean isHanzi(String msg) {
		String reg = "[\u4e00-\u9fa5]+";
		return msg.matches(reg);
	}

	/**
	 *   判断是否为整数  
	* @param str 传入的字符串  
	* @return 是整数返回true,否则返回false  
	*/  
	public static boolean isInteger(String str) {    
	    Pattern pattern = Pattern.compile("^[\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	}  
	
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}  
	
	/**
	 *   判断是否为整数  
	* @param str 传入的字符串  
	* @return 是整数返回true,否则返回false  
	*    如果不需要四舍五入，可以使用RoundingMode.DOWN
	*/  
	public static double formatDouble2(double d,int digit) {
	    BigDecimal bg = new BigDecimal(d).setScale(digit, RoundingMode.HALF_UP);
	    return bg.doubleValue();
	}
}
