package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.List;

public class UIGlobalTagLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	
	private List<UIGlobalImageLibarary> imageList;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public List<UIGlobalImageLibarary> getImageList() {
		return imageList;
	}

	public void setImageList(List<UIGlobalImageLibarary> imageList) {
		this.imageList = imageList;
	}
	
}
