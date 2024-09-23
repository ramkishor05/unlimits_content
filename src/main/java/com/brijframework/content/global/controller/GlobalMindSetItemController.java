/**
 * 
 */
package com.brijframework.content.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.service.GlobalMindSetItemService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/global/mindset/item"})
public class GlobalMindSetItemController implements CrudController<UIGlobalMindSetItem, EOGlobalMindSetItem, Long>{
	
	@Autowired
	private GlobalMindSetItemService globalMindSetItemService;

	@Override
	public CrudService<UIGlobalMindSetItem, EOGlobalMindSetItem, Long> getService() {
		return globalMindSetItemService;
	}

}
