package com.brijframework.content.global.model;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalReProgramLibarary extends UIGlobalItem {

	private UIResource fileResource;

	private String musicUrl;

	private String posterUrl;

	public UIResource getFileResource() {
		return fileResource;
	}

	public void setFileResource(UIResource fileResource) {
		this.fileResource = fileResource;
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
