package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.model.UIGlobalPrompt;
import com.brijframework.content.global.service.GlobalPromptItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tagprompt/mapping")
@Hidden
public class GlobalTagPromptMappingController extends CrudController<UIGlobalPrompt, EOGlobalPrompt, Long> {

	@Autowired
	private GlobalPromptItemService globalPromptItemService;
	
	@Override
	public CrudService<UIGlobalPrompt, EOGlobalPrompt, Long> getService() {
		return globalPromptItemService;
	}
}
