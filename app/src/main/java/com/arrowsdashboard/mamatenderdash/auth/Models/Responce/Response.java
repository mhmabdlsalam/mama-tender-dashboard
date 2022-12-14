package com.arrowsdashboard.mamatenderdash.auth.Models.Responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response{

	@SerializedName("canonical_ids")
	private int canonicalIds;

	@SerializedName("success")
	private int success;

	@SerializedName("failure")
	private int failure;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("multicast_id")
	private long multicastId;

	public int getCanonicalIds(){
		return canonicalIds;
	}

	public int getSuccess(){
		return success;
	}

	public int getFailure(){
		return failure;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public long getMulticastId(){
		return multicastId;
	}
}