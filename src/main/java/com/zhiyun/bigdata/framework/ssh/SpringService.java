/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014��5��8��
 * Id: SpringService.java,v 1.0 2014��5��8�� ����7:04:47 admin
 */
package com.zhiyun.bigdata.framework.ssh;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringService
 * @author admin
 * @date 2014��5��8�� ����7:04:47
 * @Description spring�ӿڹ�����
 */
@Component("springService")
public class SpringService implements ApplicationContextAware{

	private static ApplicationContext applicationContext;

	/** 
	 * @author yehao
	 * @date Nov 10, 2011
	 * @param arg0
	 * @throws BeansException
	 * @Description ����spring��IOC����
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringService.applicationContext = applicationContext;
	}
	
	/**
	 * @author yehao
	 * @date Nov 10, 2011
	 * @return IOC����
	 * @Description ���IOC����
	 */
	public static ApplicationContext getApplicationContext(){
		checkApplicationContext();
		return applicationContext;
	}
	
	/**
	 * @author yehao
	 * @date Nov 10, 2011
	 * @Description ��������Ƿ�Ϊ��
	 */
	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContextδע��,�뽫��������spring����");
		}
	}
	
	/**
	 * @author yehao
	 * @date Nov 10, 2011
	 * @Description ���������
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}
	
	/**
	 * @author yehao
	 * @date Nov 10, 2011
	 * @param <T>
	 * @param name
	 * @return
	 * @Description ���bean����
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * @author yehao
	 * @date Nov 10, 2011
	 * @param <T>
	 * @param clazz
	 * @return
	 * @Description ���bean����
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class clazz){
		checkApplicationContext();
		return (T) applicationContext.getBean(clazz);
	}
}
