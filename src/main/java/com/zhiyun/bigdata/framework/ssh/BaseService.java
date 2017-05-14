/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014年9月3日
 * Id: BaseService.java,v 1.0 2014年9月3日 下午5:25:03 admin
 */
package com.zhiyun.bigdata.framework.ssh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName BaseService
 * @author admin
 * @date 2014年9月3日 下午5:25:03
 * @Description 服务层接口抽象实现
 */
public abstract class BaseService {
	
	/**
	 * 日志服务对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

}
