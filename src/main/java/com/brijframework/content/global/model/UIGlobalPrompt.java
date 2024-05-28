package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalPrompt extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
