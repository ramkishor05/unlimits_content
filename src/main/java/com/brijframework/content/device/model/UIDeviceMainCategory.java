package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceMainCategory extends UIDeviceItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
