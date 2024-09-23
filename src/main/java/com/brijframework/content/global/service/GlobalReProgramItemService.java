package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalReProgramItem;
import com.brijframework.content.global.model.UIGlobalReProgramItem;

public interface GlobalReProgramItemService extends CrudService<UIGlobalReProgramItem, EOGlobalReProgramItem, Long>{

	void deleteByReProgramLibararyId(Long id);

	void deleteByReProgramLibararyIdAndIdNotIn(Long id, List<Long> ids);

}
