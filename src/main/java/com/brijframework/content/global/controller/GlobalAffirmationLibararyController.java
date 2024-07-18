/**
 * 
 */
package com.brijframework.content.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.model.UIGlobalAffirmationLibarary;
import com.brijframework.content.global.service.GlobalAffirmationLibararyService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/global/affirmation/libarary"})
public class GlobalAffirmationLibararyController implements CrudController<UIGlobalAffirmationLibarary, EOGlobalAffirmationLibarary, Long>{
	
	@Autowired
	private GlobalAffirmationLibararyService globalAffirmationLibararyService;

	@Override
	public CrudService<UIGlobalAffirmationLibarary, EOGlobalAffirmationLibarary, Long> getService() {
		return globalAffirmationLibararyService;
	}

}
