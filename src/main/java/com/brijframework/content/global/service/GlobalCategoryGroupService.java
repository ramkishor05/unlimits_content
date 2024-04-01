package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;

public interface GlobalCategoryGroupService {

	UIGlobalCategoryGroup saveCategoryGroup(UIGlobalCategoryGroup unitGroup);

	UIGlobalCategoryGroup getCategoryGroup(long id);

	List<UIGlobalCategoryGroup> getCategoryGroupList();

	List<UIGlobalCategoryGroup> getCategoryGroup( String typeId);

	List<UIGlobalCategoryGroup> getCategoryGroupList(RecordStatus dataStatus);

	boolean deleteCategoryGroup(Long id);

}
