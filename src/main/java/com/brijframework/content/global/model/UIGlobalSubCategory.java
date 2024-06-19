package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalSubCategory extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupId;
	private UIResource content;

	public UIResource getContent() {
		return content;
	}

	public void setContent(UIResource content) {
		this.content = content;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
