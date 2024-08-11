package com.brijframework.content.device.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIDeviceExampleItemModel extends UIDeviceModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer year;
	
	private UIDeviceImageModel imageLibarary;
	
	private UIDeviceTagLibarary tagLibarary;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public UIDeviceImageModel getImageLibarary() {
		return imageLibarary;
	}

	public void setImageLibarary(UIDeviceImageModel imageLibarary) {
		this.imageLibarary = imageLibarary;
	}

	public UIDeviceTagLibarary getTagLibarary() {
		return tagLibarary;
	}

	public void setTagLibarary(UIDeviceTagLibarary tagLibarary) {
		this.tagLibarary = tagLibarary;
	}
	
}