package com.cssiot.cssutil.common.data;

public class ValueNameModel {
	private String value;	//id
	private String name;	//名称
	
	public ValueNameModel() {
		super();
	}

	public ValueNameModel(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}