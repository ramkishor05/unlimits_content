package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.EOGLOBAL_SUB_CATEGORY;
import static com.brijframework.content.constants.Constants.MAIN_CATEGORY_ID;
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
@Table(name=EOGLOBAL_SUB_CATEGORY ,  uniqueConstraints = { @UniqueConstraint (columnNames = {MAIN_CATEGORY_ID, NAME})} )
public class EOGlobalSubCategory extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;

	@ManyToOne
	@JoinColumn(name = MAIN_CATEGORY_ID, nullable = false)
	private EOGlobalMainCategory mainCategory;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public EOGlobalMainCategory getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(EOGlobalMainCategory mainCategory) {
		this.mainCategory = mainCategory;
	}
	
	
}
