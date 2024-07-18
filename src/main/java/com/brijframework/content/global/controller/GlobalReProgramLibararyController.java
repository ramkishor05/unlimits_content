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

import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.model.UIGlobalReProgramLibarary;
import com.brijframework.content.global.service.GlobalReProgramLibararyService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/global/reprogram/libarary"})
@CrossOrigin("*")
public class GlobalReProgramLibararyController implements CrudController<UIGlobalReProgramLibarary, EOGlobalReProgramLibarary, Long>{
	
	@Autowired
	private GlobalReProgramLibararyService globalReProgramLibararyService;

	@Override
	public CrudService<UIGlobalReProgramLibarary, EOGlobalReProgramLibarary, Long> getService() {
		return globalReProgramLibararyService;
	}

}
