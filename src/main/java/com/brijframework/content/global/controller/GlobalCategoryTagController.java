package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalCategoryTag;
import com.brijframework.content.global.model.UIGlobalTagItem;
import com.brijframework.content.global.service.GlobalTagItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tag/library")
@Hidden
public class GlobalCategoryTagController extends CrudController<UIGlobalTagItem, EOGlobalCategoryTag, Long>{

	@Autowired
	private GlobalTagItemService globalTagItemService;
	
	@Override
	public CrudService<UIGlobalTagItem, EOGlobalCategoryTag, Long> getService() {
		return globalTagItemService;
	}
}
