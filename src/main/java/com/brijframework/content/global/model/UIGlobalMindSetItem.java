package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalMindSetItem extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UIResourceModel fileResource;
	
	private String musicUrl;
	
	private String posterUrl;
	
	private Long mindSetLibararyId;
	
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

	public Long getMindSetLibararyId() {
		return mindSetLibararyId;
	}

	public void setMindSetLibararyId(Long mindSetLibararyId) {
		this.mindSetLibararyId = mindSetLibararyId;
	}

}