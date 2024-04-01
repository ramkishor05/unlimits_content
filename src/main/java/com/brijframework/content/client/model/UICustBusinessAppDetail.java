package com.brijframework.content.client.model;

import java.util.Set;

public class UICustBusinessAppDetail extends UICustBusinessApp {

	public Set<UICustCategoryGroup> custCategoryGroupList;

	public Set<UICustCategoryGroup> getCustCategoryGroupList() {
		return custCategoryGroupList;
	}

	public void setCustCategoryGroupList(Set<UICustCategoryGroup> custCategoryGroupList) {
		this.custCategoryGroupList = custCategoryGroupList;
	}
	
}
