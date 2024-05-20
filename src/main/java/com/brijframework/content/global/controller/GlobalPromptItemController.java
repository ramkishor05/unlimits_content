package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.model.UIGlobalPromptItem;
import com.brijframework.content.global.service.GlobalPromptItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/prompt")
@Hidden
public class GlobalPromptItemController extends CrudController<UIGlobalPromptItem, EOGlobalPromptItem, Long> {

	@Autowired
	private GlobalPromptItemService globalPromptItemService;
	
	@Override
	public CrudService<UIGlobalPromptItem, EOGlobalPromptItem, Long> getService() {
		return globalPromptItemService;
	}
}
