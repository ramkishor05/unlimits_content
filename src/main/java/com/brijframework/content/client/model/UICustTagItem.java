package com.brijframework.content.client.model;

import java.io.Serializable;

public class UICustTagItem implements Serializable {

	private static final long serialVersionUID = 1L;
	public long id;
	public String categoryId;
	public String name;
	public String description;
	public String typeId;
	public long custCategoryGroupId;
	public long custBusinessAppId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public long getCustCategoryGroupId() {
		return custCategoryGroupId;
	}

	public void setCustCategoryGroupId(long custCategoryGroupId) {
		this.custCategoryGroupId = custCategoryGroupId;
	}

	public long getCustBusinessAppId() {
		return custBusinessAppId;
	}

	public void setCustBusinessAppId(long custBusinessAppId) {
		this.custBusinessAppId = custBusinessAppId;
	}

}
