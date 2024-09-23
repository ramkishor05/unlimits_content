package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalMindSetLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public UIResourceModel fileResource;

	private String musicUrl;

	private String posterUrl;

	private List<UIGlobalMindSetItem> mindSetItems;

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

	public List<UIGlobalMindSetItem> getMindSetItems() {
		if(mindSetItems==null) {
			mindSetItems=new ArrayList<UIGlobalMindSetItem>();
		}
		return mindSetItems;
	}

	public void setMindSetItems(List<UIGlobalMindSetItem> mindSetItems) {
		this.mindSetItems = mindSetItems;
	}

}
