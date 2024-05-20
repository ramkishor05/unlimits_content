package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;

public interface GlobalCategoryGroupService  extends CrudService<UIGlobalCategoryGroup, EOGlobalCategoryGroup, Long>{

	List<UIGlobalCategoryGroup> getCategoryGroupList(RecordStatus dataStatus);

}
