package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.model.UIGlobalMediaItem;
import com.brijframework.content.global.service.GlobalMediaItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/media/item")
@Hidden
public class GlobalMediaItemController extends CrudController<UIGlobalMediaItem, EOGlobalMediaItem, Long>{

	@Autowired
	private GlobalMediaItemService globalMediaItemService;
	
	@Override
	public CrudService<UIGlobalMediaItem, EOGlobalMediaItem, Long> getService() {
		return globalMediaItemService;
	}
	
}
