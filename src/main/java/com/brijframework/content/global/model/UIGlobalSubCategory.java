package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResourceModel;

public class UIGlobalSubCategory extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long mainCategoryId;
	private UIResourceModel content;

	public UIResourceModel getContent() {
		return content;
	}

	public void setContent(UIResourceModel content) {
		this.content = content;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

}
