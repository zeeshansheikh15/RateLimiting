package com.test.header.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {
	
	@Id
	private String stdId;
	private String name;
	private String belongClass;
	
	public String getStdId() {
		return stdId;
	}
	public void setStdId(String stdId) {
		this.stdId = stdId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBelongClass() {
		return belongClass;
	}
	public void setBelongClass(String belongClass) {
		this.belongClass = belongClass;
	}

}
