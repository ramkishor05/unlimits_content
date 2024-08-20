package com.brijframework.content.global.model;

public class UITagResource {
	private String name;
	private String subCategoryName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	@Override
	public String toString() {
		return "UITagResource [name=" + name + ", subCategoryName=" + subCategoryName + "]";
	}
}