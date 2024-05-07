package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.model.UIGlobalTagGroup;
import com.brijframework.content.service.CrudService;


public interface GlobalTagGroupService extends CrudService<UIGlobalTagGroup, EOGlobalTagGroup, Long>{

	List<UIGlobalTagGroup> findAllByType(String typeId);

}
