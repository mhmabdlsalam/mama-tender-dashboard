package com.arrowsdashboard.mamatenderdash.auth.Models.Request;

import com.google.gson.annotations.SerializedName;

public class Post{

	public Post(Notification notification, String to) {
		this.notification = notification;
		this.to = to;
		this.priority = "high";
	}

	@SerializedName("notification")
	private Notification notification;

	@SerializedName("data")
	private Data data;

	@SerializedName("to")
	private String to;

	@SerializedName("priority")
	private final String priority;

	public Notification getNotification(){
		return notification;
	}

	public Data getData(){
		return data;
	}

	public String getTo(){
		return to;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void setTo(String to) {
		this.to = to;
	}
}