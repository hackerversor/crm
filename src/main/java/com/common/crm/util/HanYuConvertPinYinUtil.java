package com.common.crm.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanYuConvertPinYinUtil {
	
	public static String hanyu2Pinyin(char hanyu) {
		String reg = "[\u4e00-\u9fa5]";
		StringBuffer sb = new StringBuffer();
		if(!String.valueOf(hanyu).matches(reg)) {
			return null;
		}
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			String ss[] = PinyinHelper.toHanyuPinyinStringArray(hanyu, outputFormat);
			sb.append(ss[0]);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return sb.toString();
		
		
	}
	public static void main(String[] args) {
		System.out.println( hanyu2Pinyin('样') );
	}
}
