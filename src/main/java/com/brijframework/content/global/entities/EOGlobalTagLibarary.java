package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG_LIBARARY;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAG_LIBARARY ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTagLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalSubCategory subCategory;
	
	@Column(name = "TYPE")
	private String type;

	public EOGlobalSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(EOGlobalSubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
