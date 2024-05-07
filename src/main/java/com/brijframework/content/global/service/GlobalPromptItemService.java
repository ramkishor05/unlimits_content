package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.model.UIGlobalPromptItem;
import com.brijframework.content.service.CrudService;


public interface GlobalPromptItemService extends CrudService<UIGlobalPromptItem, EOGlobalPromptItem, Long>{

	List<UIGlobalPromptItem> findAllByType(String typeId);

}
