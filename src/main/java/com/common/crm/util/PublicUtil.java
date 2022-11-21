package com.common.crm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qifan on 2018/7/25.
 */
public class PublicUtil {

    public static boolean isNull(List list){
        if(null == list || list.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNull(HashMap map){
        if(null == map || map.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNull(String str){
        if(null == str || "".equals(str)){
            return true;
        }
        return false;
    }

    public static String doubleToStr(Double doubles,int digits){
    	BigDecimal bd = new BigDecimal(doubles);
    	BigDecimal setScale = bd.setScale(digits, RoundingMode.DOWN);
    	return setScale.toPlainString();
    }
	public static void main(String[] args) {
		double d=1252.2575;
		
		System.out.println(doubleToStr(d,2));
	}
}
