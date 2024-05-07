package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.service.CrudService;

public interface GlobalCategoryGroupService  extends CrudService<UIGlobalCategoryGroup, EOGlobalCategoryGroup, Long>{

	List<UIGlobalCategoryGroup> getCategoryGroupList(RecordStatus dataStatus);

	List<UIGlobalCategoryGroup> getCategoryGroup(String typeId);

}
