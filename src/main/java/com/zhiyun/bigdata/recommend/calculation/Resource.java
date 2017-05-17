package com.zhiyun.bigdata.recommend.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Resource<T> extends Observable implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	protected CalculationManage calculationManage;
	
	protected String id;
	
	protected List<T> list;
	
	@Override
	public void run() {
		init();
		calculationManage.updateResource(id, CalculationStatus.DOING);
		if(list != null && list.size() > 0){ //ÅÐ¿Õ
			List<Result> results = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				logger.info("id:" + id + " execute data current:" + i + " total:" + list.size());
				Result result = execute(list.get(i));
				if(result == null){
					result = new Result();
					result.setBody(list.get(i).toString());
					result.setWeight(0);
				}
				results.add(result);
			}
			writeResult(results);
		}
		
	}
	
	public void init(){
		if(id == null){
			this.id = getId();
		}
		if(calculationManage == null){
			this.calculationManage = getCalculationManage();
		}
		if(list == null){
			this.list = getList();
		}
		check();
	}
	
	/**
	 * ¼ì²é½á¹û
	 * @return
	 */
	private boolean check(){
		if(id == null) throw new NullPointerException("id is null");
		if(calculationManage == null) throw new NullPointerException("calculationManage is null");
		if(list == null) throw new NullPointerException("resource entities is null");
		return true;
	}
	
	public abstract Result execute(T t);
	
	public abstract CalculationManage getCalculationManage();

	public abstract String getId();

	public abstract List<T> getList();

	public void writeResult(List<Result> results){
		calculationManage.addResults(results);
		calculationManage.updateResource(id, CalculationStatus.END);
	}

}
