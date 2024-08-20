package com.brijframework.content.device.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceTagLibarary extends UIDeviceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long subCategoryId;
	private String subCategoryName;
	
	private Long mainCategoryId;
	private String mainCategoryName;
	
	private List<UIDeviceImageModel> imageList;
	
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

	public List<UIDeviceImageModel> getImageList() {
		return imageList;
	}

	public void setImageList(List<UIDeviceImageModel> imageList) {
		this.imageList = imageList;
	}
	
}
