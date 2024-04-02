package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.EOGLOBAL_CATEGORY;
import static com.brijframework.content.constants.Constants.GROUP_ID;
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
@Table(name=EOGLOBAL_CATEGORY ,  uniqueConstraints = { @UniqueConstraint (columnNames = {GROUP_ID, NAME})} )
public class EOGlobalCategoryItem extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;

	@ManyToOne
	@JoinColumn(name = GROUP_ID, nullable = false)
	private EOGlobalCategoryGroup globalCategoryGroup;
	
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public EOGlobalCategoryGroup getGlobalCategoryGroup() {
		return globalCategoryGroup;
	}

	public void setGlobalCategoryGroup(EOGlobalCategoryGroup globalCategoryGroup) {
		this.globalCategoryGroup = globalCategoryGroup;
	}
}
