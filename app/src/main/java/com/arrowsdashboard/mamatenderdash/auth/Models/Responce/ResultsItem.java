package com.arrowsdashboard.mamatenderdash.auth.Models.Responce;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("message_id")
	private String messageId;

	public String getMessageId(){
		return messageId;
	}
}