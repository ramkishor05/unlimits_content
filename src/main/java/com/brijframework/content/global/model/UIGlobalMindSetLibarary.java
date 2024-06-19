package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalMindSetLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UIResource content;
	
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
