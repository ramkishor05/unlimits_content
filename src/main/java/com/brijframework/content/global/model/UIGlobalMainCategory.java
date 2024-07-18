package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalMainCategory extends UIGlobalItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String title;

	private String description;

	private UIResource content;

	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UIResource getContent() {
		return content;
	}

	public void setContent(UIResource content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
