package com.brijframework.content.global.model;

import java.util.ArrayList;
import java.util.List;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalReProgramLibarary extends UIGlobalItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UIResourceModel fileResource;

	private String musicUrl;

	private String posterUrl;

	private List<UIGlobalReProgramItem> reProgramItems;

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

	public List<UIGlobalReProgramItem> getReProgramItems() {
		if(reProgramItems==null) {
			reProgramItems=new ArrayList<UIGlobalReProgramItem>();
		}
		return reProgramItems;
	}

	public void setReProgramItems(List<UIGlobalReProgramItem> reProgramItems) {
		this.reProgramItems = reProgramItems;
	}

}
