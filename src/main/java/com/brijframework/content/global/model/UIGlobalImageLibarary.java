package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.List;

import com.brijframework.content.resource.modal.UIResourceModel;

public class UIGlobalImageLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long subCategoryId;
	
	private String subCategoryName;
	
	private String resourceType;
	
	private UIResourceModel fileResource;

	private String imageUrl;

	private String posterUrl;
	
	private List<UIGlobalTagModel> tagList;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
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

	public List<UIGlobalTagModel> getTagList() {
		return tagList;
	}

	public void setTagList(List<UIGlobalTagModel> tagList) {
		this.tagList = tagList;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
