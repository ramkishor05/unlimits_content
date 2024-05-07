package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.model.UIGlobalMediaItem;
import com.brijframework.content.service.CrudService;


public interface GlobalMediaItemService extends CrudService<UIGlobalMediaItem, EOGlobalMediaItem, Long>{

	List<UIGlobalMediaItem> findAllByType(String typeId);

}
