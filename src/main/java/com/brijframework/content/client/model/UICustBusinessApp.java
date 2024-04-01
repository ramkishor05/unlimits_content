package com.brijframework.content.client.model;

public class UICustBusinessApp {

	private long id;

	private long appId;

	private long custId;
	
	private long businessId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	@Override
	public String toString() {
		return "UIInventoryApplication [id=" + id + ", appId=" + appId + ", custId=" + custId + "]";
	}

}
