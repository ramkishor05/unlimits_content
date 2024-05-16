package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceSubTag extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long tagId;
	
	private String color;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
