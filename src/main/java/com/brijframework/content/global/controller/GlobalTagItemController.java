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

import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.global.service.GlobalTagItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tag/item")
@Hidden
public class GlobalTagItemController extends CrudController<UIGlobalTagItem, EOGlobalTagItem, Long>{

	@Autowired
	private GlobalTagItemService globalTagItemService;
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalTagItem> getTagList(@PathVariable(TYPE_ID) String typeId) {
		return globalTagItemService.findAllByType(typeId);
	}

	@Override
	public CrudService<UIGlobalTagItem, EOGlobalTagItem, Long> getService() {
		return globalTagItemService;
	}
}
