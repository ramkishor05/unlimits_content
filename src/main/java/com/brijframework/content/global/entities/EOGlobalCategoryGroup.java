package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_CATEGORY_GROUP;
import static com.brijframework.content.constants.Constants.GLB_CATEGORY_GROUP;
import static com.brijframework.content.constants.Constants.NAME;

import java.util.Set;

import jakarta.persistence.CascadeType;
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
	
	@OneToMany(mappedBy = GLB_CATEGORY_GROUP, cascade = CascadeType.ALL)
	public Set<EOGlobalCategoryItem> globalCategoryItemList;

	public Set<EOGlobalCategoryItem> getGlobalCategoryItemList() {
		return globalCategoryItemList;
	}

	public void setGlobalCategoryItemList(Set<EOGlobalCategoryItem> globalCategoryItemList) {
		this.globalCategoryItemList = globalCategoryItemList;
	}
}
