package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TAGE_IMAGE_MAPPING;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_TAGE_IMAGE_MAPPING)
public class EOGlobalTagImageMapping extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name="IMAGE_LIBARARY_ID")
	@ManyToOne(optional = true)
	public EOGlobalImageLibarary imageLibarary;
	
	@JoinColumn(name="TAG_LIBARARY_ID")
	@ManyToOne(optional = true)
	public EOGlobalTagLibarary tagLibarary;

	public EOGlobalImageLibarary getImageLibarary() {
		return imageLibarary;
	}

	public void setImageLibarary(EOGlobalImageLibarary imageLibarary) {
		this.imageLibarary = imageLibarary;
	}

	public EOGlobalTagLibarary getTagLibarary() {
		return tagLibarary;
	}

	public void setTagLibarary(EOGlobalTagLibarary tagLibarary) {
		this.tagLibarary = tagLibarary;
	}
}
