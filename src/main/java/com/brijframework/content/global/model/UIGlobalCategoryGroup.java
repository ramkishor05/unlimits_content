package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalCategoryGroup extends UIGlobalItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
