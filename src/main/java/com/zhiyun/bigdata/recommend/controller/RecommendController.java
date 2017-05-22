package com.zhiyun.bigdata.recommend.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhiyun.bigdata.framework.ssh.StringUtil;
import com.zhiyun.bigdata.recommend.calculation.CalculationManage;
import com.zhiyun.bigdata.recommend.calculation.Result;

@Controller
@RequestMapping(value = {"/recommend"})
public class RecommendController {
	
	@Autowired
	private CalculationManage calculationManage;
	
	@RequestMapping(value = "/recommend", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String recommend(String userId){
		if(StringUtils.isEmpty(userId)){
			return StringUtil.getFaildResult("«ÎÃÓ»ÎuserId");
		}
		List<Result> result = calculationManage.doCalculation(userId);
		return StringUtil.parseJsonNoExpose(result);
	}
	
	
	

}
