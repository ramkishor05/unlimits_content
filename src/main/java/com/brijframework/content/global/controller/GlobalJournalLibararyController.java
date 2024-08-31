package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.model.UIGlobalJournalLibarary;
import com.brijframework.content.global.service.GlobalJournalLibararyService;


@RestController
@RequestMapping("/api/global/journal/libarary")
@CrossOrigin("*")
public class GlobalJournalLibararyController implements CrudController<UIGlobalJournalLibarary, EOGlobalJournalLibarary, Long> {

	@Autowired
	private GlobalJournalLibararyService globalJournalLibararyService;
	
	@Override
	public CrudService<UIGlobalJournalLibarary, EOGlobalJournalLibarary, Long> getService() {
		return globalJournalLibararyService;
	}
	
}
