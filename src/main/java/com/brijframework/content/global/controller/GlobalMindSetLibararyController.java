package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;
import com.brijframework.content.global.service.GlobalMindSetLibararyService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/mindset/libarary")
@Hidden
public class GlobalMindSetLibararyController implements CrudController<UIGlobalMindSetLibarary, EOGlobalMindSetLibarary, Long>{

	@Autowired
	private GlobalMindSetLibararyService globalMindSetLibararyService;

	@Override
	public CrudService<UIGlobalMindSetLibarary, EOGlobalMindSetLibarary, Long> getService() {
		return globalMindSetLibararyService;
	}
}
