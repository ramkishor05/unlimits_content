package com.brijframework.content.global.model;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalAffirmationLibarary extends UIGlobalItem {

	private UIResource fileResource;
	
	private Long resourceId;

	private String musicUrl;

	private String posterUrl;

	public UIResource getFileResource() {
		return fileResource;
	}

	public void setFileResource(UIResource fileResource) {
		this.fileResource = fileResource;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

}
