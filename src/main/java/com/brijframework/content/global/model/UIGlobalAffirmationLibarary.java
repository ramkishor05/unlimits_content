package com.brijframework.content.global.model;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalAffirmationLibarary extends UIGlobalItem {

	private UIResource content;

	private String url;

	public UIResource getContent() {
		return content;
	}

	public void setContent(UIResource content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
