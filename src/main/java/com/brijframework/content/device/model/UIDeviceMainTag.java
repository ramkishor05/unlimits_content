package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceMainTag extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String color;
	
	private Long subCategoryId;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	
}
