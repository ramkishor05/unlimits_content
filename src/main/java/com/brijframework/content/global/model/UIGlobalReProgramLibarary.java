package com.brijframework.content.global.model;

import com.brijframework.content.resource.modal.UIResourceModel;

public class UIGlobalReProgramLibarary extends UIGlobalItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIResourceModel fileResource;

	private String musicUrl;

	private String posterUrl;

	public UIResourceModel getFileResource() {
		return fileResource;
	}

	public void setFileResource(UIResourceModel fileResource) {
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
