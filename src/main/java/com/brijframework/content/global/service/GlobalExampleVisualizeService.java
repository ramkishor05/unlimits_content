package com.brijframework.content.global.service;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalExampleVisualize;
import com.brijframework.content.global.model.UIGlobalExampleVisualize;

public interface GlobalExampleVisualizeService extends CrudService<UIGlobalExampleVisualize, EOGlobalExampleVisualize, Long> {

	void deleteByExampleLibararyId(Long id);

}
