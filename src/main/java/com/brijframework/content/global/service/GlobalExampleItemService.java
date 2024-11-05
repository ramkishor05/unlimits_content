package com.brijframework.content.global.service;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.model.UIGlobalExampleItem;

public interface GlobalExampleItemService extends CrudService<UIGlobalExampleItem, EOGlobalExampleItem, Long> {

	void deleteByExampleLibararyId(Long id);

}
