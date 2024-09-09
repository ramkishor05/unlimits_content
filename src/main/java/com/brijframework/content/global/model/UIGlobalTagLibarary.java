package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.List;

public class UIGlobalTagLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	private String subCategoryName;
	
	private List<UIGlobalImageModel> imageList;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public List<UIGlobalImageModel> getImageList() {
		return imageList;
	}

	public void setImageList(List<UIGlobalImageModel> imageList) {
		this.imageList = imageList;
	}
	
}
