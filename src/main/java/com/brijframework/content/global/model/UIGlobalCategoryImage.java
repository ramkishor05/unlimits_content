package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalCategoryImage extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long groupId;
	
	private String url;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}