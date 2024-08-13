package com.brijframework.content.device.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIDeviceExampleModel extends UIDeviceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subCategoryId;
	private String subCategoryName;
	
	private Long mainCategoryId;
	private String mainCategoryName;

	private String profileName;
	private String profilePictureURL;
	private String profileAge;
	private String profilePosition;
	private String profileOrganization;
	private String posterUrl;

	private Map<Integer, UIDeviceExampleVisualize> visualizeMap;

	private List<UIDeviceExampleItemModel> exampleItems;

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

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public String getProfileAge() {
		return profileAge;
	}

	public void setProfileAge(String profileAge) {
		this.profileAge = profileAge;
	}

	public String getProfilePosition() {
		return profilePosition;
	}

	public void setProfilePosition(String profilePosition) {
		this.profilePosition = profilePosition;
	}

	public String getProfileOrganization() {
		return profileOrganization;
	}

	public void setProfileOrganization(String profileOrganization) {
		this.profileOrganization = profileOrganization;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public Map<Integer, UIDeviceExampleVisualize> getVisualizeMap() {
		if(visualizeMap==null) {
			visualizeMap=new HashMap<Integer, UIDeviceExampleVisualize>();
		}
		return visualizeMap;
	}

	public void setVisualizeMap(Map<Integer, UIDeviceExampleVisualize> visualizeMap) {
		this.visualizeMap = visualizeMap;
	}

	public List<UIDeviceExampleItemModel> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<UIDeviceExampleItemModel> exampleItems) {
		this.exampleItems = exampleItems;
	}

}
