package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalPromptLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	
	private Long subCategoryId;
	
	private String subCategoryName;
	
	private Long tenureId;
	
	private Integer tenureYear;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getTenureId() {
		return tenureId;
	}

	public void setTenureId(Long tenureId) {
		this.tenureId = tenureId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Integer getTenureYear() {
		return tenureYear;
	}

	public void setTenureYear(Integer tenureYear) {
		this.tenureYear = tenureYear;
	}
	
}
