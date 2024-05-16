package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceSubCategory extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String color;
	private Long categoryId;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
