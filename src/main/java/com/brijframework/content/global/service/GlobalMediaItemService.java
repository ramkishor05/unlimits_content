package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.model.UIGlobalMediaItem;


public interface GlobalMediaItemService extends CrudService<UIGlobalMediaItem, EOGlobalMediaItem, Long>{

	List<UIGlobalMediaItem> findAllByType(String typeId);

}
