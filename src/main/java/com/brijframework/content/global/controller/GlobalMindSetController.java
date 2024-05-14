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

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.service.GlobalMindSetItemService;


@RestController
@RequestMapping("/api/global/mindset/item")
public class GlobalMindSetController extends CrudController<UIGlobalMindSetItem, EOGlobalMindSetItem, Long>{

	@Autowired
	private GlobalMindSetItemService globalMindSetItemService;
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalMindSetItem> getPromptList(@PathVariable(TYPE_ID) String typeId) {
		return globalMindSetItemService.findAllByType(typeId);
	}

	@Override
	public CrudService<UIGlobalMindSetItem, EOGlobalMindSetItem, Long> getService() {
		return globalMindSetItemService;
	}
}
