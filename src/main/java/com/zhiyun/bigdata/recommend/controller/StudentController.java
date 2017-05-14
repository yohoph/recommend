package com.zhiyun.bigdata.recommend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ѧ����ɫ�Ƽ�
 * @author yehao
 *
 */
@Controller
@RequestMapping(value = {"/student"})
public class StudentController {
	
	
	/**
	 * ���ÿγ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/roomCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String roomCourse(String userId){
		return null;
	}
	
	/**
	 * ����γ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/outDoorCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String outDoorCourse(String userId){
		return null;
	}
	
	/**
	 * ��ѿγ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/freeCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String freeCourse(String userId){
		return null;
	}
	
	/**
	 * ���ѿγ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/costCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String costCourse(String userId){
		return null;
	}
	
	/**
	 * ��������γ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/searchedCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String searchedCourse(String userId){
		return null;
	}
	
	/**
	 * ͬѧ����Ŀγ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/participateCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String participateCourse(String userId){
		return null;
	}
	
	/**
	 * ƽ̨�����γ��Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/defaultCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String defaultCourse(String userId){
		return null;
	}
	
	/**
	 * Ĭ�Ͻ�ʦ�Ƽ�
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/recommendTeacher", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String recommendTeacher(String userId){
		return null;
	}

}
