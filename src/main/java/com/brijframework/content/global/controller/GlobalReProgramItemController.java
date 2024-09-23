/**
 * 
 */
package com.brijframework.content.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalReProgramItem;
import com.brijframework.content.global.model.UIGlobalReProgramItem;
import com.brijframework.content.global.service.GlobalReProgramItemService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/global/reprogram/item"})
@CrossOrigin("*")
public class GlobalReProgramItemController implements CrudController<UIGlobalReProgramItem, EOGlobalReProgramItem, Long>{
	
	@Autowired
	private GlobalReProgramItemService globalReProgramItemService;

	@Override
	public CrudService<UIGlobalReProgramItem, EOGlobalReProgramItem, Long> getService() {
		return globalReProgramItemService;
	}

}
