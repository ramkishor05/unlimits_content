package com.brijframework.content.global.model;

import org.unlimits.rest.model.UIModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIGlobalExampleVisualize extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer visualizeYear;
	private String visualizeDate;
	private String visualizeRequest;
	private String visualizeResponse;
	private Long exampleLibararyId;

	public Integer getVisualizeYear() {
		return visualizeYear;
	}

	public void setVisualizeYear(Integer visualizeYear) {
		this.visualizeYear = visualizeYear;
	}

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

	public Long getExampleLibararyId() {
		return exampleLibararyId;
	}

	public void setExampleLibararyId(Long exampleLibararyId) {
		this.exampleLibararyId = exampleLibararyId;
	}
}
