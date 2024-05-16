package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG_GROUP;
import static com.brijframework.content.constants.Constants.GLB_TAG_GROUP;
import static com.brijframework.content.constants.Constants.NAME;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name=EOGLOBAL_TAG_GROUP ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTagGroup extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;
	
	@JoinColumn(name = "SUB_CATEGORY_ID")
	@ManyToOne
	private EOGlobalCategoryItem globalCategoryItem;
	
	@OneToMany(mappedBy = GLB_TAG_GROUP, cascade = CascadeType.ALL)
	public Set<EOGlobalTagItem> globalTagItemList;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public EOGlobalCategoryItem getGlobalCategoryItem() {
		return globalCategoryItem;
	}

	public void setGlobalCategoryItem(EOGlobalCategoryItem globalCategoryItem) {
		this.globalCategoryItem = globalCategoryItem;
	}

	public Set<EOGlobalTagItem> getGlobalTagItemList() {
		return globalTagItemList;
	}

	public void setGlobalTagItemList(Set<EOGlobalTagItem> globalTagItemList) {
		this.globalTagItemList = globalTagItemList;
	}
	
}
