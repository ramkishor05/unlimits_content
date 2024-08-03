package com.brijframework.content.global.model;

import java.io.Serializable;

import com.brijframework.content.resource.modal.UIResource;

public class UIGlobalImageLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	
	private UIResource fileResource;

	private String imageUrl;

	private String posterUrl;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public UIResource getFileResource() {
		return fileResource;
	}

	public void setFileResource(UIResource fileResource) {
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

	
}
