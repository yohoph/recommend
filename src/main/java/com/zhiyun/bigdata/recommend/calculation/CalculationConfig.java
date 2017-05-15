package com.zhiyun.bigdata.recommend.calculation;

public class CalculationConfig {
	
	/**
	 * ��С�߳���(����Ƽ���)
	 */
	private int minNum;
	
	/**
	 * ÿ�̲߳��м��������Ŀ��
	 */
	private int recNum;
	
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
	
}
