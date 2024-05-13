package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.model.UIGlobalTagGroup;


public interface GlobalTagGroupService extends CrudService<UIGlobalTagGroup, EOGlobalTagGroup, Long>{

	List<UIGlobalTagGroup> findAllByType(String typeId);

}
