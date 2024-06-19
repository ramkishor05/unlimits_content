package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.model.UIGlobalMainCategory;

public interface GlobalMainCategoryService  extends CrudService<UIGlobalMainCategory, EOGlobalMainCategory, Long>{

	List<UIGlobalMainCategory> getMainCategoryList(RecordStatus dataStatus);

}
