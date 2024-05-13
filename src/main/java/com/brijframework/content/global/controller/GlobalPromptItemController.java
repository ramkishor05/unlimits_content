package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalPromptItem;
import com.brijframework.content.global.model.UIGlobalPromptItem;
import com.brijframework.content.global.service.GlobalPromptItemService;


@RestController
@RequestMapping("/api/global/prompt")
public class GlobalPromptItemController extends CrudController<UIGlobalPromptItem, EOGlobalPromptItem, Long> {

	@Autowired
	private GlobalPromptItemService globalPromptItemService;
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalPromptItem> getPromptList(@PathVariable(TYPE_ID) String typeId) {
		return globalPromptItemService.findAllByType(typeId);
	}

	@Override
	public CrudService<UIGlobalPromptItem, EOGlobalPromptItem, Long> getService() {
		return globalPromptItemService;
	}
}
