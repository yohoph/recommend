package com.zhiyun.bigdata.recommend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 学生角色推荐
 * @author yehao
 *
 */
@Controller
@RequestMapping(value = {"/student"})
public class StudentController {
	
	
	/**
	 * 课堂课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/roomCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String roomCourse(String userId){
		return null;
	}
	
	/**
	 * 课外课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/outDoorCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String outDoorCourse(String userId){
		return null;
	}
	
	/**
	 * 免费课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/freeCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String freeCourse(String userId){
		return null;
	}
	
	/**
	 * 付费课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/costCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String costCourse(String userId){
		return null;
	}
	
	/**
	 * 搜索结果课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/searchedCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String searchedCourse(String userId){
		return null;
	}
	
	/**
	 * 同学参与的课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/participateCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String participateCourse(String userId){
		return null;
	}
	
	/**
	 * 平台自主课程推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/defaultCourse", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String defaultCourse(String userId){
		return null;
	}
	
	/**
	 * 默认教师推荐
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/recommendTeacher", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	@ResponseBody
	public String recommendTeacher(String userId){
		return null;
	}

}
