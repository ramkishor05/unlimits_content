package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_CATEGORY_GROUP;
import static com.brijframework.content.constants.Constants.GLB_CATEGORY_GROUP;
import static com.brijframework.content.constants.Constants.*;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_CATEGORY_GROUP, uniqueConstraints = { @UniqueConstraint(columnNames = { NAME }) })
public class EOGlobalCategoryGroup extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;

	@OneToMany(mappedBy = GLB_CATEGORY_GROUP, cascade = CascadeType.ALL)
	public Set<EOGlobalCategoryItem> globalCategories;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Set<EOGlobalCategoryItem> getGlobalCategories() {
		return globalCategories;
	}

	public void setGlobalCategories(Set<EOGlobalCategoryItem> globalCategories) {
		this.globalCategories = globalCategories;
	}

}
