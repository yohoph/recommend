package com.zhiyun.bigdata.recommend.calculation;

public class Result implements Comparable<Result> {
	
	private String body;
	
	private int weight;
	
	@Override
	public String toString() {
		return "Result [body=" + body + ", weight=" + weight + "]";
	}

	@Override
	public int compareTo(Result result) {
		return this.weight - result.getWeight();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
