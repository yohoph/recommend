package com.zhiyun.bigdata.framework.utils;

import java.util.Collection;

public class CollectionUtils {
	
	public static boolean isEmpty(Collection<?> collections){
		if(collections == null) return true;
		if(collections.isEmpty()) return true;
		return false;
	}

}
