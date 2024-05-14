package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetItem;


public interface GlobalMindSetItemService extends CrudService<UIGlobalMindSetItem, EOGlobalMindSetItem, Long>{

	List<UIGlobalMindSetItem> findAllByType(String typeId);

}
