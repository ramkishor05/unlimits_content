package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.service.GlobalImageLibararyService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/image/libarary")
@Hidden
public class GlobalImageLibararyController implements CrudController<UIGlobalImageLibarary, EOGlobalImageLibarary, Long>{

	@Autowired
	private GlobalImageLibararyService globalImageLibararyService;
	
	@Override
	public CrudService<UIGlobalImageLibarary, EOGlobalImageLibarary, Long> getService() {
		return globalImageLibararyService;
	}
	
}
