package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.service.GlobalTagLibararyService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tag/library")
@Hidden
public class GlobalTagLibararyController extends CrudController<UIGlobalTagLibarary, EOGlobalTagLibarary, Long>{

	@Autowired
	private GlobalTagLibararyService globalTagLibararyService;
	
	@Override
	public CrudService<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> getService() {
		return globalTagLibararyService;
	}
}
