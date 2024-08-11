package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.model.UIGlobalExampleLibarary;

public interface GlobalExampleLibararyService extends CrudService<UIGlobalExampleLibarary, EOGlobalExampleLibarary, Long> {

	void init(List<EOGlobalExampleLibarary> eoGlobalExampleLibararyJson);

}
