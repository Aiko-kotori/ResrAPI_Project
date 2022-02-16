package com.subjectdb.mongodb.model;

public class Content {

	// id will be used for primary key in MongoDB
	// We could use ObjectId, but I am keeping it
	// independent of MongoDB API classes
	private String id;

	private String temp;

	private String group;

	private String time;

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
