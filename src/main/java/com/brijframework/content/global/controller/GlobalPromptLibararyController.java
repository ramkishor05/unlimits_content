package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.service.GlobalPromptLibararyService;


@RestController
@RequestMapping("/api/global/prompt/libarary")
public class GlobalPromptLibararyController implements CrudController<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> {

	@Autowired
	private GlobalPromptLibararyService globalPromptLibararyService;
	
	@Override
	public CrudService<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> getService() {
		return globalPromptLibararyService;
	}

}
