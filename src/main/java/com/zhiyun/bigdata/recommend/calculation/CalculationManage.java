package com.zhiyun.bigdata.recommend.calculation;

import java.util.List;
import java.util.Vector;

import com.zhiyun.bigdata.framework.ssh.SpringService;
import com.zhiyun.bigdata.framework.ssh.StringUtil;
import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.pojo.Teacher;
import com.zhiyun.bigdata.recommend.service.RecommendService;

public class CalculationManage {
	
	RecommendService recommendService = SpringService.getBean(RecommendService.class);
	
	List<Result> results = new Vector<>(); //采用同步线程提交数据
	
	CalculationConfig config = null;
	
	public List<Result> doCalculation(String userId){
		Page page = new Page(config.getRecNum());
		page = recommendService.getTeachers(page, userId);
		if(getResult(page) == null){
			return null;
		}
		long totalPages = page.getTotalPages();
		long pageNo = page.getPageNo();
		if(totalPages > pageNo) {
			for (int i = 1; i <= totalPages; i++) {
				page.setPageNo(i);
				page = recommendService.getTeachers(page, userId);
				Resource<Teacher> resource = new TeacherResource(StringUtil.getUUID(), this, getResult(page));
				Thread thread = new Thread(resource);
				thread.start();
			}
		} else {
			Resource<Teacher> resource = new TeacherResource(StringUtil.getUUID(), this, getResult(page));
			Thread thread = new Thread(resource);
			thread.start();
		}
		//TODO 线程锁死等待执行结果
		
		
		return results;
	}
	
	private List<Teacher> getResult(Page page){
		if(page.getResultObj() != null && !page.getResultObj().isEmpty()){
			return page.getResultObj();
		}
		if(page.getResult() != null && !page.getResult().isEmpty()){
			return page.getResult();
		}
		return null;
	}
	
	
	public void addResults(List<Result> threadResults){
		results.addAll(threadResults);
	}
	
}
