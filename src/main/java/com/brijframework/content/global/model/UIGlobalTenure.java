package com.brijframework.content.global.model;

import java.io.Serializable;

public class UIGlobalTenure extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer year;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
