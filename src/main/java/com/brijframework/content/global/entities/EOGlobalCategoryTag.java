package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_CATEGORY_TAG;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_CATEGORY_TAG ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalCategoryTag extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name="SUB_CATEGORY_ID")
	@ManyToOne(optional = true)
	public EOGlobalCategoryItem globalCategoryItem;

	public EOGlobalCategoryItem getGlobalCategoryItem() {
		return globalCategoryItem;
	}

	public void setGlobalCategoryItem(EOGlobalCategoryItem globalCategoryItem) {
		this.globalCategoryItem = globalCategoryItem;
	}

}
