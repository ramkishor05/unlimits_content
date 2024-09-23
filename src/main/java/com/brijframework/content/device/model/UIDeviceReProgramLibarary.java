package com.brijframework.content.device.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceReProgramLibarary extends UIDeviceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String musicUrl;

	private String posterUrl;

	private List<UIDeviceReProgramItem> reProgramItems;

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

	public List<UIDeviceReProgramItem> getReProgramItems() {
		return reProgramItems;
	}

	public void setReProgramItems(List<UIDeviceReProgramItem> reProgramItems) {
		this.reProgramItems = reProgramItems;
	}

}
