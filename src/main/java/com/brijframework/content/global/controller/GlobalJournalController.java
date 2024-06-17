package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalJournal;
import com.brijframework.content.global.model.UIGlobalJournal;
import com.brijframework.content.global.service.GlobalJournalService;


@RestController
@RequestMapping("/api/global/journal")
@CrossOrigin("*")
public class GlobalJournalController extends CrudController<UIGlobalJournal, EOGlobalJournal, Long> {

	@Autowired
	private GlobalJournalService globalJournalService;
	
	@Override
	public CrudService<UIGlobalJournal, EOGlobalJournal, Long> getService() {
		return globalJournalService;
	}
	
}
