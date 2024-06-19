package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalMainCategory extends UIGlobalItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UIResource content;

	public UIResource getContent() {
		return content;
	}

	public void setContent(UIResource content) {
		this.content = content;
	}
	
	
}
