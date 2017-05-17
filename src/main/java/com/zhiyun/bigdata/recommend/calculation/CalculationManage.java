package com.zhiyun.bigdata.recommend.calculation;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhiyun.bigdata.framework.ssh.SpringService;
import com.zhiyun.bigdata.framework.ssh.StringUtil;
import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.pojo.Teacher;
import com.zhiyun.bigdata.recommend.service.RecommendService;

@Component
public class CalculationManage {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	RecommendService recommendService = SpringService.getBean(RecommendService.class);
	
	private List<Result> results = new Vector<>(); //采用同步线程提交数据
	
	private Map<String, CalculationStatus> resourceStatus = new Hashtable<>();
	
	private CalculationConfig config = new CalculationConfig();
	
	public void init(){
		results = new Vector<>(); //采用同步线程提交数据
		resourceStatus = new Hashtable<>();
		config = new CalculationConfig();
	}
	
	public List<Result> doCalculation(String userId){
		init();
		Page page = new Page(config.getRecNum());
		page = recommendService.getTeachers(page, userId);
		if(getResult(page) == null){
			return results;
		}
		long totalPages = page.getTotalPages();
		long pageNo = page.getPageNo();
		if(totalPages > pageNo) {
			for (int i = 1; i <= totalPages; i++) {
				page.setPageNo(i);
				page = recommendService.getTeachers(page, userId);
				String id = "ResourceId:" + StringUtil.getUUID();
				resourceStatus.put(id, CalculationStatus.READY);
				Resource<Teacher> resource = new TeacherResource(id, this, getResult(page),userId);
				Thread thread = new Thread(resource);
				thread.start();
			}
		} else {
			String id = "ResourceId:" + StringUtil.getUUID();
			resourceStatus.put(id, CalculationStatus.READY);
			Resource<Teacher> resource = new TeacherResource(id, this, getResult(page),userId);
			Thread thread = new Thread(resource);
			thread.start();
		}
		
		long currentTime = System.currentTimeMillis();
		//TODO 线程锁死等待执行结果
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long nowTime = System.currentTimeMillis();
			if ((nowTime - currentTime)/1000 > config.getTimeOut()) {
				logger.warn("system is time out, the data is " + userId );
				break;
			}
			Set<String> ids = resourceStatus.keySet();
			int count = 0;
			for (String id : ids) {
				CalculationStatus status = resourceStatus.get(id);
				if(status == CalculationStatus.END){
					count++;
				}
			}
			if(count >= ids.size()){
				break;
			} else {
				continue;
			}
			
		}
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
	
	
	/**
	 * 执行结果，更新数据
	 * @param threadResults
	 */
	public void addResults(List<Result> threadResults){
		results.addAll(threadResults);
	}
	
	/**
	 * 更新执行状态
	 * @param id
	 * @param status
	 */
	public void updateResource(String id , CalculationStatus status){
		resourceStatus.put(id, status);
	}
	
}
