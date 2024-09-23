package com.brijframework.content.device.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceReProgramItem extends UIDeviceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String musicUrl;

	private String posterUrl;
	
	private Long reProgramLibararyId;

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

	public Long getReProgramLibararyId() {
		return reProgramLibararyId;
	}

	public void setReProgramLibararyId(Long reProgramLibararyId) {
		this.reProgramLibararyId = reProgramLibararyId;
	}
}
