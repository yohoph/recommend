package com.zhiyun.bigdata.recommend.service;

import com.zhiyun.bigdata.framework.utils.Page;

public interface RecommendService {
	
	public Page getTeachers(Page page , String userId);

}
