package com.brijframework.content.device.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceMindSetItemModel extends UIDeviceModel {

	private static final long serialVersionUID = 1L;

	private String musicUrl;

	private String posterUrl;
	
	private Long mindSetLibararyId;

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
