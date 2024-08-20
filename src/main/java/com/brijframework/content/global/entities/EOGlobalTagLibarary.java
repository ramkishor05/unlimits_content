package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG_LIBARARY;
import static com.brijframework.content.constants.Constants.NAME;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAG_LIBARARY ,  uniqueConstraints = { @UniqueConstraint (columnNames = {"SUB_CATEGORY_ID", NAME})} )
public class EOGlobalTagLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalSubCategory subCategory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tagLibarary")
	private List<EOGlobalTagImageMapping> imageList;

	public EOGlobalSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(EOGlobalSubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public String getColor() {
		if(super.getColor()==null) {
			super.setColor(getSubCategory().getColor());
		}
		return super.getColor();
	}

	public List<EOGlobalTagImageMapping> getImageList() {
		return imageList;
	}

	public void setImageList(List<EOGlobalTagImageMapping> imageList) {
		this.imageList = imageList;
	}
}
