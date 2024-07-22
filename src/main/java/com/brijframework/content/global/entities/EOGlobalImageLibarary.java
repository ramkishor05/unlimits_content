package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_IMAGE_LIBARARY;
import static com.brijframework.content.constants.Constants.IMAGE_URL;
import static com.brijframework.content.constants.Constants.RESOURCE_ID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_IMAGE_LIBARARY)
public class EOGlobalImageLibarary extends EOGlobalItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name=RESOURCE_ID)
	private Long resourceId;
	
	@Column(name=IMAGE_URL)
	@Lob
	public String imageUrl;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalSubCategory subCategory;
	
	@JoinColumn(name="TAG_LIBARARY_ID", nullable = true)
	@ManyToOne(optional = true)
	public EOGlobalTagLibarary tagLibarary;

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

	public EOGlobalTagLibarary getTagLibarary() {
		return tagLibarary;
	}

	public void setTagLibarary(EOGlobalTagLibarary tagLibarary) {
		this.tagLibarary = tagLibarary;
	}

}
