package com.zhiyun.bigdata.recommend.calculation;

public class CalculationConfig {
	
	/**
	 * 最小线程数(最大推荐数)
	 */
	private int minNum;
	
	/**
	 * 每线程并行计算最大条目数
	 */
	private int recNum;
	
	/**
	 * 超时时长（秒）
	 */
	private long timeOut;
	
	public CalculationConfig() {
		super();
	}

	public CalculationConfig(int minNum, int recNum) {
		super();
		this.minNum = minNum;
		this.recNum = recNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public int getRecNum() {
		return recNum;
	}

	public void setRecNum(int recNum) {
		this.recNum = recNum;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	
}
