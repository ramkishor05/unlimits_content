package com.brijframework.content.global.model;

import java.util.List;
import java.util.Map;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIGlobalExampleLibarary extends UIGlobalItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long subCategoryId;

	private String profileName;
	private String profilePictureURL;
	private String profileAge;
	private String profilePosition;
	private String profileOrganization;
	private String posterUrl;
	
	private UIResourceModel profileResource;
	private Map<Integer, UIGlobalExampleVisualize> visualizeMap;

	private List<UIGlobalExampleItem> exampleItems;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
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

	public Map<Integer, UIGlobalExampleVisualize> getVisualizeMap() {
		return visualizeMap;
	}

	public void setVisualizeMap(Map<Integer, UIGlobalExampleVisualize> visualizeMap) {
		this.visualizeMap = visualizeMap;
	}

	public List<UIGlobalExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<UIGlobalExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}

	public UIResourceModel getProfileResource() {
		return profileResource;
	}

	public void setProfileResource(UIResourceModel profileResource) {
		this.profileResource = profileResource;
	}
}
