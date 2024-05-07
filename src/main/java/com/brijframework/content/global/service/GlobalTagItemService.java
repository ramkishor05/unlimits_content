package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.service.CrudService;


public interface GlobalTagItemService extends CrudService<UIGlobalTagItem, EOGlobalTagItem, Long>{

	List<UIGlobalTagItem> findAllByType(String typeId);

}
