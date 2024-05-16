package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalCategoryItem extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupId;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
