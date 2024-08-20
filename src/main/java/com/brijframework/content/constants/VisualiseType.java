package com.brijframework.content.constants;

public enum VisualiseType {

	IMAGE_PAGE ("Image"),
	WORDS_PAGE ("Words"),
	EXAMPLES_PAGE ("Example");

	String type;
	
	VisualiseType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	
}
