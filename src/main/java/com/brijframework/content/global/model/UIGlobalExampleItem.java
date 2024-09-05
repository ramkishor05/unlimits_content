package com.brijframework.content.global.model;

import org.unlimits.rest.model.UIModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIGlobalExampleItem extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer year;

	private Long imageLibararyId;

	private Long tagLibararyId;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long getImageLibararyId() {
		return imageLibararyId;
	}

	public void setImageLibararyId(Long imageLibararyId) {
		this.imageLibararyId = imageLibararyId;
	}

	public Long getTagLibararyId() {
		return tagLibararyId;
	}

	public void setTagLibararyId(Long tagLibararyId) {
		this.tagLibararyId = tagLibararyId;
	}

}
