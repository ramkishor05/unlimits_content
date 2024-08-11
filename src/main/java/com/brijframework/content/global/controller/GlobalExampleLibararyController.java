package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.model.UIGlobalExampleLibarary;
import com.brijframework.content.global.service.GlobalExampleLibararyService;

@RestController
@RequestMapping("/api/global/example/libarary")
public class GlobalExampleLibararyController implements CrudController<UIGlobalExampleLibarary, EOGlobalExampleLibarary, Long>{
	
	@Autowired
	private GlobalExampleLibararyService globalExampleLibararyService;

	@Override
	public CrudService<UIGlobalExampleLibarary, EOGlobalExampleLibarary, Long> getService() {
		return globalExampleLibararyService;
	}

}
