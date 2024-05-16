package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceSubTag extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mainTagId;

	private String color;

	public Long getMainTagId() {
		return mainTagId;
	}

	public void setMainTagId(Long mainTagId) {
		this.mainTagId = mainTagId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
