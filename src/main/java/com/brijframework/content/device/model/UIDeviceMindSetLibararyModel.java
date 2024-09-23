package com.brijframework.content.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceMindSetLibararyModel extends UIDeviceModel {

	private static final long serialVersionUID = 1L;

	private String musicUrl;

	private String posterUrl;

	private List<UIDeviceMindSetItemModel> mindSetItems;

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

	public List<UIDeviceMindSetItemModel> getMindSetItems() {
		return mindSetItems;
	}

	public void setMindSetItems(List<UIDeviceMindSetItemModel> mindSetItems) {
		this.mindSetItems = mindSetItems;
	}

}
