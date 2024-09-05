package com.brijframework.content.global.model;

import java.util.List;
import java.util.Map;

import com.brijframework.content.resource.modal.UIResourceModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;

@JsonInclude(content = Include.NON_ABSENT)
public class UIGlobalExampleResource {

	private String idenNo;
	@NotNull
	private String profileName;
	private String profilePictureURL;
	private String profileAge;
	private String profilePosition;
	private String profileOrganization;
	private String posterUrl;
	private String subCategoryName;

	private UIResourceModel profileResource;

	private Map<Integer, UIGlobalExampleVisualize> visualizeMap;

	private List<UIGlobalExampleItemResource> exampleItems;

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
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

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public UIResourceModel getProfileResource() {
		return profileResource;
	}

	public void setProfileResource(UIResourceModel profileResource) {
		this.profileResource = profileResource;
	}

	public Map<Integer, UIGlobalExampleVisualize> getVisualizeMap() {
		return visualizeMap;
	}

	public void setVisualizeMap(Map<Integer, UIGlobalExampleVisualize> visualizeMap) {
		this.visualizeMap = visualizeMap;
	}

	public List<UIGlobalExampleItemResource> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<UIGlobalExampleItemResource> exampleItems) {
		this.exampleItems = exampleItems;
	}

}
