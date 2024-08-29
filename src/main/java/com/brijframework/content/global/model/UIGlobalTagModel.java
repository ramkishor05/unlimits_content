package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalTagModel extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	
	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

}
