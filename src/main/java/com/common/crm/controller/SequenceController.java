package com.common.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.crm.service.SequenceService;

@Controller
public class SequenceController {
	@Autowired
	private SequenceService sequenceService;
	
	/**
	 * 	创建序列
	 * @param seq_code	序列编码
	 * @param seq_len 总长度
	 * @param step_value 步进
	 * @param prefix 前缀
	 * @param d_type	日期格式
	 * @param seq_desc 描述
	 * @return {retCode.retMsg}
//	 */
//	@RequestMapping(path = "/crm/sys_sequence/create")
//	@ResponseBody
//	public String create_sequence(@RequestParam(name = "seq_code",required = true)String seq_code,
//			@RequestParam(name = "seq_len",required = true)Integer seq_len, 
//			@RequestParam(name = "step_value",required = false)Integer step_value,
//			@RequestParam(name = "prefix",required = false)String prefix,
//			@RequestParam(name = "d_type",required = false)String d_type,
//			@RequestParam(name = "seq_desc",required = true)String seq_desc) {
//		JSONObject retJson = sequenceService.create_sequence(seq_code, seq_len, step_value, prefix, d_type, seq_desc);
//		return retJson.toJSONString();
//	}
//	
	@RequestMapping(path = "/crm/sys_sequence/get")
	@ResponseBody
	public String get_sequence(@RequestParam(name = "seq_code",required = true)String seq_code) {
		return sequenceService.getNewSequence(seq_code);
	}
	
	
}
