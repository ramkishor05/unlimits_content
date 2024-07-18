package com.brijframework.content.global.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalMindSetItem extends UIModel{

	private UIResource resource;
	
	private String url;
	
	private String name;
	
	private String description;

	public UIResource getResource() {
		return resource;
	}

	public void setResource(UIResource resource) {
		this.resource = resource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
