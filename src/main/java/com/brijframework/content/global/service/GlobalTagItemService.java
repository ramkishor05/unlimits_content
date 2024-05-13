package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.model.UIGlobalTagItem;


public interface GlobalTagItemService extends CrudService<UIGlobalTagItem, EOGlobalTagItem, Long>{

	List<UIGlobalTagItem> findAllByType(String typeId);

}
