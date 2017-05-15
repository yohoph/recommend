package com.zhiyun.bigdata.recommend.calculation;

import java.util.List;

import com.zhiyun.bigdata.recommend.pojo.Teacher;

public class TeacherResource extends Resource<Teacher> {
	
	private String userId;
	
	public TeacherResource(String id,CalculationManage calculationManage,List<Teacher> list ,String userId){
		this.id = id;
		this.calculationManage = calculationManage;
		this.list = list;
		this.userId = userId;
	}

	@Override
	public Result execute(Teacher t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CalculationManage getCalculationManage() {
		return calculationManage;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Teacher> getList() {
		return list;
	}

}
