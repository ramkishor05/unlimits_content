package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.model.UIGlobalPromptItem;


public interface GlobalPromptItemService extends CrudService<UIGlobalPromptItem, EOGlobalPromptItem, Long>{

	List<UIGlobalPromptItem> findAllByType(String typeId);

}
