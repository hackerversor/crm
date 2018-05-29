package com.example.demo.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloSpringBoot {
	
	@RequestMapping("/index")
	public String index(/*Map<String,Object> map*/){
//		map.put("name", "Andy");
		return "index";
	}
}
