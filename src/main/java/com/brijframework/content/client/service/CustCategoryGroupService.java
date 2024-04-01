package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.model.UICustCategoryGroup;
import com.brijframework.content.constants.RecordStatus;

public interface CustCategoryGroupService {

	UICustCategoryGroup saveCategoryGroup(long custAppId, UICustCategoryGroup custCategoryGroup);

	List<UICustCategoryGroup> getCategoryGroupList(long custAppId);

	UICustCategoryGroup getCategoryGroup(long custAppId, Long id);

	boolean deleteCategoryGroup(long custAppId, Long id);

	List<UICustCategoryGroup> getCategoryGroupListByStatus(long custAppId, RecordStatus dataStatus);

	List<UICustCategoryGroup> getCategoryGroupListByType(long custAppId, String typeId);

}
