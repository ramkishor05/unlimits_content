package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceMindSet extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
