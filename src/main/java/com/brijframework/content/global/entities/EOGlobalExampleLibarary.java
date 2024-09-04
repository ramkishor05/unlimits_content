package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.*;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_EXAMPLE_LIBARARY)
public class EOGlobalExampleLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=RESOURCE_ID)
	private Long resourceId;

	@Column(name = "PROFILE_NAME")
	private String profileName;
	
	@Column(name = "PROFILE_PICTURE_URL")
	private String profilePictureURL;
	
	@Column(name="PROFILE_AGE")
	private String profileAge;
	
	@Column(name="PROFILE_POSITION")
	private String profilePosition;
	
	@Column(name="PROFILE_ORGANIZATION")
	private String profileOrganization;
	
	@Column(name="POSTER_URL")
	private String posterUrl;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalSubCategory subCategory;
	
	@OneToMany(mappedBy = "exampleLibarary", cascade = CascadeType.ALL)
	private List<EOGlobalExampleItem> exampleItems;
	
	@OneToMany(mappedBy = "exampleLibarary", cascade = CascadeType.ALL)
	private List<EOGlobalExampleVisualize> visualizeItems;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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

	public EOGlobalSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(EOGlobalSubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public List<EOGlobalExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<EOGlobalExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}

	public List<EOGlobalExampleVisualize> getVisualizeItems() {
		return visualizeItems;
	}

	public void setVisualizeItems(List<EOGlobalExampleVisualize> visualizeItems) {
		this.visualizeItems = visualizeItems;
	}

	
}
