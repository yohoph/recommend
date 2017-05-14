package com.zhiyun.bigdata.recommend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/recommend"})
public class RecommendController {
	
	@RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String hello(String hello){
		
		return "hi";
	}
	
	
	

}
