package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.List;

import com.brijframework.content.resource.modal.UIResourceModel;

public class UIGlobalImageLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	
	private UIResourceModel fileResource;

	private String imageUrl;

	private String posterUrl;
	
	private List<UIGlobalTagLibarary> tagList;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public UIResourceModel getFileResource() {
		return fileResource;
	}

	public void setFileResource(UIResourceModel fileResource) {
		this.fileResource = fileResource;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public List<UIGlobalTagLibarary> getTagList() {
		return tagList;
	}

	public void setTagList(List<UIGlobalTagLibarary> tagList) {
		this.tagList = tagList;
	}
	
}
