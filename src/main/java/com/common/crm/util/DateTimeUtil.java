package com.common.crm.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    
    /**
     * 格式化时间
     * @param time
     * @param fromat
                "yyyy-MM-dd",              
                "yyyy-MM-dd HH:mm:ss",      
                "yyyy/MM/dd HH:mm:ss",    
                "yyyy年MM月dd日HH时mm分ss秒",     
                "yyyy/MM/dd",              
                "yy-MM-dd",                 
                "yy/MM/dd",                
                "yyyy年MM月dd日",            
                "HH:mm:ss",                
                "yyyyMMddHHmmss",         
                "yyyyMM",                  
                "yyyyMMdd",                
                "yyyy.MM.dd",             
                "yy.MM.dd",               
                "yyyy-MM-dd'T'HH:mm:ssZ"  
     * @return
     */
    public static String  date2Str(Long time,String fromat){
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(fromat);
        return dateFormat.format(date);
    }
    /**
     * String to date
     * @param dateStr
     * @return date
     */
    public static Date str2Date(String dateStr,String fromat){
    	SimpleDateFormat dateFormat = new SimpleDateFormat(fromat);
    	Date date = null;
    	try {
    		date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
		}
    	return date;
    }
    /**
     * compare date1 with date2
     * @param date1
     * @param date2
     * @return bigger date
     */
    public static Date compareDate(Date date1,Date date2){
    	return date1.getTime() > date2.getTime() ? date1 : date2 ;
    }
    /**
     * compare datestr 
     * @param dateStr1
     * @param dateStr2
     * @param format
     * @return return bigger datestr
     */
    public static String compareDateStr(String dateStr1,String dateStr2,String format){
        Date date1 = str2Date(dateStr1, format);
        Date date2 = str2Date(dateStr2, format);
        if (date1 != null && date2 != null ) {
            return date1.getTime() > date2.getTime() ? dateStr1 : dateStr2;
        }
        else
            return null;
    }
    
    /**
     * compare datestr 
     * @param dateStr1
     * @param dateStr2
     * @param format
     * @return return boolean 
     */
    public static boolean compareDateString(String dateStr1,String dateStr2,String format){
        Date date1 = str2Date(dateStr1, format);
        Date date2 = str2Date(dateStr2, format);
        if (date1 != null && date2 != null ) {
            if(date1.getTime() > date2.getTime()) {
            	return true;
            }else
                return false;
        }
		return false;
        
    }
    
    public static void main(String[] args) {
    	System.out.println(compareDateStr("20170102","20170102","yyyyMMdd"));
    }
}
