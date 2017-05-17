package com.zhiyun.bigdata.recommend.calculation;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;

import com.zhiyun.bigdata.framework.ssh.SpringService;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.pojo.Teacher;
import com.zhiyun.bigdata.recommend.service.RecommendService;

public class TeacherResource extends Resource<Teacher> {
	
	private String userId;
	
	private RecommendService recommendService;
	
	public TeacherResource(String id,CalculationManage calculationManage,List<Teacher> list ,String userId){
		this.id = id;
		this.calculationManage = calculationManage;
		this.list = list;
		this.userId = userId;
	}

	@Override
	public Result execute(Teacher t) {
		int total = 0;
		recommendService = SpringService.getBean(RecommendService.class);
		int orderCount = countOrder(t, userId);
		total += orderCount;
		Result result = new Result();
		result.setBody(t.getTeacherId());
		result.setWeight(total);
		return result;
	}
	
	/**
	 * ���㶩����Ȩֵ
	 * @param teacher
	 * @param userId
	 * @return
	 */
	public int countOrder(Teacher teacher , String userId){
		int gradeCount = 0; //�꼶Ȩ��
		int subjectCount = 0; //��ĿȨ��
		List<Order> orders = recommendService.getOrder(userId);
		if(orders != null && !orders.isEmpty()){
			for (Order order : orders) {
				Course course = recommendService.getCourse(order.getCourseId());
				if(course != null){
					if(teacher.getGradeId().equals(course.getGradeId())){
						if(order.getCompleteTime() != null){
							if(gradeCount < 8){//����8��Ȩ�صģ����꼶Ȩ����Ϊ6
								gradeCount = 8;
							}
						} else {
							if(gradeCount < 6){ //����6��Ȩ�صģ��ѿ�ĿȨ����Ϊ6
								gradeCount = 6;
							}
						}
					}
					if(teacher.getSubjectId().equals(course.getSubjectId())){
						if(order.getCompleteTime() != null){
							if(subjectCount < 6){
								subjectCount = 6;
							}
						} else {
							if(subjectCount < 4){ //����6��Ȩ�صģ����꼶Ȩ����Ϊ6
								subjectCount = 4;
							}
						}
					}
				}
				
			}
		}
		return gradeCount + subjectCount;
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
