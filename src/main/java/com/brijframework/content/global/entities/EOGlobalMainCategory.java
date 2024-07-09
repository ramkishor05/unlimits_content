package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_MAIN_CATEGORY;
import static com.brijframework.content.constants.Constants.GLB_MAIN_CATEGORY;
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
@Table(name = EOGLOBAL_MAIN_CATEGORY, uniqueConstraints = { @UniqueConstraint(columnNames = { NAME }) })
public class EOGlobalMainCategory extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = GLB_MAIN_CATEGORY, cascade = CascadeType.ALL)
	public Set<EOGlobalSubCategory> subCategoryList;

	public Set<EOGlobalSubCategory> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(Set<EOGlobalSubCategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
	@Override
	public String getIdenNo() {
		if(super.getIdenNo()==null) {
			super.setIdenNo("Global_Portal_MainCategory_"+getName());
		}
		return super.getIdenNo();
	}
}
