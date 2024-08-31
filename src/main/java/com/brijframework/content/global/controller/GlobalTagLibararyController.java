package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.service.GlobalTagLibararyService;


@RestController
@RequestMapping("/api/global/tag/library")
public class GlobalTagLibararyController implements CrudController<UIGlobalTagLibarary, EOGlobalTagLibarary, Long>{

	@Autowired
	private GlobalTagLibararyService globalTagLibararyService;
	
	@Override
	public CrudService<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> getService() {
		return globalTagLibararyService;
	}
}
