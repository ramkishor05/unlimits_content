package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalMindSetItem extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String content;
	
	private String url;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
