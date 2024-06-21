package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceImageLibarary extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long subCategoryId;
	
	private Long tagLibararyId;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getTagLibararyId() {
		return tagLibararyId;
	}

	public void setTagLibararyId(Long tagLibararyId) {
		this.tagLibararyId = tagLibararyId;
	}
}
