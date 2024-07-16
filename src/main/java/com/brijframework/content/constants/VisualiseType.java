package com.brijframework.content.constants;

public enum VisualiseType {

	VISUALISE_WITH_IMAGES ("VISUALISE_WITH_IMAGES"),
	VISUALISE_WITH_WORDS ("VISUALISE_WITH_WORDS"),
	VISUALISE_WITH_EXAMPLES ("VISUALISE_WITH_EXAMPLES");

	String type;
	
	VisualiseType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	
}
