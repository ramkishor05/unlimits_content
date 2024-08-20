package com.brijframework.content.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceImageLibarary extends UIDeviceModel{

	private static final long serialVersionUID = 1L;

	private Long subCategoryId;
	private String subCategoryName;
	
	private Long mainCategoryId;
	private String mainCategoryName;

	public String imageUrl;
	
	private List<UIDeviceTagModel> tagList;

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

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public String getMainCategoryName() {
		return mainCategoryName;
	}

	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<UIDeviceTagModel> getTagList() {
		return tagList;
	}

	public void setTagList(List<UIDeviceTagModel> tagList) {
		this.tagList = tagList;
	}
}
