package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_IMAGE_LIBARARY;
import static com.brijframework.content.constants.Constants.IMAGE_URL;
import static com.brijframework.content.constants.Constants.RESOURCE_ID;

import java.util.ArrayList;
import java.util.List;

import com.brijframework.content.constants.ResourceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_IMAGE_LIBARARY)
public class EOGlobalImageLibarary extends EOGlobalItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String RESOURCE_TYPE = "RESOURCE_TYPE";

	@Column(name=RESOURCE_ID)
	private Long resourceId;
	
	@Column(name=IMAGE_URL)
	@Lob
	public String imageUrl;
	
	@Column(name=RESOURCE_TYPE)
	private String resourceType;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalSubCategory subCategory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "imageLibarary")
	private List<EOGlobalTagImageMapping> tagList;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public EOGlobalSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(EOGlobalSubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public List<EOGlobalTagImageMapping> getTagList() {
		if(tagList==null) {
			tagList=new ArrayList<EOGlobalTagImageMapping>();
		}
		return tagList;
	}

	public void setTagList(List<EOGlobalTagImageMapping> tagList) {
		this.tagList = tagList;
	}

	public String getResourceType() {
		if(resourceType==null) {
			resourceType=ResourceType.PORTAL.toString();
		}
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	

}
