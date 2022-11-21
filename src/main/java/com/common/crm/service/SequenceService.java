package com.common.crm.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.crm.dao.SequenceDao;
import com.common.crm.util.DateTimeUtil;

@Service
public class SequenceService {
	
	@Autowired
	private SequenceDao sequenceDao;

	/**
	 *	获取一个最新的序列值
	 * @param seq_code
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String getNewSequence(String seq_code) {
		String sequence ="";
		HashMap map = sequenceDao.getSequence(seq_code);
		String date_str = DateTimeUtil.date2Str(System.currentTimeMillis(), "yyyyMMdd");
		String prefix = map.get("prefix")  == null ? ""  : map.get("prefix").toString();
		Integer max_length = (Integer) map.get("max_length");
		Integer value_max_len = max_length - date_str.length() - prefix.length();
		Long value_max = Long.valueOf("999999999999999999".substring(0,value_max_len));
		Long seq_value = Long.valueOf( map.get("seq_value").toString());
		Long new_nvalue = seq_value+1;
		if(new_nvalue > value_max) {
			new_nvalue = 0L;
		}
		sequenceDao.updateSequence(seq_code, new_nvalue);
		String zero_str = "000000000000000000";
		Integer zero_num = max_length - 8 - new_nvalue.toString().length() - prefix.length();
		zero_str = zero_str.substring(0,zero_num);
		sequence = prefix+date_str + zero_str + new_nvalue;
		return sequence;
	}
}
