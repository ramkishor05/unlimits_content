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

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.model.UIGlobalMediaItem;
import com.brijframework.content.global.service.GlobalMediaItemService;


@RestController
@RequestMapping("/api/global/Media/item")
public class GlobalMediaItemController extends CrudController<UIGlobalMediaItem, EOGlobalMediaItem, Long>{

	@Autowired
	private GlobalMediaItemService globalMediaItemService;
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalMediaItem> getMediaList(@PathVariable(TYPE_ID) String typeId) {
		return globalMediaItemService.findAllByType(typeId);
	}

	@Override
	public CrudService<UIGlobalMediaItem, EOGlobalMediaItem, Long> getService() {
		return globalMediaItemService;
	}
}
