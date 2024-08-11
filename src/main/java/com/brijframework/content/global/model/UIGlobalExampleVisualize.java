package com.brijframework.content.global.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIGlobalExampleVisualize {

	private String visualizeDate;
	private String visualizeRequest;
	private String visualizeResponse;

	public String getVisualizeDate() {
		return visualizeDate;
	}

	public void setVisualizeDate(String visualizeDate) {
		this.visualizeDate = visualizeDate;
	}

	public String getVisualizeRequest() {
		return visualizeRequest;
	}

	public void setVisualizeRequest(String visualizeRequest) {
		this.visualizeRequest = visualizeRequest;
	}

	public String getVisualizeResponse() {
		return visualizeResponse;
	}

	public void setVisualizeResponse(String visualizeResponse) {
		this.visualizeResponse = visualizeResponse;
	}

}
