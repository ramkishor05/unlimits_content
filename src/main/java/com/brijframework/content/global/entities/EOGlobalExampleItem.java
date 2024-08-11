package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_EXAMPLE_ITEM;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_EXAMPLE_ITEM , uniqueConstraints = {@UniqueConstraint(columnNames = { "TENURE_ID" , "IMAGE_LIBARARY_ID", "TAG_LIBARARY_ID", "EXAMPLE_LIBARARY_ID"})})
public class EOGlobalExampleItem extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name="TENURE_ID")
	@ManyToOne(optional = true)
	public EOGlobalTenure tenure;

	@JoinColumn(name="IMAGE_LIBARARY_ID")
	@ManyToOne(optional = true)
	public EOGlobalImageLibarary imageLibarary;
	
	@JoinColumn(name="TAG_LIBARARY_ID")
	@ManyToOne(optional = true)
	public EOGlobalTagLibarary tagLibarary;
	
	@JoinColumn(name="EXAMPLE_LIBARARY_ID", referencedColumnName = "id")
	@ManyToOne(optional = true)
	public EOGlobalExampleLibarary exampleLibarary;
	
	public EOGlobalTenure getTenure() {
		return tenure;
	}

	public void setTenure(EOGlobalTenure tenure) {
		this.tenure = tenure;
	}

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

	public EOGlobalExampleLibarary getExampleLibarary() {
		return exampleLibarary;
	}

	public void setExampleLibarary(EOGlobalExampleLibarary exampleLibarary) {
		this.exampleLibarary = exampleLibarary;
	}

	@Override
	public String toString() {
		return "EOGlobalExampleItem [tenure=" + tenure + ", imageLibarary=" + imageLibarary + ", tagLibarary="
				+ tagLibarary + ", exampleLibarary=" + exampleLibarary + "]";
	}

	
}
