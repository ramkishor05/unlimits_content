package com.brijframework.content.global.resource;

import java.util.List;

public class UIExampleResource {

	private String profileName;
	private String profilePictureURL;
	private String profileAge;
	private String profilePosition;
	private String profileOrganization;
	private String posterUrl;

	private List<UIlExampleItemResource> exampleItems;

	private List<UIExampleVisualizeResource> visualizeList;

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

	public List<UIlExampleItemResource> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<UIlExampleItemResource> exampleItems) {
		this.exampleItems = exampleItems;
	}

	public List<UIExampleVisualizeResource> getVisualizeList() {
		return visualizeList;
	}

	public void setVisualizeList(List<UIExampleVisualizeResource> visualizeList) {
		this.visualizeList = visualizeList;
	}

}
